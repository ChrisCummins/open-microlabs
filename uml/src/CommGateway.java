/* Chris Cummins - 15 Apr 2012
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

package ac.openmicrolabs.model;

import ac.openmicrolabs.model.com.CommSettings;
import ac.openmicrolabs.model.com.SerialReader;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

/**
 * The CommGateway interface details the method contract for the Serial Comms
 * Model.
 * 
 * @author Chris Cummins
 * 
 */
public interface CommGateway {

	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * 
	 * @return <code>true</code> if connection is established, else
	 *         <code>false</code>.
	 * @throws NoSuchPortException
	 *             If port was not found.
	 * @throws PortInUseException
	 *             If port is in use.
	 * @throws UnsupportedCommOperationException
	 *             If port is not supported.
	 * @throws IOException
	 *             In case of IO error.
	 */
	boolean commTest() throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException;

	/**
	 * Connects to the serial port. setCommSettings() must have been called
	 * beforehand.
	 * 
	 * @throws NoSuchPortException
	 *             If port was not found.
	 * @throws PortInUseException
	 *             If port is in use.
	 * @throws UnsupportedCommOperationException
	 *             If port is not supported.
	 * @throws IOException
	 *             In case of IO error.
	 */
	void commConnect() throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException;

	/**
	 * This method refreshes the list of available comm ports.
	 */
	void refreshCommPorts();

	/**
	 * Set the comm settings for serial communications with the microcontroller.
	 * 
	 * @param c
	 *            CommSettingsView.
	 * @see ac.openmicrolabs.model.com.CommSettings#CommSettings()
	 */
	void setCommSettings(CommSettings c);

	/**
	 * This method sets a new SerialReader to the record state.
	 * 
	 * @param r
	 *            SerialReader.
	 */
	void setSerialReader(SerialReader r);

	/**
	 * Returns a list of available comm ports.
	 * 
	 * @return 2xn Object array.
	 */
	Object[][] getCommPorts();

	/**
	 * Returns the set CommSettingsView.
	 * 
	 * @return CommSettingsView currently in use.
	 * @see ac.openmicrolabs.model.com.CommSettings#CommSettings()
	 */
	CommSettings getCommSettings();

	/**
	 * Returns the used SerialReader.
	 * 
	 * @return SerialReader.
	 */
	SerialReader getSerialReader();

}
