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

package ac.openmicrolabs.controller.listeners;

import ac.openmicrolabs.controller.LoggerScreenStateUpdater;
import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.logger.LogSettings;
import ac.openmicrolabs.view.ViewGateway;

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the SeriesChangeListener interface is responsible for
 * updating screen state data after a change to the record state data.
 * 
 * @author Chris Cummins
 * 
 */
public class LoggerNewDataListener implements SeriesChangeListener {

	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * Creates a new SeriesChangeListener.
	 * 
	 * @param model
	 *            Model Gateway for getting record state data.
	 * @param view
	 *            View Gateway for update screen state data.
	 */
	public LoggerNewDataListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
	}

	@Override
	public final void seriesChanged(final SeriesChangeEvent event) {
		// Get record state data.
		final TimeSeriesCollection data = m.logger().getData();
		final LogSettings l = m.logger().getLogSettings();

		// Update filelogger if necessary.
		if (m.logger().getLogSettings().logPath() != null) {
			m.logger().addNewDataToLog(data);
		}

		// Update screen state.
		new LoggerScreenStateUpdater(data, l, m, v);

		// If last reading, set screen state to readings complete.
		if (data.getItemCount(0) == l.readCount()) {
			v.lv().setViewLoggingCompleted(l.readCount() * l.readDelay());

			String s = "<b>" + m.logger().getReadingCount()
					+ " readings complete";
			if (m.logger().getNullReadingCount() > 0) {
				s += " (" + m.logger().getNullReadingCount()
						+ " reading";
				if (m.logger().getNullReadingCount() > 1) {
					s += "s";
				}
				s += " lost)";
			}
			s += ".</b>";
			v.lv().setSignalStrenghLabel(s);
		}
	}
}
