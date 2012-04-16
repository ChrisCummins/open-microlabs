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
 * This implementation of the ModelGateway interface performs the necessary
 * roles of an Open MicroLabs ModelGateway.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLModelGateway implements ModelGateway {
	private final OMLComGateway c;
	private final OMLLoggerGateway l;

	/**
	 * Creates a new OMLModelGateway.
	 */
	public OMLModelGateway() {
		c = new OMLComGateway();
		l = new OMLLoggerGateway();
	}

	@Override
	public final CommGateway com() {
		return c;
	}

	@Override
	public final LoggerGateway logger() {
		return l;
	}

	@Override
	public final OMLSettings getOMLSettings() {
		return OMLDefaultSettings.get();
	}

}
