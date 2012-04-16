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

package ac.aston.oml.model.com;

import ac.aston.oml.model.com.signals.OMLSignal;

import java.util.ArrayList;

/**
 * This class contains a representation of the active ports on the slave
 * microcontrollers along with the data types that they represent.
 * 
 * @author Chris Cummins
 * 
 */
public class Datamask {
	private final OMLSignal[] signals;
	private final OMLSignal[] activeSignals;
	private final char comChar;

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
		this.comChar = getChar();
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
	public final char asciiChar() {
		return comChar;
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
	 * Iterates over signals and produces an ascii char data request code.
	 */
	private char getChar() {
		String binary = "";
		for (OMLSignal signal : signals) {
			if (signal != null) {
				binary += "1";
			} else {
				binary += "0";
			}
		}

		// TODO: Proper char creation.

		return (char) Integer.parseInt(binary + "0", 2);
	}

}
