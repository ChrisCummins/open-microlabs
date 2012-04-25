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

package ac.openmicrolabs.controller;

import ac.openmicrolabs.view.ViewGateway;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

/**
 * This controller class catches Exceptions thrown by the model during an
 * attempt to connect to a comm port. It then interprets this exception and
 * reacts accordingly.
 * 
 * @author Chris Cummins
 */
public final class CommExceptionResponse {

	/**
	 * Protect class from instantiation.
	 */
	protected CommExceptionResponse() {
	}

	/**
	 * Catch an exception and react.
	 * 
	 * @param v
	 *            ViewGateway for displaying error messages.
	 * @param portName
	 *            The name of the port that connection attempt was to.
	 * @param e
	 *            The Exception thrown.
	 */
	public static void catchException(final ViewGateway v,
			final String portName, final Throwable e) {
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
