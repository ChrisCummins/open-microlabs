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

package openmicrolabs.signals;

/**
 * This implementation of the Signal interface represents a raw signal, with no
 * conversion performed. It is a 10-bit unsigned int in the range [0, 1023].
 * 
 * @author Chris Cummins
 * 
 */
public class OMLRaw implements Signal
{

	/**
	 * Returns the argument, since it is a raw signal and so no conversion is
	 * required.
	 */
	@Override
	public double toValue (double rawInt)
	{
		return rawInt;
	}

	/**
	 * Formats the value to a 4-digit decimal value.
	 */
	@Override
	public String toString (double rawInt)
	{
		return String.format ("%04d", rawInt);
	}

	/**
	 * Returns Raw.
	 */
	@Override
	public String name ()
	{
		return "Raw";
	}

}
