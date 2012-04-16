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

/**
 * This class contains the settings required to establish a connection between
 * the Open MicroLabs software and a microcontroller via the USART interface.
 * 
 * @author Chris Cummins
 * 
 */
public class CommSettings {

	private final String PORTNAME;
	private final int BAUDRATE;
	private final int DATABITS;
	private final int STOPBITS;
	private final int PARITY;
	private final int FLOWCONTROL;

	/**
	 * Constructs a CommSettingsView object from arguments.
	 * 
	 * @param portName
	 *            Port name to connect to.
	 * @param baudRate
	 *            The baud rate to use (b/s).
	 * @param dataBits
	 *            The number of databits to use.
	 * @param stopBits
	 *            The number of stopbits to use.
	 * @param parity
	 *            The parity to set.
	 * @param flowControl
	 *            The flow control to set.
	 * @see jcummins.serial.SerialComm#SerialComm(String, int, int, int, int,
	 *      int)
	 */
	public CommSettings(String portName, int baudRate, int dataBits,
			int stopBits, int parity, int flowControl) {
		this.PORTNAME = portName;
		this.BAUDRATE = baudRate;
		this.DATABITS = dataBits;
		this.STOPBITS = stopBits;
		this.PARITY = parity;
		this.FLOWCONTROL = flowControl;
	}

	/**
	 * Get port name.
	 * 
	 * @return String.
	 */
	public String portName() {
		return PORTNAME;
	}

	/**
	 * Get baud rate.
	 * 
	 * @return Int.
	 */
	public int baudRate() {
		return BAUDRATE;
	}

	/**
	 * Get data bits.
	 * 
	 * @return Int.
	 */
	public int dataBits() {
		return DATABITS;
	}

	/**
	 * Get stop bits.
	 * 
	 * @return Int.
	 */
	public int stopBits() {
		return STOPBITS;
	}

	/**
	 * Get parity.
	 * 
	 * @return Int.
	 */
	public int parity() {
		return PARITY;
	}

	/**
	 * Get flow control.
	 * 
	 * @return Int.
	 */
	public int flowControl() {
		return FLOWCONTROL;
	}

}
