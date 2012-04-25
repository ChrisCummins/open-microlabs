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

import com.jcummins.html.font.HTMLFontset;


/**
 * @author Chris Cummins
 * 
 */
public interface LogSettingsView {

	/**
	 * Initialise screens state data.
	 * 
	 * @param h
	 *            HTMLFontset.
	 * @param pinCount
	 *            Pin count.
	 * @param signalTypes
	 *            An array of strings representing signal names, one per signal.
	 */
	void init(HTMLFontset h, int pinCount, String[] signalTypes);

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
	 * Set the number of pins in use and redraw.
	 * 
	 * @param p
	 *            Integer.
	 */
	void setPincount(int p);

	/**
	 * Set the options of the micronctroller slave box.
	 * 
	 * @param o
	 *            Objects (Strings).
	 */
	void setSlaveBoxOptions(Object[] o);

	/**
	 * Set the text for the file path label.
	 * 
	 * @param s
	 *            String.
	 */
	void setFilepathLabel(String s);

	/**
	 * Add an Action Listener to the File Select button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addFileButtonListener(ActionListener l);

	/**
	 * Add an Action Listener to the Done button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addDoneButtonListener(ActionListener l);

	/**
	 * Add an Action Listener to the AdvancedSettings button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addAdvancedButtonListener(ActionListener l);

	/**
	 * Add an Action Listener to the Slave options box.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addSlaveOptionsListener(ActionListener l);

	/**
	 * Return the selected index of the slave box.
	 * 
	 * @return Int index.
	 */
	int getSlaveBoxSelectedIndex();

	/**
	 * Return whether file logging is selected.
	 * 
	 * @return <code>true</code> if file logging is selected, else
	 *         <code>false</code>.
	 */
	boolean getLogToFile();

	/**
	 * Set the text for the file path text.
	 * 
	 * @return String.
	 */
	String getFilepathText();

	/**
	 * Returns an array of Integers corresponding to the selected signal type.
	 * If a channel is not in use, value will be <code>null</code>.
	 * 
	 * @return Array of Integers, <code>null</code> possible.
	 */
	Integer[] getSignalTypeOptions();

	/**
	 * Returns the text from the read delay box.
	 * 
	 * @return String.
	 */
	String getReadDelayText();

	/**
	 * Returns the text from the read count box.
	 * 
	 * @return String.
	 */
	String getReadCountText();

}
