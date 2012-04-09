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

package openmicrolabs.settings;

import java.util.ArrayList;

import openmicrolabs.signals.OMLSignal;

/**
 * This class contains a representation of the active ports on the slave
 * microcontroller along with the data types that they represent.
 * 
 * @author Chris Cummins
 * 
 */
public class Datamask
{
	private final OMLSignal[] SIGNALS;
	private final OMLSignal[] ACTIVESIGNALS;
	private final char CHAR;

	/**
	 * Produces a Datamask object from the given argument.
	 * 
	 * @param signals
	 *            An array of signal types, one representing each pin.
	 *            <code>null</code> if the pin is not active.
	 */
	public Datamask (OMLSignal[] signals)
	{
		this.SIGNALS = signals;
		this.ACTIVESIGNALS = getActiveSignals ();
		this.CHAR = getChar ();
	}

	/**
	 * Returns an array of signal types, <code>null</code> entries indicate
	 * inactive pins.
	 * 
	 * @return Array of Signals, with <code>null</code> entries.
	 */
	public OMLSignal[] signals ()
	{
		return SIGNALS;
	}

	/**
	 * Returns an array of only the active signals. Note that index values
	 * within this array may not match those of the pin channels, as inactive
	 * pins are stripped.
	 * 
	 * @return Array of Signals.
	 */
	public OMLSignal[] activeSignals ()
	{
		return ACTIVESIGNALS;
	}

	/**
	 * Returns
	 * <code>true<code> if selected pin is active, else <code>false</code>.
	 * 
	 * @param n
	 *            Pin index to check.
	 * @return <code>true</code> or <code>false</code>.
	 */
	public boolean pin (int n)
	{
		if (SIGNALS[n] != null)
			return true;
		else
			return false;
	}

	/**
	 * Returns the signal at the specified pin index, or <code>null</code> if
	 * pin is inactive.
	 * 
	 * @param n
	 *            Pin index to check.
	 * @return OMLSignal.
	 */
	public OMLSignal signal (int n)
	{
		return SIGNALS[n];
	}

	/**
	 * Returns the character which represents the correct data request for this
	 * datamask, to be transmitted to the microcontroller.
	 * 
	 * @return character.
	 */
	public char asciiChar ()
	{
		return CHAR;
	}

	/*
	 * Strips the SIGNALS array of null entries and returns.
	 * 
	 * @return Array of Signals.
	 */
	private OMLSignal[] getActiveSignals ()
	{
		ArrayList<OMLSignal> activeSignals = new ArrayList<OMLSignal> ();
		for (OMLSignal signal : SIGNALS)
			if (signal != null)
				activeSignals.add (signal);
		OMLSignal[] array = new OMLSignal[activeSignals.size ()];
		activeSignals.toArray (array);

		return array;
	}

	/*
	 * Iterates over SIGNALS and produces an ascii char data request code.
	 * 
	 * @return Ascii char.
	 */
	private char getChar ()
	{
		String binary = "";
		for (OMLSignal signal : SIGNALS)
			if (signal != null)
				binary += "1";
			else
				binary += "0";

		return (char) Integer.parseInt (binary, 2);
	}

}
