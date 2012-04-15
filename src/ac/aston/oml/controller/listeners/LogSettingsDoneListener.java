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

package ac.aston.oml.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jcummins.gui.GUITools;

import ac.aston.oml.include.AppDetails;
import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.com.Datamask;
import ac.aston.oml.model.com.signals.OMLSignal;
import ac.aston.oml.model.logger.AdvancedSettings;
import ac.aston.oml.model.logger.LogSettings;
import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.view.ViewGateway;

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

	private final ModelGateway m;
	private final ViewGateway v;

	private OMLSettings c;

	public LogSettingsDoneListener(ModelGateway m, ViewGateway v) {
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		c = m.getOMLSettings();

		long readDelay;
		int readCount;

		try {
			readDelay = Long.parseLong(v.ls().getReadDelayText());
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

		final String filepath = v.ls().getFilepathText();
		Datamask d = createDatamask();

		LogSettings l = new LogSettings(d, readDelay, readCount, filepath);

		try {
			m.logger().setLogSettings(l, m.com());
		} catch (IllegalArgumentException e) {
			v.showError(e.getMessage());
			return;
		}

		m.logger().startLogging();
		renderLoggerView();
		m.logger().addNewDataListener(new LoggerNewDataListener(m, v));
	}

	private Datamask createDatamask() {
		Integer[] indexes = v.ls().getSignalTypeOptions();
		OMLSignal[] o = new OMLSignal[indexes.length];

		for (int i = 0; i < indexes.length; i++)
			if (indexes[i] != null)
				o[i] = c.signalTypes[indexes[i]];
			else
				o[i] = null;

		return new Datamask(o);
	}

	private void renderLoggerView() {
		AdvancedSettings a;
		if (m.logger().getAdvancedSettings() != null)
			a = m.logger().getAdvancedSettings();
		else
			a = new AdvancedSettings(
					(Double) c.graphTimeRangeOptions[1][c.graphTimeRangeOptionsSelected],
					0.0, 1023.0);

		v.lv().init(c.fontset, m.logger().getData(), AppDetails.name(),
				a.minY(), a.maxY(), a.timeRange(),
				m.logger().getLogSettings().datamask().signalsToString());
		GUITools.centreFrame(v.lv().fetchFrame());

		v.as().teardown();
		v.ls().fetchFrame().setVisible(false);
		v.lv().fetchFrame().setVisible(true);
	}
}
