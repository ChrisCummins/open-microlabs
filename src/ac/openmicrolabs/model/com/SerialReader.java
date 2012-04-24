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

import ac.openmicrolabs.include.OMLAppDetails;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import com.jcummins.serial.SerialComm;

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
public class SerialReader extends SerialComm {

	private static final long DEFAULT_SLEEP_TIME = 100;
	private static final char[] TEST_CONNECT_CHAR = { (char) 127 };

	private final CommSettings commSettings;

	private long sleepTime = DEFAULT_SLEEP_TIME;

	/**
	 * Creates an instance of superclass SerialComm.
	 * 
	 * @param c
	 *            CommSettingsView to connect with.
	 */
	public SerialReader(final CommSettings c) {
		super(c.portName(), c.baudRate(), c.dataBits(), c.stopBits(), c
				.parity(), c.flowControl());
		this.commSettings = c;
	}

	/**
	 * Sends the argument to the connected serial port transmitter, then sleeps
	 * for the time specified with sleepTime before returning the contents of
	 * the serial read buffer.
	 * 
	 * @param dataRequest
	 *            Char array to be transmitted.
	 * @return String buffer.
	 * @throws IOException
	 *             In case of IO error.
	 */
	public final String sendDataRequest(final char[] dataRequest)
			throws IOException {
		for (char c : dataRequest) {
			super.write(c);
		}

		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// Don't care.
		}

		final String s = super.read();

		return s;
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
	 * @throws UnsupportedCommOperationException
	 *             If port is not supported.
	 * @throws PortInUseException
	 *             If port is in use.
	 * @throws NoSuchPortException
	 *             If port is not found.
	 * @see SerialReader#sendDataRequest(char)
	 */
	public final boolean testConnection() throws IOException,
			NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException {
		super.connect(OMLAppDetails.name());

		String s = sendDataRequest(TEST_CONNECT_CHAR);
		super.close();

		return (s.length() > 0);
	}

	/**
	 * Sets a new sleep time (ms), for use in serial comm reads.
	 * 
	 * @param s
	 *            Sleep time (ms).
	 * @see SerialReader#sendDataRequest(char)
	 */
	public final void setSleepTime(final long s) {
		this.sleepTime = s;
	}

	/**
	 * Returns the CommSettingsView currently in use.
	 * 
	 * @return CommSettingsView.
	 */
	public final CommSettings getCommSettings() {
		return commSettings;
	}

	/**
	 * Returns the sleep time currently in use.
	 * 
	 * @return Sleep time (ms).
	 */
	public final long getSleepTime() {
		return sleepTime;
	}

}
