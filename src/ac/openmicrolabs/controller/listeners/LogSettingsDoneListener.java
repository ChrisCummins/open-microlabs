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

import ac.openmicrolabs.include.AppDetails;
import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.com.Datamask;
import ac.openmicrolabs.model.com.signals.OMLSignal;
import ac.openmicrolabs.model.logger.AdvancedSettings;
import ac.openmicrolabs.model.logger.LogSettings;
import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import com.jcummins.gui.GUITools;


/**
 * This implementation of the ActionListener interface is responsible for
 * getting log settings from the view and setting those to the model.
 * Additionally, it will interpret any exceptions thrown by the model and feed
 * those back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettingsDoneListener implements ActionListener {

	private static final int READ_DELAY_MULTIPLIER = 1000;
	private static final double DEFAULT_MINY = 0.0;
	private static final double DEFAULT_MAXY = 1023.0;

	private final ModelGateway m;
	private final ViewGateway v;
	private final OMLSettings c;

	/**
	 * Constructs new ActionListener.
	 * 
	 * @param model
	 *            Model Gateway for updating record state.
	 * @param view
	 *            View Gateway for getting screen state data.
	 */
	public LogSettingsDoneListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
		this.c = model.getOMLSettings();
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {
		// Get screen state data.
		long readDelay;
		int readCount;
		try {
			readDelay = (long) (Double.parseDouble(v.ls().getReadDelayText()) * READ_DELAY_MULTIPLIER);
		} catch (NumberFormatException e) {
			v.showError("Read delay must be a positive integer!");
			return;
		}
		try {
			readCount = Integer.parseInt(v.ls().getReadCountText());
		} catch (NumberFormatException e) {
			v.showError("Read count must be a positive integer!");
			return;
		}

		String filepath;
		if (v.ls().getLogToFile()) {
			filepath = v.ls().getFilepathText();
		} else {
			filepath = null;
		}

		// Create session state data.
		Datamask d = createDatamask();
		LogSettings l = new LogSettings(d, readDelay, readCount, filepath);

		// Update record state data.
		try {
			m.logger().setLogSettings(l, m.com());
		} catch (IllegalArgumentException e) {
			// TODO:
			// Thrown by ??? because read delay was too small.
			v.showError(e.getMessage());
			return;
		} catch (IOException e) {
			// Thrown by SerialLogger being unable to instantiate FileLogger.
			v.showError("Unable to log to file due to IO error!");
			return;
		}

		try {
			m.logger().startLogging();
		} catch (IOException e) {
			v.showError("Unable to log to file, IO error!");
			return;
		}

		// Update screen state.
		renderLoggerView();

		// Add listener to record data.
		m.logger().addNewDataListener(new LoggerNewDataListener(m, v));

		// Hide settings windows frames.
		v.as().fetchFrame().setVisible(false);
		v.ls().fetchFrame().setVisible(false);

		// Display logger view frame.
		v.lv().fetchFrame().setVisible(true);
	}

	/*
	 * Creates a session state datamask.
	 */
	private Datamask createDatamask() {
		Integer[] indexes = v.ls().getSignalTypeOptions();
		OMLSignal[] o = new OMLSignal[indexes.length];

		for (int i = 0; i < indexes.length; i++) {
			if (indexes[i] != null) {
				o[i] = c.getSignalTypes()[indexes[i]];
			} else {
				o[i] = null;
			}
		}

		return new Datamask(o);
	}

	/*
	 * Creates a session state advanced settings.
	 */
	private AdvancedSettings createAdvancedSettings() {
		final Double timeRange = (Double) c.getGraphTimeRangeOptions()[1][c
				.getGraphTimeRangeOptionsSelected()];

		return new AdvancedSettings(timeRange, DEFAULT_MINY, DEFAULT_MAXY);
	}

	/*
	 * Display log view.
	 */
	private void renderLoggerView() {
		AdvancedSettings a;
		if (m.logger().getAdvancedSettings() != null) {
			a = m.logger().getAdvancedSettings();
		} else {
			a = createAdvancedSettings();
		}
		v.lv().init(c.getFontset(), m.logger().getData(),
				AppDetails.name() + " " + AppDetails.version(), a.minY(),
				a.maxY(), a.timeRange(),
				m.logger().getLogSettings().datamask().signalsToString());
		GUITools.centreFrame(v.lv().fetchFrame());
	}

}
