/* Chris Cummins - 21 Apr 2012
 *
 * This file is part of openmicrolabs.
 *
 * openmicrolabs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * openmicrolabs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with openmicrolabs.  If not, see <http://www.gnu.org/licenses/>.
 */

package ac.openmicrolabs.controller;

import java.util.List;

import javax.swing.SwingWorker;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import ac.openmicrolabs.model.com.signals.OMLSignal;
import ac.openmicrolabs.view.ViewGateway;

/**
 * 
 * 
 * @author Chris Cummins
 * 
 */
public class AverageCalculatorWorker extends SwingWorker<String[], Void> {

	private final TimeSeriesCollection data;
	private final OMLSignal[] signals;
	private final ViewGateway v;

	/**
	 * Creates a new AverageCalculatorWorker SwingWorker.
	 * @param t TimeSeriesCollection.
	 * @param o Active OMLSignals as an array.
	 * @param view ViewGateway for update Avg labels.
	 */
	public AverageCalculatorWorker(final TimeSeriesCollection t,
			final OMLSignal[] o, final ViewGateway view) {
		this.data = t;
		this.signals = o;
		this.v = view;
	}

	@Override
	protected final String[] doInBackground() throws Exception {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			final TimeSeries series = data.getSeries(i);
			@SuppressWarnings("unchecked")
			final List<TimeSeriesDataItem> vals = series.getItems();
			double total = 0;

			for (int j = 0; j < vals.size(); j++) {
				if (vals.get(j).getValue() != null) {
					total += vals.get(j).getValue().doubleValue();
				}
			}

			s[i] = signals[i].toString(total / vals.size());
		}

		return s;
	}

	@Override
	protected final void done() {
		try {
			v.lv().setAvgLabels(get());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
