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

package ac.aston.oml.model;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;


import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.data.AppDetails;
import ac.aston.oml.model.data.CommSettings;
import ac.aston.oml.model.data.LogSettings;

/**
 * This implementation of the Model interface performs the necessary roles of an
 * Open MicroLabs model.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLModel implements Model
{
	private SerialReader serialReader;
	private SerialLogger serialLogger;
	private Logger logger;

	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * @throws UnsupportedCommOperationException 
	 * @throws PortInUseException 
	 * @throws NoSuchPortException 
	 * 
	 */
	@Override
	public boolean commTest () throws NoSuchPortException, PortInUseException, UnsupportedCommOperationException, IOException
	{
		return serialReader.testConnection ();
	}
	
	public void commConnect () throws NoSuchPortException,
	PortInUseException, UnsupportedCommOperationException, IOException
	{
		serialReader.connect (AppDetails.name ());
	}

	/**
	 * Creates a new Logger from the SerialLogger and then starts
	 * logging.
	 */
	@Override
	public void startLogging ()
	{
		logger = new Logger (serialLogger);
		logger.startLogging ();
	}

	/**
	 * Stops logging.
	 */
	@Override
	public void stopLogging ()
	{
		logger.stopLogging ();
	}

	/**
	 * Creates a new SerialReader with the given CommSettingsView.
	 */
	@Override
	public void setCommSettings (CommSettings c)
	{
		serialReader = new SerialReader (c);
	}

	/**
	 * Creates a new SerialLogger with the given LogSettingsView, then updates the
	 * SerialReader to the latest version.
	 */
	@Override
	public void setLogSettings (LogSettings l)
	{
		serialLogger = new SerialLogger (l, serialReader);
		serialReader = serialLogger.getSerialBuffer ();
		if (serialReader.getSleepTime () > l.readDelay ())
			throw new IllegalArgumentException ("Minimum valid read delay is "
					+ serialReader.getSleepTime () + "ms!");
	}

	/**
	 * Returns the CommSettingsView from the SerialReader.
	 */
	@Override
	public CommSettings getCommSettings ()
	{
		return serialReader.getCommSettings ();
	}

	/**
	 * Returns the log settings from the Serialreader.
	 */
	@Override
	public LogSettings getLogSettings ()
	{
		return serialLogger.getLogSettings ();
	}

	/**
	 * Returns the TimeSeriesCollection from the Logger.
	 */
	@Override
	public TimeSeriesCollection getData ()
	{
		return logger.getData ();
	}

	/**
	 * Adds a new SeriesChangeListener to the Logger.
	 */
	@Override
	public void addNewDataListener (SeriesChangeListener l)
	{
		logger.addNewDataListener (l);
	}

	@Override
	public boolean isLogging ()
	{
		return logger.isLogging ();
	}

}
