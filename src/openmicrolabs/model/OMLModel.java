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

import openmicrolabs.settings.CommSettings;
import openmicrolabs.settings.LogSettings;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the Model interface performs the necessary roles of an
 * Open MicroLabs model.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLModel implements Model
{
	private SerialBuffer buffer;
	private SerialReader reader;
	private OMLDataseries dataset;

	/**
	 * Tests the connection with the microcontroller at the set comm settings.
	 * setCommSettings() must have been called beforehand.
	 * 
	 */
	@Override
	public boolean testConnection () throws IOException
	{
		return buffer.testConnection ();
	}

	/**
	 * Creates a new OMLDataseries from the SerialReader and then starts
	 * logging.
	 */
	@Override
	public void startLogging ()
	{
		dataset = new OMLDataseries (reader);
		dataset.statLogging ();
	}

	/**
	 * Stops logging.
	 */
	@Override
	public void stopLogging ()
	{
		dataset.stopLogging ();
	}

	/**
	 * Creates a new SerialBuffer with the given CommSettings.
	 */
	@Override
	public void setCommSettings (CommSettings c) throws NoSuchPortException,
			PortInUseException, UnsupportedCommOperationException, IOException
	{
		buffer = new SerialBuffer (c);
	}

	/**
	 * Creates a new SerialReader with the given LogSettings, then updates the
	 * SerialBuffer to the latest version.
	 */
	@Override
	public void setLogSettings (LogSettings l)
	{
		reader = new SerialReader (l, buffer);
		buffer = reader.getSerialBuffer ();
	}

	/**
	 * Returns the CommSettings from the SerialBuffer.
	 */
	@Override
	public CommSettings getCommSettings ()
	{
		return buffer.getCommSettings ();
	}

	/**
	 * Returns the log settings from the Serialreader.
	 */
	@Override
	public LogSettings getLogSettings ()
	{
		return reader.getLogSettings ();
	}

	/**
	 * Returns the TimeSeriesCollection from the OMLDataseries.
	 */
	@Override
	public TimeSeriesCollection getData ()
	{
		return dataset.getData ();
	}

	/**
	 * Adds a new SeriesChangeListener to the OMLDataseries.
	 */
	@Override
	public void addNewDataListener (SeriesChangeListener l)
	{
		dataset.addNewDataListener (l);
	}

}
