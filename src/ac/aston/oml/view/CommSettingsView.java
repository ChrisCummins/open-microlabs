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
public interface CommSettingsView {

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
	 * Set the options for the com ports.
	 * 
	 * @param o
	 *            Objects (Strings).
	 */
	void setComOptions(Object[] o);

	/**
	 * Set the options for the baud rate.
	 * 
	 * @param o
	 *            Objects (Strings).
	 * @param index
	 *            Default option.
	 */
	void setBaudOptions(Object[] o, int index);

	/**
	 * Set the options for the data bits.
	 * 
	 * @param o
	 *            Objects (Strings).
	 * @param index
	 *            Default options.
	 */
	void setDataOptions(Object[] o, int index);

	/**
	 * Set the options for the stop bits.
	 * 
	 * @param o
	 *            Objects (Strings).
	 * @param index
	 *            Default options.
	 */
	void setStopOptions(Object[] o, int index);

	/**
	 * Set the options for the parity options.
	 * 
	 * @param o
	 *            Objects (Strings).
	 * @param index
	 *            Default options.
	 */
	void setParityOptions(Object[] o, int index);

	/**
	 * Set the options for the flow control.
	 * 
	 * @param o
	 *            Objects (Strings).
	 * @param index
	 *            Default options.
	 */
	void setFlowOptions(Object[] o, int index);

	/**
	 * Return selected com port option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedComOption();

	/**
	 * Return selected baud rate option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedBaudOption();

	/**
	 * Return selected data bits option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedDataOption();

	/**
	 * Return selected stop bits option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedStopOption();

	/**
	 * Return selected parity option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedParityOption();

	/**
	 * Return selected flow control option index.
	 * 
	 * @return Int index.
	 */
	int getSelectedFlowOption();

	/**
	 * Add an Action Listener to the Refresh Com Ports button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addRefreshButtonListener(ActionListener l);

	/**
	 * Add an Action Listener to the Done button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addDoneButtonListener(ActionListener l);

	/**
	 * Add an Action Listener to the Test Connection button.
	 * 
	 * @param l
	 *            Action Listener.
	 */
	void addTestButtonListener(ActionListener l);

}
