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
import ac.aston.oml.view.gui.OMLCommSettingsView;
import jcummins.gui.GUITools;
import jcummins.serial.DiscoverPorts;

/**
 * Testing class for OMLCommSettingsView.
 * 
 * @author Chris Cummins
 * @see ac.aston.oml.view.gui.OMLCommSettingsView
 * 
 */
public class DrawCommSettingsView
{

	/**
	 * Renders a OMLCommSettingsView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main (String[] args)
	{
		GUITools.setNativeLookAndFeel ();

		OMLSettings c = OMLTestSettings.getOMLSettings ();

		final OMLCommSettingsView frame = new OMLCommSettingsView ();
		frame.init (c.fontset ());

		final String[] ports = DiscoverPorts.listToArray ();
		if (ports.length < 1)
		{
			String[] s = { "None" };
			frame.setComOptions (s);
		} else
			frame.setComOptions (ports);
		frame.setBaudOptions (c.baudOptions (), 5);
		frame.setDataOptions (c.databitOptions ()[0], 3);
		frame.setStopOptions (c.stopbitOptions ()[0], 0);
		frame.setParityOptions (c.parityOptions ()[0], 0);
		frame.setFlowOptions (c.flowOptions ()[0], 0);

		frame.setVisible (true);

	}

}
