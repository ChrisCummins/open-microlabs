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

import openmicrolabs.model.Model;
import openmicrolabs.settings.LogSettings;
import openmicrolabs.view.View;

/**
 * This implementation of the ActionListener interface is responsible for
 * getting log settings from the view and setting those to the model.
 * Additionally, it will interpret any exceptions thrown by the model and feed
 * those back to the view for the user.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettingsListener extends OMLController implements
		ActionListener
{
	
	private final Model m;
	private final View v;
	
	public LogSettingsListener (Model m, View v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent arg0)
	{
		try
		{
			LogSettings l = v.getLogSettings ();
			m.setLogSettings (l);
			m.startLogging ();
			v.loggingStarted (l, m.getData ());
			m.addNewDataListener (new NewDataListener (m, v));
			v.addCancelLoggingListener (new CancelLoggingListener (m, v));
		} catch (NumberFormatException e)
		{
			v.showError ("Text areas must contain positive integers only!");
		} catch (IllegalArgumentException e1)
		{
			v.showError (e1.getMessage ());
		}
	}

}
