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

package ac.aston.oml.model.com;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import jcummins.serial.DiscoverPorts;

import ac.aston.oml.model.ComGateway;

/**
 * @author Chris Cummins
 * 
 */
public class OMLComGateway implements ComGateway
{

	private SerialReader serialReader;
	private Object[][] o;

	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * 
	 * @throws UnsupportedCommOperationException
	 * @throws PortInUseException
	 * @throws NoSuchPortException
	 * 
	 */
	@Override
	public boolean commTest () throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException
	{
		return serialReader.testConnection ();
	}

	@Override
	public void commConnect () throws NoSuchPortException, PortInUseException,
			UnsupportedCommOperationException, IOException
	{
		serialReader.connect (ac.aston.oml.include.AppDetails.name ());
	}

	@Override
	public void refreshCommPorts ()
	{
		if (DiscoverPorts.size () > 0)
		{
			o = new Object[2][DiscoverPorts.size ()];
			o[0] = DiscoverPorts.listToArray ();
			o[1] = DiscoverPorts.listNamesToArray ();
		} else
		{
			o = new Object[2][1];
			o[0][0] = "-NONE-";
			o[1][0] = "-NONE-";
		}
	}

	@Override
	public void setCommSettings (CommSettings c)
	{
		serialReader = new SerialReader (c);
	}

	public void setSerialReader (SerialReader r)
	{
		this.serialReader = r;
	}
	
	@Override
	public Object[][] getCommPorts ()
	{
		return o;
	}

	@Override
	public CommSettings getCommSettings ()
	{
		return null;
	}
	
	public SerialReader getSerialReader ()
	{
		return serialReader;
	}
}
