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

package openmicrolabs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import openmicrolabs.view.View;

/**
 * This implementation of the ActionListener interface is responsible for
 * getting GUI settings from the GUISettingsView class and setting those to the
 * view. Additionally, it will interpret any exceptions thrown and feed those
 * back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class GraphSettingsDoneListenner extends OMLController implements
		ActionListener
{
	private final View v;
	
	public GraphSettingsDoneListenner (View v)
	{
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{
		v.graphSettingsComplete ();
	}

}
