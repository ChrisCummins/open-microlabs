/* Chris Cummins - 15 Apr 2012
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

import java.util.List;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ac.aston.oml.model.logger.LogSettings;
import ac.aston.oml.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class DynamicNewData {

	private final TimeSeriesCollection data;
	private final LogSettings l;
	private final ViewGateway v;

	public DynamicNewData(TimeSeriesCollection data, LogSettings l,
			ViewGateway v) {
		this.data = data;
		this.l = l;
		this.v = v;

		updateReadingsLabel();
		updateSignalStrengthLabel();
		updateProgressBar();
		updateValues();
		updateMin();
		updateMax();
		updateAvg();
	}

	private void updateReadingsLabel() {
		String s = data.getSeries(0).getItemCount() + " of ";
		s += l.readCount() + " (";
		s += (l.readDelay() * (l.readCount() - data.getSeries(0).getItemCount()))
				/ 1000 + "s remaining)";
		v.lv().setReadingsLabel(s);
	}

	private void updateSignalStrengthLabel() {
		String s = "100%" + " signal strength";
		// TODO: Calculation
		v.lv().setSignalStrenghLabel(s);
	}

	private void updateProgressBar() {
		v.lv().setProgressBar(data.getItemCount(0), l.readCount());
	}

	private void updateValues() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			final Number value = data.getSeries(i).getValue(
					data.getSeries(i).getItemCount() - 1);
			if (value != null)
				s[i] = l.datamask().activeSignals()[i].toString(value
						.doubleValue());
			else
				s[i] = null;
		}
		v.lv().setValLabels(s);
	}

	private void updateMin() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++)
			s[i] = l.datamask().activeSignals()[i].toString(data.getSeries(i)
					.getMinY());
		v.lv().setMinLabels(s);
	}

	private void updateMax() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++)
			s[i] = l.datamask().activeSignals()[i].toString(data.getSeries(i)
					.getMaxY());
		v.lv().setMaxLabels(s);
	}

	private void updateAvg() {
		String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			final TimeSeries series = data.getSeries(i);
			@SuppressWarnings("unchecked")
			final List<TimeSeriesDataItem> vals = series.getItems();
			double total = 0;

			for (int j = 0; j < vals.size(); j++)
				if (vals.get(j).getValue() != null)
					total += vals.get(j).getValue().doubleValue();

			s[i] = l.datamask().activeSignals()[i]
					.toString(total / vals.size());
		}
		v.lv().setAvgLabels(s);
	}

}
