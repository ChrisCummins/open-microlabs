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

package ac.aston.oml.controller;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import ac.aston.oml.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class CommExceptionResponse {

	public static void catchException(ViewGateway v, String portName,
			Throwable e) {
		if (portName == "-NONE-") {
			v.showError("No com port selected!");
		} else if (e instanceof NoSuchPortException) {
			v.showError("Unable to connect to com port, " + portName
					+ " does not exist!");
		} else if (e instanceof PortInUseException) {
			v.showError("Unable to connect to com port, " + portName
					+ " already in use!");
		} else if (e instanceof UnsupportedCommOperationException) {
			v.showError("Unable to connect to com port, " + portName
					+ " does not support this type of operation!");
		} else if (e instanceof IOException) {
			v.showError("Unable to connect to com port, " + portName
					+ " access denied!");
		} else {
			v.showError("Connection failed, unkown error!");
		}
	}
}
