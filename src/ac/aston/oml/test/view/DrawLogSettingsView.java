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

package ac.aston.oml.test.view;

import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.test.OMLTestSettings;
import ac.aston.oml.view.gui.OMLLogSettingsView;
import jcummins.gui.GUITools;

/**
 * Testing class for OMLCommSettingsView.
 * 
 * @author Chris Cummins
 * @see ac.aston.oml.view.gui.OMLLogSettingsView
 */
public class DrawLogSettingsView {

	/**
	 * Renders a OMLCommSettingsView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main(String[] args) {
		GUITools.setNativeLookAndFeel();
		final OMLSettings c = OMLTestSettings.getOMLSettings();
		final String[] s = { "1 Microcontroller", "2 Microcontrollers" };

		final OMLLogSettingsView frame = new OMLLogSettingsView();
		frame.init(c.fontset, 14, OMLTestSettings.signalTypes);
		frame.setSlaveBoxOptions(s);

		frame.setFilepathLabel("C:\\test");
		System.out.println(frame.getFilepathText());

		frame.setPincount(21);

		GUITools.centreFrame(frame);
		frame.setVisible(true);
	}

}
