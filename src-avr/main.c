/* main.c - Main program file for AVR microcontrollers.
 * Version 0.6.5 - 19th April 2012.
 * Target: ATmega164PA.
 *
 *
 * Chris Cummins - 08 Apr 2012
 * This file is part of Open MicroLabs.
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

#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdio.h>
#include <string.h>

// Baud values for desired baud rates at different clock speeds.
#define UBRR16_9600  0x67               // 9600b/s @ 16MHZ (0.160% error).
#define UBBR16_19200 0x33               // 19200b/s @ 16MHZ (0.160% error).
#define UBRR16_38400 0x19               // 38400b/s @ 16MHZ (0.160% error).
// List of references voltages for ADC.
#define AREF_AVCC (1<<REFS0)            // Vcc (5V).
// UBRR value to be used for USART0.
#define UBRR0_value UBRR16_38400
// UBRR value to be used for USART1.
#define UBRR1_value UBRR16_38400
// AREF value to be used for ADC.
#define AREF_value AREF_AVCC
#define ADC_PRESCALE_VALUE ((1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0))

// Char buffer used for storing ADC values for transmission.
static char usart0_tx_buffer[5];

// Char buffer used for received data request slave instructions.
static char usart0_rx_buffer[256];

// Char buffer used for storing received data request responses from slaves.
static char usart1_rx_buffer[512];

// Function prototypes.
void
appendToString(char c, char *string);

void
waitForADC();

void
processInstruction(char c);

/**
 * Main method, initialises USARTs, ADC, interrupts, and then loops nothing.
 */
int
main(void)
{
  // Set USART bauds.
  UBRR0 = UBRR0_value;
  UBRR1 = UBRR1_value;

  // Enable rx/tx/rxi for USART0 and USART1.
  UCSR0B |= (1 << RXEN0) | (1 << TXEN0) | (1 << RXCIE0);
  UCSR1B |= (1 << RXEN1) | (1 << TXEN1) | (1 << RXCIE1);

  // Setup ADC AREF.
  ADMUX = AREF_value;

  // Set ADC Options.
  ADCSRA |= ADC_PRESCALE_VALUE | ((1 << ADEN) | (1 << ADSC));

  // Set free running mode.
  ADCSRB = 0x00;

  // Perform dummy ADC conversion.
  waitForADC();

  // Enable interrupts.
  sei();

  // Do nothing.
  while (1)
    asm("NOP");
}

void
waitForADC()
{
  // Start ADC.
  ADCSRA |= (1 << ADSC);

  // While ADC is in progress, do nothing.
  while (ADCSRA & (1 << ADSC))
    asm ("NOP");
}

void
appendToString(char c, char *string)
{
  // Get string last char index.
  uint16_t index = strlen(string);

  // Add char to string at index.
  string[index] = c;

  // Add null char to new end of string.
  string[index + 1] = '\0';
}

void
processInstruction(char c)
{
  // Initialise temporary variable.
  uint16_t index;

  if (strlen(usart0_rx_buffer) > 0) // If USART0 RX buffer contains chars,
    {
      index = strlen(usart0_rx_buffer) - 1; // Get buffer index.
      usart0_rx_buffer[index] &= 0xFE; // Disable SBI for last instruction.

      index = 0; // Initialise counter.
      while (usart0_rx_buffer[index] != '\0') // Iterate over chars in USART0 RX buffer.
        {
          while (!(UCSR1A & 1 << UDRE1)) // While UDR0 isn't clear:
            asm("NOP");
          // Do nothing.
          UDR1 = usart0_rx_buffer[index++]; // Then transmit USART0 RX buffer char.
        }
    }
  for (index = 0x01; index < 0x08; index++) // Iterate from 1 - 7.
    if (c & (1 << index)) // If instruction bit 'index' set high:
      {
        ADMUX = (ADMUX & 0xE0) | (index &= 0x1F); // Set ADC channel to 'index'.
        waitForADC();
        // Do nothing.
        sprintf(usart0_tx_buffer, "%d,", ADC); // Then format result into USART0 TX buffer.
        uint8_t i = 0; // Initialise counter.
        while (usart0_tx_buffer[i] != '\0') // Iterate over USART0 TX buffer chars.
          {
            while (!(UCSR0A & 1 << UDRE0)) // While UDR0 isn't clear:
              asm("NOP");
            // Do nothing.
            UDR0 = usart0_tx_buffer[i++]; // Transmit character via USART0.
          }
        memset(usart0_tx_buffer, 0x0, sizeof(usart0_tx_buffer)); // Clear USART0 TX Buffer.
      }
  if (strlen(usart0_rx_buffer) < 1) // If USART0 RX buffer is empty,
    {
      while (!(UCSR0A & 1 << UDRE0)) // While UDR0 isn't clear:
        asm("NOP");
      // Do nothing.
      UDR0 = '\r'; // Transmit EOL character via USART0.
    }
  else
    memset(usart0_rx_buffer, 0x0, sizeof(usart0_rx_buffer)); // Clear USART0 RX buffer.
}

/**
 * USART0 Receive character interrupt.
 */
void
USART0_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART0_RX_vect(void)
{
  // Turn off USART0 RX interrupt.
  UCSR0B |= (0 << RXCIE0);

  // Read char from UDR0.
  unsigned char c = UDR0;

  // If char SBI is set, add to USART0 RX bufer.
  if (c & (1 << 0))
    {
      appendToString(c, usart0_rx_buffer);
    }
  else // Otherwise, process instruction:
    {
      processInstruction(c);
    }
  UCSR0B |= (1 << RXCIE0); // Turn RX interrupt back on.
}

/**
 * USART1 Receive character interrupt.
 */
void
USART1_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART1_RX_vect(void)
{
  // Turn off USART1 RX interrupt.
  UCSR1B |= (0 << RXCIE1);

  // Read char from UDR1.
  unsigned char c = UDR1;

  appendToString(c, usart1_rx_buffer);

  uint16_t index;
  if (c == '\r') // If char is EOL,
    { // Transmit USART1 RX buffer:
      index = 0; // Initialise counter.
      while (usart1_rx_buffer[index] != '\0') // Iterate over USART1 RX buffer.
        {
          while (!(UCSR0A & 1 << UDRE0)) // While UDR0 isn't clear:
            asm ("NOP");
          // Do nothing.
          UDR0 = usart1_rx_buffer[index++]; // Transmit character via USART0.
        }
      memset(usart1_rx_buffer, 0x0, sizeof(usart1_rx_buffer)); // Clear USART1 RX buffer
    }
  UCSR1B |= (1 << RXCIE1); // Turn USART1 RX interrupt back on.
}
