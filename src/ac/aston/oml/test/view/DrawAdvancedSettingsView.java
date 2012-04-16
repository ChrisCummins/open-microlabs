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

package ac.aston.oml.test.view;

import jcummins.gui.GUITools;

import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.test.OMLTestSettings;
import ac.aston.oml.view.gui.OMLAdvancedSettingsView;

/**
 * Testing class for OMLAdvancedSettingsView.
 * 
 * @author Chris Cummins
 */
public abstract class DrawAdvancedSettingsView {

	/**
	 * Render an AdvancedSettingsView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main(final String[] args) {
		GUITools.setNativeLookAndFeel();

		final OMLSettings c = OMLTestSettings.getOMLSettings();

		final OMLAdvancedSettingsView frame = new OMLAdvancedSettingsView();
		frame.init(c.getFontset());
		frame.setMinYText("0.0");
		frame.setMaxYText("1023.0");
		frame.setTimeRangeOptions(c.getGraphTimeRangeOptions()[0],
				c.getGraphTimeRangeOptionsSelected());

		GUITools.centreFrame(frame);

		frame.setVisible(true);
	}

}
