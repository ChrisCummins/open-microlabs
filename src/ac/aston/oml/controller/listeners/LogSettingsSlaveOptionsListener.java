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

package ac.aston.oml.controller.listeners;

import ac.aston.oml.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This implementation of the ActionListener interface is responsible for
 * getting updating the number of pins visible in the LogSettingsView.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettingsSlaveOptionsListener implements ActionListener {

	private static final int SLAVE_BITS = 1;
	private static final int PIN_BITS = 7;

	private final ViewGateway v;

	/**
	 * Constructs a new Action Listener.
	 * 
	 * @param view
	 *            View Gateway for updating screen state.
	 */
	public LogSettingsSlaveOptionsListener(final ViewGateway view) {
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		v.ls().setPincount(
				(v.ls().getSlaveBoxSelectedIndex() + SLAVE_BITS) * PIN_BITS);
	}

}
