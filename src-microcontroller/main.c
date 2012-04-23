/* main.c - Main program file for AVR microcontrollers.
 * Version 0.8.0 - 23rd April 2012.
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

// INCLUDES ====================================================================

#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdio.h>
#include <string.h>

// MACROS ======================================================================

/* USER CONFIG
 * BAUD_VALUE           Baud value for the desired baud rate.
 * AREF_VALUE           ADC reference voltage to use.
 * ADC_PRESCALE         ADC prescale to use.
 */
#define BAUD_VALUE 0x19                 // 38400b/s @ 16MHZ (0.160% error).
#define AREF_VALUE (1<<REFS0)           // Vcc (5V).
#define ADC_PRESCALE ((1<<ADPS2)|(1<<ADPS1)|(1<<ADPS0)) // PRESCALE 127.
/* INSTRUCTION BITS
 * SIB                  Slave instruction bit.
 * SIB_OFF_MASK         Value to set SIB low when char &= SIB_OFF_MASK.
 */
#define SIB 0
#define SIB_OFF_MASK 0xFE

/* INTERRUPT FLAGS
 * UDR0_FLAG            Set by USART0_RX_vect to show that udr0 has been updated.
 * UDR1_FLAG            Set by USART1_RX_vect to show that udr1 has been updated.
 */
#define UDR0_FLAG 0
#define UDR1_FLAG 1

// GLOBAL VARIABLES ============================================================

/* USART BUFFERS
 * usart0_tx_buffer     Char buffer used for storing formatted ADC values.
 * usart0_rx_buffer     Char buffer used for received data request slave
 *                      instructions.
 * usart1_rx_buffer     Char buffer used for storing received data request
 *                      responses from slaves.
 */
static char usart0_tx_buffer[5];
static char usart0_rx_buffer[14];
static char usart1_rx_buffer[490];

/* STATUS INTEGER
 * The 8-bit unsigned integer acts as a status register for interrupts. See
 * interrupt flags.
 */
volatile static uint8_t sint;

/* UDR CHARACTERS
 * Characters used for storing values from UDR0 and UDR1 respectively.
 */
static char udr0;
static char udr1;

// FUNCTIONS ===================================================================

/**
 * Start an ADC conversion and wait until finished.
 */
static inline __attribute__((always_inline))
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

/**
 * Transmit a character through USART port.
 *
 * @param c             Character to transmit.
 * @param *udr          UDR port address.
 * @param *ucsra        UCSRA register address.
 * @param udre_bit      UDRE bit index within UCSRA.
 */
static inline __attribute__((always_inline))
void
txCharUSART(char c, volatile uint8_t *udr_address,
    volatile uint8_t *ucsra_address, uint8_t udre_bit)
{
  while (!(*ucsra_address & 1 << udre_bit))
    {
      asm("NOP");
    }
  *udr_address = c;
}

/**
 * Transmit a string through USART port.
 *
 * @param *string       Character array to transmit.
 * @param index         Integer used to traverse the string.
 * @param *udr          UDR port address.
 * @param *ucsra        UCSRA register address.
 * @param udre_bit      UDRE bit index within UCSRA.
 */
static inline __attribute__((always_inline))
void
txStringUSART(char *string, int index, volatile uint8_t *udr,
    volatile uint8_t *ucsra, uint8_t udre_bit)
{
  index = 0;
  while (string[index] != '\0')
    {
      txCharUSART(string[index++], udr, ucsra_address, udre_bit);
    }
}

/**
 * Iterate over each bit of a char and perform ADCs and transmit as required.
 *
 * @param c Instruction char to process.
 */
static inline __attribute__((always_inline))
void
localInstruction(char c)
{
  // Iterate over ADC channels [1, 7].
  uint8_t index;
  for (index = 0x01; index < 0x08; index++)
    {
      // If instruction bit 'index' is set:
      if (c & (1 << index))
        {
          // Set ADC channel to 'index'.
          ADMUX = (ADMUX & 0xE0) | (index &= 0x1F);

          performADC();

          // Put result in USART0 TX buffer.
          sprintf(usart0_tx_buffer, "%d,", ADC);

          // Transmit USART0 TX buffer.
          uint8_t i;
          txStringUSART(usart0_tx_buffer, i, &UDR0, &UCSR0A, UDRE0);
        }
    }
}

