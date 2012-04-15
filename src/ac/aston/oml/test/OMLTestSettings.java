/* Chris Cummins - 12 Apr 2012
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

package ac.aston.oml.test;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.settings.OMLDefaultSettings;
import ac.aston.oml.model.settings.OMLSettings;
/**
 * @author Chris Cummins
 * 
 */
public class OMLTestSettings
{

	public static final String[] signals = { "Voltage", null, "Raw", "Voltage",
			"Voltage", null, null, null, null, "Voltage", "Raw", null, null,
			"Raw" };
	public static final String[] signalTypes = { "Raw", "Voltage" };
	public static final int activeSignalCount = 7;

	public static OMLSettings getOMLSettings ()
	{
		return OMLDefaultSettings.get ();
	}

	public static TimeSeriesCollection getTimeSeriesCollection ()
	{
		final TimeSeriesCollection t = new TimeSeriesCollection ();

		for (int i = 0; i < activeSignalCount; i++)
			t.addSeries (new TimeSeries ("<?>"));

		return t;
	}

}
