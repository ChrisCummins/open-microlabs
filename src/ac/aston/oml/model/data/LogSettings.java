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

package ac.aston.oml.model.data;

/**
 * This class contains the required settings for starting a Open MicroLabs
 * logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettings
{

	private final Datamask DATAMASK;
	private final long READDELAY;
	private final int READCOUNT;
	private final String LOGPATH;

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
	public LogSettings (Datamask datamask, long readDelay, int readCount,
			String logPath)
	{
		this.DATAMASK = datamask;
		this.READDELAY = readDelay;
		this.READCOUNT = readCount;
		this.LOGPATH = logPath;
	}

	/**
	 * Returns the datamask.
	 * 
	 * @return Datamask.
	 */
	public Datamask datamask ()
	{
		return DATAMASK;
	}

	/**
	 * Returns the delay between reads (ms).
	 * 
	 * @return Long.
	 */
	public long readDelay ()
	{
		return READDELAY;
	}

	/**
	 * Returns the number of readings to take.
	 * 
	 * @return Int, or <code>null</code> for unlimited readings.
	 */
	public int readCount ()
	{
		return READCOUNT;
	}

	/**
	 * Returns the path to the log file.
	 * 
	 * @return String, or <code>null</code> if not logging to file.
	 */
	public String logPath ()
	{
		return LOGPATH;
	}

}