/**
 * Process USART0_FLAG. If udr0 SIB is set, the char is added to
 * usart0_rx_buffer. Else, it processes the instruction, transmits an EOL if
 * necessary, else transmits slave instructions.
 *
 * @param c Instruction char to process.
 */
static inline __attribute__((always_inline))
void
usart0Flag(char c)
{
  // If SIB.
  if (c & (1 << SIB))
    {
      // Add to USART0 RX bufer:
      usart0_rx_buffer[strlen(usart0_rx_buffer)] = c;
    }
  else
    {
      localInstruction(c);

      // If USART0 RX buffer is empty (no slave instructions).
      if (strlen(usart0_rx_buffer) < 1)
        {
          // Transmit EOL.
          txCharUSART('\r', &UDR0, &UCSR0A, UDRE0);
        }
      else
        {
          // Disable SIB for last char.
          uint8_t index = strlen(usart0_rx_buffer) - 1;
          usart0_rx_buffer[index] &= SIB_OFF_MASK;

          // Transmit slave instructions.
          txStringUSART(usart0_rx_buffer, index, &UDR1, &UCSR1A, UDRE1);

          // Clear USART0 RX buffer.
          memset(usart0_rx_buffer, 0x0, sizeof(usart0_rx_buffer));
        }
    }
}

/**
 * Process USART1_FLAG. Adds char to usart1_rx_buffer, then transmits buffer
 * contents if char is EOL.
 *
 * @param c Slave response character to process.
 */
static inline __attribute__((always_inline))
void
usart1Flag(char c)
{
  usart1_rx_buffer[strlen(usart1_rx_buffer)] = c;

  // If character is EOL.
  if (c == '\r')
    {
      // Transmit USART1 RX buffer:
      uint16_t index;
      txStringUSART(usart1_rx_buffer, index, &UDR0, &UCSR0A, UDRE0);

      // Clear USART1 RX buffer.
      memset(usart1_rx_buffer, 0x0, sizeof(usart1_rx_buffer));
    }
}

/**
 * Main method. Sets up ADC, USART1, USART0, global interrupts and then performs
 * flag checks indefinitely.
 */
int
main(void)
{
  // Setup ADC AREF.
  ADMUX = AREF_VALUE;

  // Set ADC Prescale.
  ADCSRA |= ((ADC_PRESCALE) | (1 << ADEN) | (1 << ADSC));

  // Set free running mode.
  ADCSRB = 0x00;

  // Perform Dummy read of ADC.
  performADC();

  // Set USART bauds.
  UBRR1 = BAUD_VALUE;
  UBRR0 = BAUD_VALUE;

  // Enable rx/tx/rxi for USART0 and USART1.
  UCSR1B |= (1 << RXEN1) | (1 << TXEN1) | (1 << RXCIE1);
  UCSR0B |= (1 << RXEN0) | (1 << TXEN0) | (1 << RXCIE0);

  // Enable interrupts.
  sei();

  // Do nothing.
  while (1)
    {
      // Test UDR0_FLAG.
      if (sint & (1 << UDR0_FLAG))
        {
          usart0Flag(udr0);
          sint &= (0 << UDR0_FLAG);
        }
      // TEST UDR1_FLAG.
      if (sint & (1 << UDR1_FLAG))
        {
          usart1Flag(udr1);
          sint &= (0 << UDR1_FLAG);
        }
    }
}

// INTERRUPT VECTORS ===========================================================

/**
 * USART0 Receive character interrupt: store char and set UDR0_flag.
 */
void
USART0_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART0_RX_vect(void)
{
  udr0 = UDR0;
  sint |= (1 << UDR0_FLAG);
}

/**
 * USART1 Receive character interrupt: store char and set UDR1_FLAG.
 */
void
USART1_RX_vect(void) __attribute__ ((signal,used, externally_visible));
void
USART1_RX_vect(void)
{
  udr1 = UDR1;
  sint |= (1 << UDR1_FLAG);
}
