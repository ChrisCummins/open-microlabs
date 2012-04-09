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

import cummins.gui.GUITools;

import openmicrolabs.data.Datamask;
import openmicrolabs.data.GraphSettings;
import openmicrolabs.data.LogSettings;
import openmicrolabs.signals.OMLSignal;
import openmicrolabs.signals.OMLVoltage;
import openmicrolabs.view.LoggerView;

import org.jfree.data.time.TimeSeriesCollection;

/**
 * Testing class for CommSettingsView.
 * 
 * @author Chris Cummins
 * @see openmicrolabs.view.OMLSettingsView
 */
public class DrawLoggerView
{

	private static OMLSignal d1 = new OMLSignal ();
	private static OMLVoltage d2 = new OMLVoltage ();
	private static Datamask d = new Datamask ((new OMLSignal[] { d1, null, d1,
			d2, null, d2, d2, null }));
	
	private static LogSettings l = new LogSettings (d, 1000, 35, null);
	private static GraphSettings g = new GraphSettings (null, 0, 1023);
	private static TimeSeriesCollection dataset;

	/**
	 * Renders a LoggerView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main (String[] args)
	{
		GUITools.setNativeLookAndFeel ();
		LoggerView frame = new LoggerView (l, g, dataset);

		frame.setVisible (true);
	}

}
