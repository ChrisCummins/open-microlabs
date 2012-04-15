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
import java.io.File;

import jcummins.gui.GUITools;

import ac.aston.oml.controller.CommExceptionResponse;
import ac.aston.oml.controller.OMLController;
import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.com.CommSettings;
import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.view.ViewGateway;

/**
 * This implementation of the ActionListener interface is responsible for
 * getting comm settings from the view and setting those to the model.
 * Additionally, it will interpret any exceptions thrown by the model and feed
 * those back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class CommSettingsDoneListener extends OMLController implements
		ActionListener
{

	private final ModelGateway m;
	private final ViewGateway v;

	private OMLSettings c;

	public CommSettingsDoneListener (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		c = m.getOMLSettings ();

		final String portName = (String) m.com ().getCommPorts ()[1][v.cs ()
				.getSelectedComOption ()];
		final int baudrate = (int) c.baudOptions[v.cs ()
				.getSelectedBaudOption ()];
		final int databits = (int) c.databitOptions[1][v.cs ()
				.getSelectedDataOption ()];
		final int stopbits = (int) c.stopbitOptions[1][v.cs ()
				.getSelectedStopOption ()];
		final int paritybits = (int) c.parityOptions[1][v.cs ()
				.getSelectedParityOption ()];
		final int flowbits = (int) c.flowOptions[1][v.cs ()
				.getSelectedFlowOption ()];

		final CommSettings com = new CommSettings (portName, baudrate,
				databits, stopbits, paritybits, flowbits);

		try
		{
			m.com ().setCommSettings (com);
			m.com ().commConnect ();
			v.cs ().teardown ();
			renderLogSettings ();
		} catch (Throwable t)
		{
			CommExceptionResponse.catchException (v, portName, t);
		}
	}

	private void renderLogSettings ()
	{
		final String[] s = { "1 Microcontroller", "2 Microcontrollers",
				"3 Microcontrollers", "4 Microcontrollers",
				"5 Microcontrollers" };

		v.ls ().init (c.fontset, 7, c.signalTypeOptions);

		v.ls ().setFilepathLabel (
				System.getProperty ("user.dir") + File.separator + "log.dat");
		v.ls ().setSlaveBoxOptions (s);

		GUITools.centreFrame (v.ls ().fetchFrame ());
		v.ls ().fetchFrame ().setVisible (true);
	}

}
