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

import ac.openmicrolabs.controller.CommExceptionResponse;
import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.com.CommSettings;
import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import com.jcummins.gui.GUITools;


/**
 * This implementation of the ActionListener interface is responsible for
 * getting comm settings from the view and setting those to the model.
 * Additionally, it will interpret any exceptions thrown by the model and feed
 * those to a CommExceptionResponse object.
 * 
 * @author Chris Cummins
 * @see CommExceptionResponse#catchException(ViewGateway, String, Throwable)
 */
public class CommSettingsDoneListener implements ActionListener {

	private static final String[] SLAVE_OPTIONS = { "1 Microcontroller",
			"2 Microcontrollers", "3 Microcontrollers", "4 Microcontrollers",
			"5 Microcontrollers", "6 Microcontrollers", "7 Microcontrollers",
			"8 Microcontrollers", "9 Microcontrollers", "10 Microcontrollers",
			"11 Microcontrollers", "12 Microcontrollers",
			"13 Microcontrollers", "14 Microcontrollers" };
	private static final int DEFAULT_PIN_COUNT = 7;

	private final ModelGateway m;
	private final ViewGateway v;
	private final OMLSettings c;

	/**
	 * Creates a new Action Listener.
	 * 
	 * @param model
	 *            Model Gateway for setting comm settings.
	 * @param view
	 *            View Gateway for controlling view.
	 */
	public CommSettingsDoneListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
		this.c = m.getOMLSettings();
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {

		// Get data from UI.
		final String portName = (String) m.com().getCommPorts()[1][v.cs()
				.getSelectedComOption()];
		final int baudrate = (int) c.getBaudOptions()[v.cs()
				.getSelectedBaudOption()];
		final int databits = (int) c.getDatabitOptions()[1][v.cs()
				.getSelectedDataOption()];
		final int stopbits = (int) c.getStopbitOptions()[1][v.cs()
				.getSelectedStopOption()];
		final int paritybits = (int) c.getParityOptions()[1][v.cs()
				.getSelectedParityOption()];
		final int flowbits = (int) c.getFlowOptions()[1][v.cs()
				.getSelectedFlowOption()];

		// Construct model data from values.
		final CommSettings com = new CommSettings(portName, baudrate, databits,
				stopbits, paritybits, flowbits);

		// Try and assign to model, exceptions are handled by
		// CommExceptionResponse.
		try {
			m.com().setCommSettings(com);
			m.com().commConnect();
			v.cs().teardown();
			renderLogSettings();
		} catch (Throwable t) {
			CommExceptionResponse.catchException(v, portName, t);
		}
	}

	/*
	 * Render the Log Settings view.
	 */
	private void renderLogSettings() {
		// Setup screen state data.
		v.ls()
				.init(c.getFontset(), DEFAULT_PIN_COUNT,
						c.getSignalTypeOptions());
		v.ls().setFilepathLabel(
				System.getProperty("user.dir") + File.separator + "log.dat");
		v.ls().setSlaveBoxOptions(SLAVE_OPTIONS);

		// Make frame visible.
		GUITools.centreFrame(v.ls().fetchFrame());
		v.ls().fetchFrame().setVisible(true);
	}

}
