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

package ac.openmicrolabs.controller.listeners;

import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This implementation of the ActionListener interface is responsible for
 * receiving logging cancel requests form the user and so updating the view and
 * model accordingly.
 * 
 * @author Chris Cummins
 * 
 */
public class LoggerDoneListener implements ActionListener {

	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * Constructs a new ActionListener.
	 * 
	 * @param model
	 *            Model Gateway for stopping logging.
	 * @param view
	 *            View Gateway for updating view.
	 */
	public LoggerDoneListener(final ModelGateway model, final ViewGateway view) {
		this.m = model;
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		// Check record state for logging.
		if (m.logger().isLogging()) {
			if (v.showYesNoPrompt("Readings in progress!\nAre you sure you "
					+ "would like to exit?")) {
				m.logger().stopLogging();
				returnToLogSettings();
			} else {
				return;
			}
		} else {
			returnToLogSettings();
		}
	}

	/*
	 * Return from LoggerView to LogSettingsView.
	 */
	private void returnToLogSettings() {
		v.lv().teardown();
		v.ls().fetchFrame().setVisible(true);
	}

}
