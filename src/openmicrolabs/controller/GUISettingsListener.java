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

/**
 * This implementation of the ActionListener interface is responsible for
 * getting GUI settings from the GUISettingsView class and setting those to the
 * view. Additionally, it will interpret any exceptions thrown and feed those
 * back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class GUISettingsListener extends OMLController implements
		ActionListener
{

	@Override
	public void actionPerformed (ActionEvent e)
	{
		// TODO Auto-generated method stub

	}

}
