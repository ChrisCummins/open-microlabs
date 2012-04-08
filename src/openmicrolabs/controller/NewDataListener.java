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

import org.jfree.data.general.SeriesChangeEvent;
import org.jfree.data.general.SeriesChangeListener;

/**
 * This implementation of the SeriesChangeListener interface is responsible for
 * handling new data notifications from the model and updating the view
 * accordingly.
 * 
 * @author Chris Cummins
 * 
 */
public class NewDataListener extends OMLController implements
		SeriesChangeListener
{

	@Override
	public void seriesChanged (SeriesChangeEvent event)
	{
		// TODO: New data listener

	}

}
