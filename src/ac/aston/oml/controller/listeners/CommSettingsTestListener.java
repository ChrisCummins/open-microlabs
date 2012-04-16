/* Chris Cummins - 9 Apr 2012
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

package ac.aston.oml.controller.listeners;

import ac.aston.oml.controller.CommExceptionResponse;
import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.com.CommSettings;
import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This implementation of the ActionListener interface is responsible for
 * handling test connection requests from the view.
 * 
 * @author Chris Cummins
 * 
 */
public class CommSettingsTestListener implements ActionListener {

	private final ModelGateway m;
	private final ViewGateway v;
	private final OMLSettings c;

	/**
	 * Constructs a new Action Listener.
	 * 
	 * @param model
	 *            Model gateway for testing connection.
	 * @param view
	 *            View gateway for display results.
	 */
	public CommSettingsTestListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;

		this.c = m.getOMLSettings();
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {

		// Get screen state data.
		final String portName = (String) m.com().getCommPorts()[1][v.cs()
				.getSelectedComOption()];
		final int baudrate = (int) c.getBaudOptions()[v.cs()
				.getSelectedBaudOption()];
		final int databits = (int) c.getDatabitOptions()[1][v.cs()
				.getSelectedDataOption()];
		final int stopbits = (int) c.getStopbitOptions()[1][v.cs()
				.getSelectedStopOption()];
		final int paritybits = (int) c.getParityOptions()[1][v.cs()
				.getSelectedParityOption()];
		final int flowbits = (int) c.getFlowOptions()[1][v.cs()
				.getSelectedFlowOption()];

		// Create session state data.
		final CommSettings com = new CommSettings(portName, baudrate, databits,
				stopbits, paritybits, flowbits);

		// Update record state data.
		try {
			m.com().setCommSettings(com);

			// Perform test.
			if (m.com().commTest()) {
				v.showMessage("Test succeeded!");
			} else {
				v.showError("Test failed on " + portName
						+ "! No response from microcontroller.");
			}
		} catch (Throwable t) {
			CommExceptionResponse.catchException(v, portName, t);
		}

	}
}
