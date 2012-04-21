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

package ac.openmicrolabs.controller;

import javax.swing.SwingUtilities;

import com.jcummins.gui.GUITools;

import ac.openmicrolabs.controller.listeners.AdvancedSettingsDoneListenner;
import ac.openmicrolabs.controller.listeners.AdvancedSettingsShowListener;
import ac.openmicrolabs.controller.listeners.CommSettingsDoneListener;
import ac.openmicrolabs.controller.listeners.CommSettingsRefreshPortsListener;
import ac.openmicrolabs.controller.listeners.CommSettingsTestListener;
import ac.openmicrolabs.controller.listeners.LogSettingsDoneListener;
import ac.openmicrolabs.controller.listeners.LogSettingsFileListener;
import ac.openmicrolabs.controller.listeners.LogSettingsSlaveOptionsListener;
import ac.openmicrolabs.controller.listeners.LoggerDoneListener;
import ac.openmicrolabs.controller.listeners.LoggerSaveReportListener;
import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.view.ViewGateway;

/**
 * This implementation of the Controller interface performs the duties of
 * instantiating a model and view, then attaching the required listeners and
 * rendering the first view.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLController implements Controller {

	private ModelGateway m;
	private ViewGateway v;

	@Override
	public final void init(final ModelGateway model, final ViewGateway view) {
		this.m = model;
		this.v = view;

		attachListeners();
		renderCommSettingsView();
	}

	/*
	 * Add listeners to frame components.
	 */
	private void attachListeners() {
		// Comm Settings view listeners.
		v.cs().addRefreshButtonListener(
				new CommSettingsRefreshPortsListener(m, v));
		v.cs().addTestButtonListener(new CommSettingsTestListener(m, v));
		v.cs().addDoneButtonListener(new CommSettingsDoneListener(m, v));

		// Log Settings view listeners.
		v.ls().addFileButtonListener(new LogSettingsFileListener(v));
		v.ls()
				.addAdvancedButtonListener(
						new AdvancedSettingsShowListener(m, v));
		v.ls().addDoneButtonListener(new LogSettingsDoneListener(m, v));
		v.ls().addSlaveOptionsListener(new LogSettingsSlaveOptionsListener(v));

		// Advanced Settings view listeners.
		v.as().addDoneButtonListener(new AdvancedSettingsDoneListenner(m, v));

		// Logger view listeners.
		v.lv().addReportButtonListener(new LoggerSaveReportListener(m, v));
		v.lv().addDoneButtonListener(new LoggerDoneListener(m, v));
	}

	/*
	 * Renders the first frame and makes it visible.
	 */
	private void renderCommSettingsView() {
		final OMLSettings c = m.getOMLSettings();
		m.com().refreshCommPorts();

		v.cs().init(m.getOMLSettings().getFontset());
		v.cs().setBaudOptions(c.getBaudOptions(), c.getBaudOptionsSelected());
		v.cs().setComOptions(m.com().getCommPorts()[0]);
		v.cs().setDataOptions(c.getDatabitOptions()[0],
				c.getDatabitOptionsSelected());
		v.cs().setStopOptions(c.getStopbitOptions()[0],
				c.getStopbitOptionsSelected());
		v.cs().setParityOptions(c.getParityOptions()[0],
				c.getParityOptionsSelected());
		v.cs()
				.setFlowOptions(c.getFlowOptions()[0],
						c.getFlowOptionsSelected());

		GUITools.centreFrame(v.cs().fetchFrame());
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public final void run() {
				v.cs().fetchFrame().setVisible(true);
			}
		});
	}

}
