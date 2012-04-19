/* Chris Cummins - 15 Apr 2012
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

package ac.openmicrolabs.model;

import ac.openmicrolabs.model.logger.AdvancedSettings;
import ac.openmicrolabs.model.logger.LogSettings;

import java.io.IOException;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * The CommGateway interface details the method contract for the Logger Model.
 * 
 * @author Chris Cummins
 * 
 */
public interface LoggerGateway {

	/**
	 * Starts a logging session. The comm model must have been setup beforehand.
	 * 
	 * @see ModelGateway#startLogging()
	 * @throws IOException
	 *             In case of file writing error.
	 */
	void startLogging() throws IOException;

	/**
	 * Stops a running logging session prematurely and prevents any more
	 * readings from being made.
	 */
	void stopLogging();

	/**
	 * Saves an HTML report of a logging session to the specified filepath.
	 * 
	 * @param filepath
	 *            File path.
	 * @throws IOException
	 *             In case of file IO error.
	 */
	void saveReport(String filepath) throws IOException;

	/**
	 * Set the logging settings for the logging session.
	 * 
	 * @param l
	 *            LogSettingsView.
	 * @param c
	 *            CommGateway.
	 * @throws IOException
	 *             In case of file logging IO error.
	 * @see ac.openmicrolabs.model.logger.LogSettings#LogSettings()
	 */
	void setLogSettings(LogSettings l, CommGateway c) throws IOException;

	/**
	 * Set the advanced settings for the logging session.
	 * 
	 * @param a
	 *            Advanced Settings.
	 * @see ac.openmicrolabs.model.logger.AdvancedSettings#AdvancedSettings(Double,
	 *      double, double)
	 */
	void setAdvancedSettings(AdvancedSettings a);

	/**
	 * Adds a series change listener to the microcontroller data model,
	 * therefore enabling new data to be read. Note that logging must have
	 * started first.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 */
	void addNewDataListener(SeriesChangeListener l);

	/**
	 * Add new data to File Logger.
	 * 
	 * @param data
	 *            TimeSeriesCollection.
	 * @see ac.openmicrolabs.model.logger.file.FileLogger#addNewData(TimeSeriesCollection)
	 */
	void addNewDataToLog(TimeSeriesCollection data);

	/**
	 * Returns whether a logger session is active or not.
	 * 
	 * @return <code>true</code> if active, else <code>false</code>.
	 */
	boolean isLogging();

	/**
	 * Returns the LogSettings.
	 * 
	 * @return LogSettings currently in use.
	 * @see ac.openmicrolabs.model.logger.LogSettings#LogSettings()
	 */
	LogSettings getLogSettings();

	/**
	 * Returns the AdvancedSettings.
	 * 
	 * @return Advanced Settings in use.
	 * @see ac.openmicrolabs.model.logger.AdvancedSettings#AdvancedSettings(Double,
	 *      double, double)
	 */
	AdvancedSettings getAdvancedSettings();

	/**
	 * Returns the current data which has been read from the microcontroller.
	 * 
	 * @return TimeSeriesCollection
	 */
	TimeSeriesCollection getData();

	/**
	 * Returns the total number of readings made.
	 * 
	 * @return Long.
	 */
	long getReadingCount();

	/**
	 * Returns the number of null readings made.
	 * 
	 * @return Long.
	 */
	long getNullReadingCount();

}
