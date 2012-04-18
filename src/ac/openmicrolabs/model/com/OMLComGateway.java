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

package ac.openmicrolabs.model.com;

import ac.openmicrolabs.model.CommGateway;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import com.jcummins.serial.DiscoverPorts;


/**
 * This implementation of the CommGateway interface provides the necessary
 * functionality to fulfill the interface contract.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLComGateway implements CommGateway {

	private SerialReader serialReader;
	private Object[][] o;

	@Override
	public final boolean commTest() throws NoSuchPortException,
			PortInUseException, UnsupportedCommOperationException, IOException {
		return serialReader.testConnection();
	}

	@Override
	public final void commConnect() throws NoSuchPortException,
			PortInUseException, UnsupportedCommOperationException, IOException {
		serialReader.connect(ac.openmicrolabs.include.AppDetails.name());
	}

	@Override
	public final void refreshCommPorts() {
		if (DiscoverPorts.size() > 0) {
			o = new Object[2][DiscoverPorts.size()];
			o[0] = DiscoverPorts.listToArray();
			o[1] = DiscoverPorts.listNamesToArray();
		} else {
			o = new Object[2][1];
			o[0][0] = "-NONE-";
			o[1][0] = "-NONE-";
		}
	}

	@Override
	public final void setCommSettings(final CommSettings c) {
		serialReader = new SerialReader(c);
	}

	@Override
	public final void setSerialReader(final SerialReader r) {
		this.serialReader = r;
	}

	@Override
	public final Object[][] getCommPorts() {
		return o;
	}

	@Override
	public final CommSettings getCommSettings() {
		return null;
	}

	@Override
	public final SerialReader getSerialReader() {
		return serialReader;
	}
}
