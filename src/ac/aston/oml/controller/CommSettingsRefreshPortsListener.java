/* Chris Cummins - 14 Apr 2012
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class CommSettingsRefreshPortsListener extends OMLController implements
		ActionListener
{

	private final ModelGateway m;
	private final ViewGateway v;

	public CommSettingsRefreshPortsListener (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		m.c ().refreshCommPorts ();
		v.cs ().setComOptions (m.c ().getCommPorts ()[0]);
	}

}
