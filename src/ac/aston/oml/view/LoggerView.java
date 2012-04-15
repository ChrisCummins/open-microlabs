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

package ac.aston.oml.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import jcummins.gui.HTMLFontset;

import org.jfree.data.time.TimeSeriesCollection;

/**
 * @author Chris Cummins
 * 
 */
public interface LoggerView
{

	public void init (HTMLFontset h, TimeSeriesCollection t, String graphTitle,
			double graphMinY, double graphMaxY, double graphTimeRange,
			String[] signals);

	public void teardown ();
	public JFrame fetchFrame ();

	public void setValLabels (String[] s);

	public void setMinLabels (String[] s);

	public void setMaxLabels (String[] s);

	public void setAvgLabels (String[] s);

	public void setReadingsLabel (String s);

	public void setSignalStrenghLabel (String s);

	public void setProgressBar (int progress);

	public void addDoneButtonListener (ActionListener l);

}
