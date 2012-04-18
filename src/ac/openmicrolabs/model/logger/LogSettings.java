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

package ac.openmicrolabs.model.logger;

import ac.openmicrolabs.model.com.Datamask;

/**
 * This class contains the required settings for starting a Open MicroLabs
 * logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettings {

	private final Datamask datamaskVal;
	private final long readDelayVal;
	private final int readCountVal;
	private final String logPathVal;

	/**
	 * Constructs a LogSettingsView object from arguments.
	 * 
	 * @param datamask
	 *            The Datamask to be used.
	 * @param readDelay
	 *            The delay between reads (ms).
	 * @param readCount
	 *            The amount of readings to take (null for unlimited).
	 * @param logPath
	 *            The file path to the log (null for no file logging).
	 */
	public LogSettings(final Datamask datamask, final long readDelay,
			final int readCount, final String logPath) {
		this.datamaskVal = datamask;
		this.readDelayVal = readDelay;
		this.readCountVal = readCount;
		this.logPathVal = logPath;
	}

	/**
	 * Returns the datamask.
	 * 
	 * @return Datamask.
	 */
	public final Datamask datamask() {
		return datamaskVal;
	}

	/**
	 * Returns the delay between reads (ms).
	 * 
	 * @return Long.
	 */
	public final long readDelay() {
		return readDelayVal;
	}

	/**
	 * Returns the number of readings to take.
	 * 
	 * @return Int, or <code>null</code> for unlimited readings.
	 */
	public final int readCount() {
		return readCountVal;
	}

	/**
	 * Returns the path to the log file.
	 * 
	 * @return String, or <code>null</code> if not logging to file.
	 */
	public final String logPath() {
		return logPathVal;
	}

}
