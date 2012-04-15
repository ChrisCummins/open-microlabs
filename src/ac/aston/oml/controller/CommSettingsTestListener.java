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

package ac.aston.oml.controller;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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

		final String portName = (String) m.c ().getCommPorts ()[1][v.cs ()
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
			m.c ().setCommSettings (com);
			if (m.c ().commTest ())
				v.showMessage ("Test succeeded!");
			else
				v.showError ("Test failed on " + portName
						+ "! No response from microcontroller.");
		} catch (NoSuchPortException e)
		{
			v.showError ("Unable to connect to com port, " + portName
					+ " does not exist!");
		} catch (PortInUseException e)
		{
			v.showError ("Unable to connect to com port, " + portName
					+ " already in use!");
		} catch (UnsupportedCommOperationException e)
		{
			v.showError ("Unable to connect to com port, " + portName
					+ " does not support this type of operation!");
		} catch (IOException e)
		{
			v.showError ("Unable to connect to com port, " + portName
					+ " access denied!");
		}

	}
}
