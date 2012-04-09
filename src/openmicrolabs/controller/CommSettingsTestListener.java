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

package openmicrolabs.controller;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import openmicrolabs.data.CommSettings;
import openmicrolabs.model.Model;
import openmicrolabs.view.View;

/**
 * @author Chris Cummins
 * 
 */
public class CommSettingsTestListener extends OMLController implements
		ActionListener
{

	private final Model m;
	private final View v;

	public CommSettingsTestListener (Model m, View v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		CommSettings c = v.getCommSettings ();
		try
		{
			m.setCommSettings (c);
			if (m.testConnection ())
				v.showMessage ("Test succeeded!");
			else
				v.showError ("Test failed! No response from microcontroller.");
		} catch (NoSuchPortException e)
		{
			v.showError ("Unable to connect to com port, " + c.portName ()
					+ " does not exist!");
		} catch (PortInUseException e)
		{
			v.showError ("Unable to connect to com port, " + c.portName ()
					+ " already in use!");
		} catch (UnsupportedCommOperationException e)
		{
			v.showError ("Unable to connect to com port, " + c.portName ()
					+ " does not support this type of operation!");
		} catch (IOException e)
		{
			v.showError ("Unable to connect to com port, " + c.portName ()
					+ " access denied!");
		}

	}
}
