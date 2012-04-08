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

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import openmicrolabs.AppDetails;
import openmicrolabs.settings.CommSettings;
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
	private final CommSettings C;
	private long sleepTime = 100;

	/**
	 * Creates an instance of superclass SerialComm and attempts to connect to
	 * it. In case of error, an exception is thrown.
	 * 
	 * @param c
	 *            CommSettings to connect with.
	 * @throws NoSuchPortException
	 *             If port does not exist.
	 * @throws PortInUseException
	 *             If port is in use.
	 * @throws UnsupportedCommOperationException
	 *             If port does not support this type of operation.
	 * @throws IOException
	 *             In case of IO error.
	 */
	public SerialBuffer (CommSettings c) throws NoSuchPortException,
			PortInUseException, UnsupportedCommOperationException, IOException
	{
		super (c.portName (), c.baudRate (), c.dataBits (), c.stopBits (), c
				.parity (), c.flowControl ());
		this.C = c;
		super.connect (AppDetails.name ());
	}

	/**
	 * Sends the argument to the connected serial port transmitter, then sleeps
	 * for the time specified with sleepTime before returning the contents of
	 * the serial read buffer.
	 * 
	 * @param c
	 *            Char to be transmitted.
	 * @return String buffer.
	 * @throws IOException
	 *             In case of IO error.
	 */
	public String sendDataRequest (char c) throws IOException
	{
		super.write (c);
		try
		{
			Thread.sleep (sleepTime);
		} catch (InterruptedException e)
		{
			// Don't care.
		}
		return super.read ();
	}

	/**
	 * Sends a full data request (ASCII 255) to the serial transmitter, then
	 * sleeps for the time specified with sleepTime before returning
	 * <code>true</code> if information was received on the serial buffer, else
	 * <code>false</code>.
	 * 
	 * @return <code>true</code> if information was received, else
	 *         <code>false</code>.
	 * @throws IOException
	 *             In case of IO error.
	 * @see SerialBuffer#sendDataRequest(char)
	 */
	public boolean testConnection () throws IOException
	{
		String s = sendDataRequest ((char) 255);
		if (s.length () > 0)
			return true;
		else
			return false;
	}

	/**
	 * Sets a new sleep time (ms), for use in serial comm reads.
	 * 
	 * @param s
	 *            Sleep time (ms).
	 * @see SerialBuffer#sendDataRequest(char)
	 */
	public void setSleepTime (long s)
	{
		this.sleepTime = s;
	}

	/**
	 * Returns the CommSettings currently in use.
	 * 
	 * @return CommSettings.
	 */
	public CommSettings getCommSettings ()
	{
		return C;
	}
	
	/**
	 * Returns the sleep time currently in use.
	 * @return Sleep time (ms).
	 */
	public long getSleepTime ()
	{
		return sleepTime;
	}

}
