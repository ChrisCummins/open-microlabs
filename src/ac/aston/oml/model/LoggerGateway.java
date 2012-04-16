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

import java.io.IOException;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.logger.AdvancedSettings;
import ac.aston.oml.model.logger.LogSettings;

public interface LoggerGateway {

	/**
	 * Starts a logging session. setLogSettings() must have been called
	 * beforehand.
	 * 
	 * @see ModelGateway#startLogging()
	 */
	public void startLogging() throws IOException;

	/**
	 * Stops a running logging session prematurely and prevents any more
	 * readings from being made.
	 */
	public void stopLogging();

	/**
	 * Set the settings for a logging session.
	 * 
	 * @param l
	 *            LogSettingsView.
	 * @see ac.aston.oml.model.logger.LogSettings#LogSettings()
	 */
	public void setLogSettings(LogSettings l, ComGateway c) throws IOException;

	public void setAdvancedSettings(AdvancedSettings a);

	/**
	 * Adds a series change listener to the microcontroller data model,
	 * therefore enabling new data to be read.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 */
	public void addNewDataListener(SeriesChangeListener l);

	public void addNewDataToLog(final TimeSeriesCollection data);

	public boolean isLogging();

	/**
	 * Returns the LogSettingsView.
	 * 
	 * @return LogSettingsView currently in use.
	 * @see ac.aston.oml.model.logger.LogSettings#LogSettings()
	 */
	public LogSettings getLogSettings();

	public AdvancedSettings getAdvancedSettings();

	/**
	 * Returns the current data which has been read from the microcontroller.
	 * 
	 * @return TimeSeriesCollection
	 */
	public TimeSeriesCollection getData();

}
