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

import ac.aston.oml.controller.OMLController;
import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.view.ViewGateway;


/**
 * This implementation of the ActionListener interface is responsible for
 * receiving logging cancel requests form the user and so updating the view and
 * model accordingly.
 * 
 * @author Chris Cummins
 * 
 */
public class LoggerDoneListener extends OMLController implements
		ActionListener
{
	private final ModelGateway m;
	private final ViewGateway v;
	
	public LoggerDoneListener (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{	
		if (m.logger ().isLogging ())
			if (v.showYesNoPrompt ("Readings in progress!\nAre you sure you "
					+ "would like to exit?"))
				returnToLogSettings ();
			else
				return;
		else
			returnToLogSettings ();
	}

	private void returnToLogSettings ()
	{
		v.lv ().teardown ();
		v.ls ().fetchFrame ().setVisible (true);
	}

}
