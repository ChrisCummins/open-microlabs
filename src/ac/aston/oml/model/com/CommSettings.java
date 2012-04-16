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

	private final String portNameVal;
	private final int baudRateVal;
	private final int dataBitsVal;
	private final int stopBitsVal;
	private final int parityVal;
	private final int flowControlVal;

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
	public CommSettings(final String portName, final int baudRate,
			final int dataBits, final int stopBits, final int parity,
			final int flowControl) {
		this.portNameVal = portName;
		this.baudRateVal = baudRate;
		this.dataBitsVal = dataBits;
		this.stopBitsVal = stopBits;
		this.parityVal = parity;
		this.flowControlVal = flowControl;
	}

	/**
	 * Get port name.
	 * 
	 * @return String.
	 */
	public final String portName() {
		return portNameVal;
	}

	/**
	 * Get baud rate.
	 * 
	 * @return Int.
	 */
	public final int baudRate() {
		return baudRateVal;
	}

	/**
	 * Get data bits.
	 * 
	 * @return Int.
	 */
	public final int dataBits() {
		return dataBitsVal;
	}

	/**
	 * Get stop bits.
	 * 
	 * @return Int.
	 */
	public final int stopBits() {
		return stopBitsVal;
	}

	/**
	 * Get parity.
	 * 
	 * @return Int.
	 */
	public final int parity() {
		return parityVal;
	}

	/**
	 * Get flow control.
	 * 
	 * @return Int.
	 */
	public final int flowControl() {
		return flowControlVal;
	}

}
