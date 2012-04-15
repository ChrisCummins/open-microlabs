/* Chris Cummins - 9 Apr 2012
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

import ac.aston.oml.controller.CommExceptionResponse;
import ac.aston.oml.controller.OMLController;
import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.com.CommSettings;
import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class CommSettingsTestListener extends OMLController implements
		ActionListener
{

	private final ModelGateway m;
	private final ViewGateway v;

	public CommSettingsTestListener (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		final OMLSettings c = m.getOMLSettings ();

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
			if (m.com ().commTest ())
				v.showMessage ("Test succeeded!");
			else
				v.showError ("Test failed on " + portName
						+ "! No response from microcontroller.");
		} catch (Throwable t)
		{
			CommExceptionResponse.catchException (v, portName, t);
		}

	}
}