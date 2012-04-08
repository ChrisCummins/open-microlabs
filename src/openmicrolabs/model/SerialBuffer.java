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

package openmicrolabs.model;

import cummins.serial.SerialComm;

/**
 * This extension of the SerialComm class from the JCummins Library provides
 * Open MicroLabs specific functionality to the class. It represents the lowest
 * level communication with the microcontroller, sending data request characters
 * and then reading the response from the microcontroller after waiting for a
 * calculated amount of sleep time.
 * 
 * @author Chris Cummins
 * 
 */
public class SerialBuffer extends SerialComm
{

	public SerialBuffer (String portName, int baudRate, int dataBits,
			int stopBits, int parity, int flowControl)
	{
		super (portName, baudRate, dataBits, stopBits, parity, flowControl);
		// TODO Auto-generated constructor stub
	}

}
