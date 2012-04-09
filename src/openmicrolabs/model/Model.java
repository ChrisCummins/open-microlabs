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

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

import openmicrolabs.data.CommSettings;
import openmicrolabs.data.LogSettings;

/**
 * This interface specifies the required behaviour of a model class for the Open
 * MicroLabs software. It handles data flow to and from the controller and is
 * responsible for serial communications with the microcontroller along with
 * receiving, interpreting and storing the read data.
 * 
 * @author Chris Cummins
 * 
 */
public interface Model
{

	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * 
	 * @return <code>true</code> if connection is established, else
	 *         <code>false</code>.
	 * @throws IOException
	 *             In case of IO error
	 * @see Model#setCommSettings(CommSettings) .
	 */
	public boolean testConnection () throws NoSuchPortException,
	PortInUseException, UnsupportedCommOperationException, IOException;

	public void connect () throws NoSuchPortException,
	PortInUseException, UnsupportedCommOperationException, IOException;
	
	/**
	 * Starts a logging session. setLogSettings() must have been called
	 * beforehand.
	 * 
	 * @see Model#startLogging()
	 */
	public void startLogging ();

	public boolean isLogging ();
	
	/**
	 * Stops a running logging session prematurely and prevents any more
	 * readings from being made.
	 */
	public void stopLogging ();

	/**
	 * Set the comm settings for serial communications with the microcontroller.
	 * 
	 * @param c
	 *            CommSettings.
	 * @see openmicrolabs.data.CommSettings#CommSettings()
	 */
	public void setCommSettings (CommSettings c);

	/**
	 * Set the settings for a logging session.
	 * 
	 * @param l
	 *            LogSettings.
	 * @see openmicrolabs.data.LogSettings#LogSettings()
	 */
	public void setLogSettings (LogSettings l);

	/**
	 * Returns the set CommSettings.
	 * 
	 * @return CommSettings currently in use.
	 * @see openmicrolabs.data.CommSettings#CommSettings()
	 */
	public CommSettings getCommSettings ();

	/**
	 * Returns the LogSettings.
	 * 
	 * @return LogSettings currently in use.
	 * @see openmicrolabs.data.LogSettings#LogSettings()
	 */
	public LogSettings getLogSettings ();

	/**
	 * Returns the current data which has been read from the microcontroller.
	 * 
	 * @return TimeSeriesCollection
	 */
	public TimeSeriesCollection getData ();

	/**
	 * Adds a series change listener to the microcontroller data model,
	 * therefore enabling new data to be read.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 */
	public void addNewDataListener (SeriesChangeListener l);

}
