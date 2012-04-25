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

package ac.openmicrolabs.controller;

import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.logger.LogSettings;
import ac.openmicrolabs.view.ViewGateway;

import org.jfree.data.time.TimeSeriesCollection;

/**
 * This class updates the LoggerView with the latest data from the model.
 * 
 * @author Chris Cummins
 */
public class NewDataRunnable implements Runnable {

	private static final int READ_DELAY_TO_SECONDS_DIVISOR = 1000;
	private static final int PERCENTAGE_MULTIPLIER = 100;

	private final TimeSeriesCollection data;
	private final LogSettings l;
	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * Create a new LoggerUpdateViewer.
	 * 
	 * @param dataCollection
	 *            Data model.
	 * @param logSettings
	 *            Log Settings in use.
	 * @param model
	 *            ModelGateway for getting reading counts from Logger.
	 * @param view
	 *            ViewGateway for updating view.
	 */
	public NewDataRunnable(final TimeSeriesCollection dataCollection,
			final LogSettings logSettings, final ModelGateway model,
			final ViewGateway view) {
		this.data = dataCollection;
		this.l = logSettings;
		this.m = model;
		this.v = view;

	}

	@Override
	public final void run() {
		updateReadings();
		updateSignalStrength();
		updateProgressBar();
		updateValues();
		updateMin();
		updateMax();
		updateAvg();
	}

	/*
	 * Update the readings label.
	 */
	private void updateReadings() {
		final int r = l.readCount();

		String s = data.getSeries(0).getItemCount() + " of ";
		s += r + " (";
		s += (l.readDelay() * r - data.getSeries(0).getItemCount())
				/ READ_DELAY_TO_SECONDS_DIVISOR + "s remaining)";
		v.lv().setReadingsLabel(s);
	}

	/*
	 * Update the signal strength label.
	 */
	private void updateSignalStrength() {
		float lostPercentage = (float) 0.0;
		if (m.logger().getReadingCount() > 0) {
			lostPercentage = (((float) m.logger().getNullReadingCount() / (float) m
					.logger().getReadingCount()) * PERCENTAGE_MULTIPLIER);
		}
		String s = (int) (PERCENTAGE_MULTIPLIER - lostPercentage)
				+ "% signal strength";
		v.lv().setSignalStrenghLabel(s);
	}

	/*
	 * Update the progress bar.
	 */
	private void updateProgressBar() {
		v.lv().setProgressBar(data.getItemCount(0), l.readCount());
	}

	/*
	 * Update the latest reading values.
	 */
	private void updateValues() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			final Number value = data.getSeries(i).getValue(
					data.getSeries(i).getItemCount() - 1);
			if (value != null) {
				s[i] = l.datamask().activeSignals()[i].toString(value
						.doubleValue());
			} else {
				s[i] = null;
			}
		}
		v.lv().setValLabels(s);
	}

	/*
	 * Update the minimum reading values.
	 */
	private void updateMin() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			s[i] = l.datamask().activeSignals()[i].toString(data.getSeries(i)
					.getMinY());
		}
		v.lv().setMinLabels(s);
	}

	/*
	 * Update the maximum reading values.
	 */
	private void updateMax() {
		final String[] s = new String[data.getSeriesCount()];
		for (int i = 0; i < s.length; i++) {
			s[i] = l.datamask().activeSignals()[i].toString(data.getSeries(i)
					.getMaxY());
		}
		v.lv().setMaxLabels(s);
	}

	/*
	 * Update the average reading values.
	 */
	private void updateAvg() {
		(new AverageCalculatorWorker(data, m.logger().getLogSettings()
				.datamask().activeSignals(), v)).execute();
	}

}
