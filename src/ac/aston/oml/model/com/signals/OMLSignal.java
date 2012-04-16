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

package ac.aston.oml.model.com.signals;

/**
 * This interface represents the raw result from a microcontroller AD
 * conversion.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLSignal {

	/**
	 * Converts an unsigned 10-bit integer [0, 1023] to a double value
	 * representing the physical value that this signal represents.
	 * 
	 * @param rawInt
	 *            Value to be converted.
	 * @return Converted value.
	 */
	public double toValue(double rawInt) {
		return rawInt;
	}

	/**
	 * Converts an unsigned 10-bit integer [0, 1023] to formatted String
	 * representing the physical value that this signal represents, with units
	 * and rounded to an appropriate number of decimal places.
	 * 
	 * @param rawInt
	 *            Value to be converted.
	 * @return Converted value as human readable string.
	 */
	public String toString(double rawInt) {
		return String.format("%04d", (int) rawInt);
	}

	/**
	 * Returns the name of the Signal as a string.
	 * 
	 * @return String.
	 */
	public String name() {
		return "Raw";
	}

}
