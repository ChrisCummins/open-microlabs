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

package ac.aston.oml.controller;

import ac.aston.oml.model.Model;
import ac.aston.oml.view.ViewGateway;

/**
 * This interface specifies the required behaviour for a Open MicroLabs
 * controller, fulfilling the roles of a controller within the MCV architecture.
 * 
 * @author Chris Cummins
 * 
 */
public interface Controller
{
	/**
	 * Initialise the Open MicroLabs program.
	 * 
	 * @param m
	 *            Model to be used.
	 * @param v
	 *            ViewGateway to be used.
	 */
	public void init (Model m, ViewGateway v);

}
