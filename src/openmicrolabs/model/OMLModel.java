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

package openmicrolabs.model;

import openmicrolabs.settings.CommSettings;
import openmicrolabs.settings.LogSettings;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the Model interface performs the necessary roles of an
 * Open MicroLabs model.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLModel implements Model
{

	@Override
	public boolean testConnection ()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startLogging ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLogging ()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCommSettings (CommSettings c)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLogSettings (LogSettings l)
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
	public TimeSeriesCollection getData ()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addNewDataListener (SeriesChangeListener l)
	{
		// TODO Auto-generated method stub
		
	}

}
