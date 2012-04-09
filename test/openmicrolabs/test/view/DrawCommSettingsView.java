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

package openmicrolabs.test.view;

import openmicrolabs.view.CommSettingsView;
import cummins.gui.GUITools;

/**
 * Testing class for CommSettingsView.
 * 
 * @author Chris Cummins
 * @see openmicrolabs.view.CommSettingsView
 * 
 */
public class DrawCommSettingsView
{

	/**
	 * Renders a CommSettingsView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main (String[] args)
	{
		GUITools.setNativeLookAndFeel ();

		CommSettingsView frame = new CommSettingsView ();
		frame.setVisible (true);

	}

}
