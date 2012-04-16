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

import ac.aston.oml.model.settings.OMLSettings;

/**
 * This interface specifies the required behaviour of a model class for the Open
 * MicroLabs software. It handles data flow to and from the controller and is
 * responsible for serial communications with the microcontroller along with
 * receiving, interpreting and storing the read data.
 * 
 * @author Chris Cummins
 * 
 */
public interface ModelGateway {

	public ComGateway com();

	public LoggerGateway logger();

	public OMLSettings getOMLSettings();

}
