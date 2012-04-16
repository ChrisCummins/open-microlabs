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

package ac.aston.oml.model;

import ac.aston.oml.model.logger.AdvancedSettings;
import ac.aston.oml.model.logger.LogSettings;

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
	 * Set the logging settings for the logging session.
	 * 
	 * @param l
	 *            LogSettingsView.
	 * @param c
	 *            CommGateway.
	 * @throws IOException
	 *             In case of file logging IO error.
	 * @see ac.aston.oml.model.logger.LogSettings#LogSettings()
	 */
	void setLogSettings(LogSettings l, CommGateway c) throws IOException;

	/**
	 * Set the advanced settings for the logging session.
	 * @param a
	 */
	void setAdvancedSettings(AdvancedSettings a);

	/**
	 * Adds a series change listener to the microcontroller data model,
	 * therefore enabling new data to be read.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 */
	void addNewDataListener(SeriesChangeListener l);

	void addNewDataToLog(final TimeSeriesCollection data);

	boolean isLogging();

	/**
	 * Returns the LogSettingsView.
	 * 
	 * @return LogSettingsView currently in use.
	 * @see ac.aston.oml.model.logger.LogSettings#LogSettings()
	 */
	LogSettings getLogSettings();

	AdvancedSettings getAdvancedSettings();

	/**
	 * Returns the current data which has been read from the microcontroller.
	 * 
	 * @return TimeSeriesCollection
	 */
	TimeSeriesCollection getData();

}
