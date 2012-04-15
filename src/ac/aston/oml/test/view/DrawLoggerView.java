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
import ac.aston.oml.view.gui.OMLLoggerView;

import jcummins.gui.GUITools;

/**
 * Testing class for .
 * 
 * @author Chris Cummins
 * @see ac.aston.oml.view.gui.OMLLogSettingsView
 */
public class DrawLoggerView
{

	/**
	 * Renders a OMLLoggerView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main (String[] args)
	{
		final OMLSettings c = OMLTestSettings.getOMLSettings ();

		GUITools.setNativeLookAndFeel ();

		final OMLLoggerView frame = new OMLLoggerView ();
		frame.init (c.fontset, OMLTestSettings.getTimeSeriesCollection (),
				"[Graph title]", 0.0, 1023.0, 30000.0, OMLTestSettings.signals);

		String[] tmp = new String[OMLTestSettings.activeSignalCount];
		for (int i = 0; i < tmp.length; i++)
			tmp[i] = "[?]";

		frame.setValLabels (tmp);
		frame.setMinLabels (tmp);
		frame.setMaxLabels (tmp);
		frame.setAvgLabels (tmp);
		frame.setReadingsLabel ("<?>");

		frame.setVisible (true);
		frame.repaint ();
		GUITools.centreFrame (frame);

	}

}
