/* Chris Cummins - 14 Apr 2012
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

import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This implementation of the ActionListener interface is responsible for
 * refreshing the list of available comm ports.
 * 
 * @author Chris Cummins
 * 
 */
public class CommSettingsRefreshPortsListener implements ActionListener {

	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * Create a new Action Listener.
	 * 
	 * @param model
	 *            Model Gateway for refreshing comm ports.
	 * @param view
	 *            View Gateway for setting new comm ports.
	 */
	public CommSettingsRefreshPortsListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {
		// Update record state data.
		m.com().refreshCommPorts();

		// Set new screen state data.
		v.cs().setComOptions(m.com().getCommPorts()[0]);
	}

}
