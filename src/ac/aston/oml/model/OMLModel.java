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

package ac.aston.oml.model;

import ac.aston.oml.model.com.OMLComGateway;
import ac.aston.oml.model.logger.OMLLoggerGateway;
import ac.aston.oml.model.settings.OMLDefaultSettings;
import ac.aston.oml.model.settings.OMLSettings;

/**
 * This implementation of the Model interface performs the necessary roles of an
 * Open MicroLabs model.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLModel implements ModelGateway {
	private final OMLComGateway c;
	private final OMLLoggerGateway l;

	public OMLModel() {
		c = new OMLComGateway();
		l = new OMLLoggerGateway();
	}

	@Override
	public ComGateway com() {
		return c;
	}

	@Override
	public LoggerGateway logger() {
		return l;
	}

	public OMLSettings getOMLSettings() {
		return OMLDefaultSettings.get();
	}

}
