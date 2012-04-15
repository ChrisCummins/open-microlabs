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

package ac.aston.oml.model;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import ac.aston.oml.model.com.CommSettings;
import ac.aston.oml.model.com.SerialReader;

/**
 * @author Chris Cummins
 * 
 */
public interface ComGateway
{
	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * 
	 * @return <code>true</code> if connection is established, else
	 *         <code>false</code>.
	 * @throws IOException
	 *             In case of IO error
	 * @see ModelGateway#setCommSettings(CommSettingsView) .
	 */
	public boolean commTest () throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException;

	public void commConnect () throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException;

	public void refreshCommPorts ();

	/**
	 * Set the comm settings for serial communications with the microcontroller.
	 * 
	 * @param c
	 *            CommSettingsView.
	 * @see ac.aston.oml.model.com.CommSettings#CommSettings()
	 */
	public void setCommSettings (CommSettings c);
	
	public void setSerialReader (SerialReader r);

	public Object[][] getCommPorts ();

	/**
	 * Returns the set CommSettingsView.
	 * 
	 * @return CommSettingsView currently in use.
	 * @see ac.aston.oml.model.com.CommSettings#CommSettings()
	 */
	public CommSettings getCommSettings ();

	public SerialReader getSerialReader ();

}
