/* Chris Cummins - 8 Apr 2012
 *
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

package ac.openmicrolabs.model.com;

import ac.openmicrolabs.model.com.signals.OMLSignal;

import java.util.ArrayList;

/**
 * This class contains a representation of the active ports on the slave
 * microcontrollers along with the data types that they represent.
 * 
 * @author Chris Cummins
 * 
 */
public class Datamask {

	private static final int BIN_0 = 0;
	private static final int BIN_1 = 1;
	private static final int BIN_OFFSET = 128;
	private static final int BYTE_BITS = 8;
	private static final int PIN_BITS = 7;
	private static final int SBI_BITS = BYTE_BITS - PIN_BITS;

	private final OMLSignal[] signals;
	private final OMLSignal[] activeSignals;
	private final char[] comChar;

	/**
	 * Produces a Datamask object from the given argument.
	 * 
	 * @param signalMask
	 *            An array of signal types, one representing each pin.
	 *            <code>null</code> if the pin is not active.
	 */
	public Datamask(final OMLSignal[] signalMask) {
		this.signals = signalMask;
		this.activeSignals = getActiveSignals();
		this.comChar = getCharArray();
	}

	/**
	 * Returns an array of signal types, <code>null</code> entries indicate
	 * inactive signals.
	 * 
	 * @return Array of Signals, <code>null</code> entries possible.
	 */
	public final OMLSignal[] signals() {
		return signals;
	}

	/**
	 * Returns an array of strings, <code>null</code> entries indicate inactive
	 * signals.
	 * 
	 * @return Array of Strings, <code>null</code> entries possible.
	 */
	public final String[] signalsToString() {
		String[] s = new String[signals.length];

		for (int i = 0; i < s.length; i++) {
			if (signals[i] != null) {
				s[i] = signals[i].name();
			} else {
				s[i] = null;
			}
		}

		return s;
	}

	/**
	 * Returns an array of only the active signals. Note that index values
	 * within this array may not match those of the pin channels, as inactive
	 * pins are stripped.
	 * 
	 * @return Array of Signals.
	 */
	public final OMLSignal[] activeSignals() {
		return activeSignals;
	}

	/**
	 * Returns <code>true</code> if selected pin is active, else
	 * <code>false</code>.
	 * 
	 * @param n
	 *            Pin index to check.
	 * @return <code>true</code> or <code>false</code>.
	 */
	public final boolean pin(final int n) {
		return (signals[n] != null);
	}

	/**
	 * Returns the signal at the specified pin index, or <code>null</code> if
	 * pin is inactive.
	 * 
	 * @param n
	 *            Pin index to check.
	 * @return OMLSignal, <code>null</code> possible.
	 */
	public final OMLSignal signal(final int n) {
		return signals[n];
	}

	/**
	 * Returns the character which represents the correct data request for this
	 * datamask, to be transmitted to the microcontroller.
	 * 
	 * @return character.
	 */
	public final char[] asciiChar() {
		return comChar;
	}

	/**
	 * Returns the com data request as a binary string, with individual bytes
	 * separated by a space.
	 * 
	 * @return Binary string.
	 */
	public final String binaryCode() {
		StringBuilder binary = new StringBuilder();

		// Iterate over characters within data request.
		for (Character c : comChar) {
			// Iterate over bytes within char.
			for (byte b : c.toString().getBytes()) {
				int val = b;
				for (int i = 0; i < PIN_BITS + SBI_BITS; i++) {
					// Append either BIN_0 or BIN_1 to binary string.
					binary.append((val & BIN_OFFSET) == 0 ? BIN_0 : BIN_1);
					val <<= 1;
				}
				binary.append(' ');
			}
		}

		// Return binary string, with last space removed.
		return binary.toString().substring(0, binary.length() - 1);
	}

	/*
	 * Strips the signals array of null entries and returns.
	 */
	private OMLSignal[] getActiveSignals() {
		ArrayList<OMLSignal> active = new ArrayList<OMLSignal>();
		for (OMLSignal signal : signals) {
			if (signal != null) {
				active.add(signal);
			}
		}

		OMLSignal[] array = new OMLSignal[active.size()];
		active.toArray(array);

		return array;
	}

	/*
	 * Iterates over signals and produces an array of char data request codes.
	 */
	private char[] getCharArray() {
		// Setup local variable.
		String characters = "";

		// Iterate over sets of signals.
		for (int i = 0; i < (signals.length / PIN_BITS); i++) {
			String binary = "";

			// Iterate over individual signals within set.
			for (int j = 0; j < PIN_BITS; j++) {
				if (signals[(i * PIN_BITS) + j] != null) {
					binary += "1";
				} else {
					binary += "0";
				}
			}

			// Add trailing SBI bit.
			if (i == (signals.length / PIN_BITS) - 1) {
				binary += "0";
			} else {
				binary += "1";
			}

			// TODO: Remove debugging.
			System.out.println((char) Integer.parseInt(binary, 2) + ": "
					+ binary);

			// Convert code into one byte.
			characters += (char) Integer.parseInt(binary, 2);
		}

		return characters.toCharArray();
	}
}
