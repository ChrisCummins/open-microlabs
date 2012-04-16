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

/**
 * @author Chris Cummins
 * 
 */
public interface AdvancedSettingsView {

	/**
	 * Initialise screens state data.
	 * 
	 * @param h
	 *            HTMLFontset.
	 */
	void init(HTMLFontset h);

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
	 * Sets the minimum Y box text.
	 * 
	 * @param s
	 *            String.
	 */
	void setMinYText(String s);

	/**
	 * Sets the maximum Y box text.
	 * 
	 * @param s
	 *            String.
	 */
	void setMaxYText(String s);

	/**
	 * Set the options for the time range selector.
	 * 
	 * @param o
	 *            Options (Strings).
	 * @param selectedIndex
	 *            Default selected index.
	 */
	void setTimeRangeOptions(Object[] o, int selectedIndex);

	/**
	 * Add an action listener to the done button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addDoneButtonListener(ActionListener l);

	/**
	 * Returns the index of the selected time range.
	 * 
	 * @return Int index.
	 */
	int getTimeRangeSelectedIndex();

	/**
	 * Returns the min y box text.
	 * 
	 * @return String.
	 */
	String getMinYText();

	/**
	 * Return the max y box text.
	 * 
	 * @return String.
	 */
	String getMaxYText();
}
