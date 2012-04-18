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
import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.jcummins.gui.GUITools;


/**
 * This implementation of the ActionListener interface is responsible for
 * receiving show AdvancedSettings requests from the user and so updating the
 * view accordingly.
 * 
 * @author Chris Cummins
 * 
 */
public class AdvancedSettingsShowListener implements ActionListener {

	private final ModelGateway m;
	private final ViewGateway v;
	private final OMLSettings c;

	/**
	 * Constructs a new listener.
	 * 
	 * @param model
	 *            Model Gateway for getting settings data.
	 * @param view
	 *            View Gateway for rendering frame.
	 */
	public AdvancedSettingsShowListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;

		c = m.getOMLSettings();
	}

	@Override
	public final void actionPerformed(final ActionEvent e) {
		// Setup screen state data.
		v.as().init(c.getFontset());
		v.as().setTimeRangeOptions(c.getGraphTimeRangeOptions()[0],
				c.getGraphTimeRangeOptionsSelected());

		// Make frame visible.
		GUITools.centreFrame(v.as().fetchFrame());
		v.as().fetchFrame().setVisible(true);
	}

}
