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

package ac.openmicrolabs.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;


import org.jfree.data.time.TimeSeriesCollection;

import com.jcummins.html.font.HTMLFontset;

/**
 * @author Chris Cummins
 * 
 */
public interface LoggerView {

	/**
	 * Initialise screens state data.
	 * 
	 * @param h
	 *            HTMLFontset.
	 * @param t
	 *            TimeSeriesCollection.
	 * @param graphTitle
	 *            Title of graph.
	 * @param graphMinY
	 *            Minimum Y value.
	 * @param graphMaxY
	 *            Maximum Y value.
	 * @param graphTimeRange
	 *            Time Range of graph (ms).
	 * @param signals
	 *            String array of all signals (include nulls).
	 */
	void init(HTMLFontset h, TimeSeriesCollection t, String graphTitle,
			double graphMinY, double graphMaxY, double graphTimeRange,
			String[] signals);

	/**
	 * Dispose of screen state data and free memory.
	 */
	void teardown();

	/**
	 * Fetch the parent frame.
	 * 
	 * @return JFrame.
	 */
	JFrame fetchFrame();

	/**
	 * Refresh view to show that Logging has completed.
	 * 
	 * @param graphTimeRange
	 *            New time range (ms).
	 */
	void setViewLoggingCompleted(double graphTimeRange);

	/**
	 * Set the text for value labels being read.
	 * 
	 * @param s
	 *            Array of strings, one per active signal.
	 */
	void setValLabels(String[] s);

	/**
	 * Set the text for minimum value labels being read.
	 * 
	 * @param s
	 *            Array of strings, one per active signal.
	 */
	void setMinLabels(String[] s);

	/**
	 * Set the text for maximum value labels being read.
	 * 
	 * @param s
	 *            Array of strings, one per active signal.
	 */
	void setMaxLabels(String[] s);

	/**
	 * Set the text for average value labels being read.
	 * 
	 * @param s
	 *            Array of strings, one per active signal.
	 */
	void setAvgLabels(String[] s);

	/**
	 * Set the text for the readings label.
	 * 
	 * @param s
	 *            String.
	 */
	void setReadingsLabel(String s);

	/**
	 * Set the text for the signal strength label.
	 * 
	 * @param s
	 *            String.
	 */
	void setSignalStrenghLabel(String s);

	/**
	 * Set new values for the progress bar.
	 * 
	 * @param current
	 *            Current reading count.
	 * @param max
	 *            Maximum reading count.
	 */
	void setProgressBar(int current, int max);

	/**
	 * Add an Action Listener to the Done/Cancel button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addDoneButtonListener(ActionListener l);

}
