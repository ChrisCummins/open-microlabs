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

package ac.openmicrolabs.test.view;

import javax.swing.SwingUtilities;

import com.jcummins.gui.GUITools;
import com.jcummins.serial.DiscoverPorts;

import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.test.OMLTestSettings;
import ac.openmicrolabs.view.gui.OMLCommSettingsView;

/**
 * Testing class for OMLCommSettingsView.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class DrawCommSettingsView {

	/**
	 * Renders a OMLCommSettingsView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main(final String[] args) {
		GUITools.setNativeLookAndFeel();

		OMLSettings c = OMLTestSettings.getOMLSettings();

		final OMLCommSettingsView frame = new OMLCommSettingsView();
		frame.init(c.getFontset());

		final String[] ports = DiscoverPorts.listToArray();
		if (ports.length < 1) {
			String[] s = { "None" };
			frame.setComOptions(s);
		} else {
			frame.setComOptions(ports);
		}
		frame.setComLabel(ports.length);

		frame.setBaudOptions(c.getBaudOptions(), c.getBaudOptionsSelected());
		frame.setDataOptions(c.getDatabitOptions()[0],
				c.getDatabitOptionsSelected());
		frame.setStopOptions(c.getStopbitOptions()[0],
				c.getStopbitOptionsSelected());
		frame.setParityOptions(c.getParityOptions()[0],
				c.getParityOptionsSelected());
		frame.setFlowOptions(c.getFlowOptions()[0], c.getFlowOptionsSelected());

		GUITools.centreFrame(frame);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				frame.setVisible(true);
			}
		});

	}

}
