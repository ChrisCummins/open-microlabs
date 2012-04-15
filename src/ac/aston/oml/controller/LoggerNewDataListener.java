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

package ac.aston.oml.controller;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.com.signals.OMLSignal;
import ac.aston.oml.view.ViewGateway;

/**
 * This implementation of the SeriesChangeListener interface is responsible for
 * handling new data notifications from the model and updating the view
 * accordingly.
 * 
 * @author Chris Cummins
 * 
 */
public class LoggerNewDataListener extends OMLController implements
		SeriesChangeListener
{

	private final ModelGateway m;
	private final ViewGateway v;
	private final OMLSignal[] o;

	public LoggerNewDataListener (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;
		this.o = m.l ().getLogSettings ().datamask ().activeSignals ();
	}

	@Override
	public void seriesChanged (SeriesChangeEvent event)
	{
		final TimeSeriesCollection data = m.l ().getData ();

		updateValues (data);
		updateMin (data);
		updateMax (data);
	}

	private void updateValues (TimeSeriesCollection data)
	{
		final String[] s = new String[data.getSeriesCount ()];

		for (int i = 0; i < s.length; i++)
		{
			final Number value = data.getSeries (i).getValue (
					data.getSeries (i).getItemCount () - 1);
			if (value != null)
				s[i] = o[i].toString (value.doubleValue ());
			else
				s[i] = null;
		}
		v.lv ().setValLabels (s);
	}

	private void updateMin (TimeSeriesCollection data)
	{
		final String[] s = new String[data.getSeriesCount ()];

		for (int i = 0; i < s.length; i++)
		{
			s[i] = o[i].toString (data.getSeries (i).getMinY ());
		}
		v.lv ().setMinLabels (s);
	}

	private void updateMax (TimeSeriesCollection data)
	{
		final String[] s = new String[data.getSeriesCount ()];

		for (int i = 0; i < s.length; i++)
		{
			s[i] = o[i].toString (data.getSeries (i).getMaxY ());
		}
		v.lv ().setMinLabels (s);
	}

	private void updateAvg (TimeSeriesCollection data)
	{
		// TODO:
	}

}
