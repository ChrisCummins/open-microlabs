/* main.c
 * Target: ATmega164PA
 * Chris Cummins - 08 Apr 2012
 *
 * This file is part of Open MicroLabs v0.5.
 *
 * Open MicroLabs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open MicroLabs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open MicroLabs.  If not, see <http://www.gnu.org/licenses/>.
 */

// HEADER FILES ---------------------------------------------------------------
#include <avr/io.h>									// For IO definitions.
#include <avr/interrupt.h>							// For USART Interrupt vector.
#include <stdio.h>									// For sprintf().
#include <string.h>

// DEFINITIONS ----------------------------------------------------------------
/* BAUD VALUES FOR CLOCK SPEEDS AND DESIRED BAUD RATES */
#define UBRR16_9600  0x67							// 9600b/s @ 16MHZ (0.160% error).
#define UBBR16_19200 0x33							// 19200b/s @ 16MHZ (0.160% error).
#define UBRR16_38400 0x19							// 38400b/s @ 16MHZ (0.160% error).

/* LIST OF REFERENCE VOLTAGES FOR ADC */
#define AREF_AVCC (1<<REFS0)						// Vcc (5V).

// INSTANCE VARIABLES ---------------------------------------------------------

/* Char buffer used for storing ADC values for
 * transmission.
 */
static char usart0_tx_buffer[5];

/* Char buffer used.for storing instructions up
 * until the EOI bit is set.
 */
static char usart0_rx_buffer[256];

/* Char buffer used for storing received responses
 * from microcontrollers befor EOL char is received.
 */
static char usart1_rx_buffer[512];

// FUNCTIONS ------------------------------------------------------------------

int
main (void)
{
  UCSR0B |= (1<<RXEN0) | (1<<TXEN0) | (1<<RXCIE0);	// Enable USART0 rx/tx/rxi.
  UBRR0 = UBRR16_38400;								// Set USART0 baud (38400).

  UCSR1B |= (1<<RXEN1) | (1<<TXEN1) | (1<<RXCIE1);	// Enable USART1 rx/tx/rxi.
  UBRR1 = UBRR16_38400;								// Set USART1 baud (38400).

  ADMUX = AREF_AVCC;								// Set ADC VREF (5V).
  ADCSRA |= ((1 << ADPS2)|(1 << ADPS1)|(1 << ADPS0));// Set ADC Prescale (127).
  ADCSRA |= ((1 << ADEN)|(1 << ADSC));				// Set ADC Enable.
  ADCSRB = 0x00;									// Set free running mode.
  ADCSRA |= (1<<ADSC);								// Start dummy ADC.
  while (ADCSRA & (1<<ADSC))						// While ADC is in progress:
    asm ("NOP");									// Do nothing.
  sei ();											// Enable interrupts.
  while (1)											// Loop infinitely:
    asm("NOP");										// Do nothing.
}  

// INTERRUPT ROUTINES ---------------------------------------------------------

/**
 * USART0 Receive character interrupt..
 */
ISR (USART0_RX_vect)
{
  UCSR0B |= (0<<RXCIE0);							// Turn off USART0 RX interrupt.
  unsigned char c = UDR0;							// Read char from UDR0.

  uint16_t index;
  if (c & (1<<0))									// If char SBI is set,
  {													// Add to USART0 RX bufer:
    uint16_t index = strlen(usart0_rx_buffer);		// Get buffer index.
    usart0_rx_buffer[index] = c;					// Add char to buffer.
    usart0_rx_buffer[index+1] = '\0';				// Add null char to string.
  }
  else												// Otherwise, process instruction:
  {
    if (strlen(usart0_rx_buffer) > 0)				// If USART0 RX buffer contains chars,
    {
  	  index = strlen(usart0_rx_buffer) - 1;			// Get buffer index.
      usart0_rx_buffer[index] &= 0xFE;			    // Disable SBI for last instruction.
      index = 0;									// Initialise counter.
      while (usart0_rx_buffer[index] != '\0')		// Iterate over chars in USART0 RX buffer.
      {
        while (!(UCSR1A & 1 << UDRE1))				// While UDR0 isn't clear:
          asm("NOP");								// Do nothing.
        UDR1 = usart0_rx_buffer[index++];			// Then transmit USART0 RX buffer char.
      }
    }
    for (index = 0x01; index < 0x08; index++)		// Iterate from 1 - 7.
  	  if (c & (1<<index))							// If instruction bit 'index' set high:
	  {
	    ADMUX = (ADMUX & 0xE0) | (index &= 0x1F);	// Set ADC channel to 'index'.
        ADCSRA |= (1<<ADSC);						// Start ADC.
        while (ADCSRA & (1<<ADSC))					// While ADC is in progress:
          asm ("NOP");								// Do nothing.
        sprintf(usart0_tx_buffer, "%d,", ADC);		// Then format result into USART0 TX buffer.
	    uint8_t i = 0;								// Initialise counter.
        while (usart0_tx_buffer[i] != '\0')			// Iterate over USART0 TX buffer chars.
        {
          while (!(UCSR0A & 1<<UDRE0))				// While UDR0 isn't clear:
            asm("NOP");								// Do nothing.
         UDR0 = usart0_tx_buffer[i++];				// Transmit character via USART0.
        }
        memset(usart0_tx_buffer,0x0,sizeof(usart0_tx_buffer)); // Clear USART0 TX Buffer.
	  }
    if (strlen (usart0_rx_buffer) < 1)				// If USART0 RX buffer is empty,
	{
	  while (!(UCSR0A & 1 << UDRE0))				// While UDR0 isn't clear:
        asm("NOP");									// Do nothing.
      UDR0 = '\r';									// Transmit EOL character via USART0.
	}
	else
 	  memset(usart0_rx_buffer,0x0,sizeof(usart0_rx_buffer)); // Clear USART0 RX buffer.
  }
  UCSR0B |= (1<<RXCIE0);							// Turn RX interrupt back on.
}

/**
 * USART1 Receive character interrupt..
 */
ISR (USART1_RX_vect)
{
  UCSR1B |= (0<<RXCIE1);							// Turn off USART1 RX interrupt.
  unsigned char c = UDR1;							// Read char from UDR1.
  uint16_t index = strlen(usart1_rx_buffer);			// Get buffer index.
  usart1_rx_buffer[index] = c;						// Add char to buffer.
  usart1_rx_buffer[index+1] = '\0';					// Add null char to string.
  if (c == '\r')									// If char is EOL,
  {													// Transmit USART1 RX buffer:
    index = 0;										// Initialise counter.
    while (usart1_rx_buffer[index] != '\0')			// Iterate over USART1 RX buffer.
    {
      while (!(UCSR0A & 1 << UDRE0))				// While UDR0 isn't clear:
        asm ("NOP");								// Do nothing.
      UDR0 = usart1_rx_buffer[index++];				// Transmit character via USART0.
    }
    memset(usart1_rx_buffer,0x0,sizeof(usart1_rx_buffer)); // Clear USART1 RX buffer
  }
  UCSR1B |= (1<<RXCIE1);							// Turn USART1 RX interrupt back on.
}
