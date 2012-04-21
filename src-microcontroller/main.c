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
#define AREF_AVCC (1<<REFS0)            // Vcc (5V).
#define ADC_PRESCALE_127 ((1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0))
#define DESIRED_UBRR0 UBRR16_38400
#define DESIRED_UBRR1 UBRR16_38400
#define DESIRED_AREF AREF_AVCC
#define DESIRED_ADC_PRESCALE ADC_PRESCALE_127

// Char buffer used for storing ADC values for transmission.
static char usart0_tx_buffer[5];

// Char buffer used for received data request slave instructions.
static char usart0_rx_buffer[28];

// Char buffer used for storing received data request responses from slaves.
static char usart1_rx_buffer[980];

/**
 * Start an ADC conversion and wait until finished.
 */
void
performADC()
{
  // Start ADC.
  ADCSRA |= (1 << ADSC);

  // While ADC is in progress, do nothing.
  while (ADCSRA & (1 << ADSC))
    {
      asm ("NOP");
    }
}

void
appendToString(char *string, char c)
{
  // Get string last char index.
  uint16_t index = strlen(string);

  // Add char to string at index.
  string[index] = c;

  // Add null char to new end of string.
  string[index + 1] = '\0';
}

void
txCharUSART0(char c)
{
  while (!(UCSR0A & 1 << UDRE0))
    {
      asm("NOP");
    }
  UDR0 = c;
}

void
txStringUSART0(char *string, int index)
{
  index = 0;
  while (string[index] != '\0')
    {
      txCharUSART0(string[index++]);
    }
}

void
txCharUSART1(char c)
{
  while (!(UCSR1A & 1 << UDRE1))
    {
      asm("NOP");
    }
  UDR1 = c;
}

void
txStringUSART1(char *string, int index)
{
  index = 0;
  while (string[index] != '\0')
    {
      txCharUSART1(string[index++]);
    }
}

void
processInstruction(char c)
{
  // Iterate over ADC channels [1, 7].
  uint8_t index;
  for (index = 0x01; index < 0x08; index++)
    {
      // If instruction bit 'index' set high:
      if (c & (1 << index))
        {
          // Set ADC channel to 'index'.
          ADMUX = (ADMUX & 0xE0) | (index &= 0x1F);

          performADC();

          // Put result in USART0 TX buffer.
          sprintf(usart0_tx_buffer, "%d,", ADC);

          // Transmit USART0 TX buffer.
          uint8_t i;
          txStringUSART0(usart0_tx_buffer, i);
        }
    }
}

/**
 * USART0 Receive character interrupt.
 */
void
USART0_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART0_RX_vect(void)
{
  // Read char from UDR0.
  unsigned char c = UDR0;

  if (c & (1 << 0))
    {
      // Add to USART0 RX bufer:
      appendToString(usart0_rx_buffer, c);
    }
  else
    {
      if (strlen(usart0_rx_buffer) > 0)
        {
          // Disable SIB for last char.
          uint8_t index = strlen(usart0_rx_buffer) - 1;
          usart0_rx_buffer[index] &= 0xFE;
          txStringUSART1(usart0_rx_buffer, index);
        }

      processInstruction(c);

      // If USART0 RX buffer is empty (no slave instructions).
      if (strlen(usart0_rx_buffer) < 1)
        {
          // Transmit EOL.
          txCharUSART0('\r');
        }
      else
        {
          // Clear USART0 RX buffer.
          memset(usart0_rx_buffer, 0x0, sizeof(usart0_rx_buffer));
        }
    }
}

/**
 * USART1 Receive character interrupt.
 */
void
USART1_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART1_RX_vect(void)
{
  // Read char from UDR1.
  unsigned char c = UDR1;

  appendToString(usart1_rx_buffer, c);

  // If char is EOL,
  if (c == '\r')
    {
      // Transmit USART1 RX buffer:
      uint16_t index;
      txStringUSART0(usart1_rx_buffer, index);

      // Clear USART1 RX buffer.
      memset(usart1_rx_buffer, 0x0, sizeof(usart1_rx_buffer));
    }
}

int
main(void)
{
  // Set USART bauds.
  UBRR0 = DESIRED_UBRR0;
  UBRR1 = DESIRED_UBRR1;

  // Enable rx/tx/rxi for USART0 and USART1.
  UCSR0B |= (1 << RXEN0) | (1 << TXEN0) | (1 << RXCIE0);
  UCSR1B |= (1 << RXEN1) | (1 << TXEN1) | (1 << RXCIE1);

  // Setup ADC AREF.
  ADMUX = DESIRED_AREF;

  // Set ADC Prescale.
  ADCSRA |= (DESIRED_ADC_PRESCALE | (1 << ADEN) | (1 << ADSC));

  // Set free running mode.
  ADCSRB = 0x00;

  // Perform Dummy read of ADC.
  performADC();

  // Enable interrupts.
  sei();

  // Do nothing.
  while (1)
    {
      asm("NOP");
    }
}
