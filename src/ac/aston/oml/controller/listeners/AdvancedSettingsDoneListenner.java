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

package ac.aston.oml.controller.listeners;

import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.logger.AdvancedSettings;
import ac.aston.oml.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This implementation of the ActionListener interface is responsible for
 * getting Advanced Settings from the AdvancedSettingsView frame and setting
 * those to the model. Additionally, it will interpret any exceptions thrown and
 * feed those back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class AdvancedSettingsDoneListenner implements ActionListener {
	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * Construct an new listener.
	 * 
	 * @param model
	 *            Model Gateway for setting Advanced Settings to.
	 * @param view
	 *            View Gateway for getting user input and showing errors.
	 */
	public AdvancedSettingsDoneListenner(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		// Set up local variables.
		Double minY;
		Double maxY;

		// Convert text boxes to numeric values.
		try {
			minY = Double.parseDouble(v.as().getMinYText());
			maxY = Double.parseDouble(v.as().getMaxYText());
		} catch (NumberFormatException e1) {
			v.showError("Y axis values must be a decimal value!");
			return;
		}

		// Check validity of values.
		if (maxY < minY) {
			v.showError("Maximum Y value should be greater than minimum!");
			return;
		}

		// Get graph time range.
		final Double graphTimeRange = (Double) m.getOMLSettings()
				.getGraphTimeRangeOptions()[1][v.as()
				.getTimeRangeSelectedIndex()];

		// Construct AdvancedSettings from obtained data.
		final AdvancedSettings a = new AdvancedSettings(graphTimeRange, minY,
				maxY);

		// Set AdvancedSettings to model and hide frame.
		m.logger().setAdvancedSettings(a);
		v.as().fetchFrame().setVisible(false);
	}

}
