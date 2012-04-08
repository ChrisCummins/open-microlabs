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

package openmicrolabs.view;

import java.awt.event.ActionListener;

import openmicrolabs.settings.CommSettings;
import openmicrolabs.settings.LogSettings;

/**
 * This implementation of the View interface is responsible for rendering and
 * updating the graphical user interface for user interactions/data feedback.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLView implements View
{

	@Override
	public void showMessage (String msg)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void showError (String ms)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public CommSettings getCommSettings ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LogSettings getLogSettings ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void loggingStarted ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateViews ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCommSettingsListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTestConnectionListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addShowGUISettingsListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGUISettingsListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addStartLoggingListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCancelLoggingListener (ActionListener l)
	{
		// TODO Auto-generated method stub
		
	}

}
