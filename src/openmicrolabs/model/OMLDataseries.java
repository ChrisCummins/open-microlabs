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

import java.util.Observable;
import java.util.Observer;

import openmicrolabs.signals.Signal;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the Observer interface is responsible reading and
 * storing data read from the SerialReader. It also serves as the means for
 * passing data to the Model.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLDataseries implements Observer
{

	private final SerialReader READER;
	private final TimeSeries[] SERIES;
	private final TimeSeriesCollection SERIESCOLLECTION;

	/**
	 * Creates a new OMLDataseries object. It adds itself as an observer to the
	 * SerialReader argument and creates new TimeSeries and
	 * TimeSeriesCollections to appropriately store the data from the
	 * SerialReader.
	 * 
	 * @param reader
	 */
	public OMLDataseries (SerialReader reader)
	{
		this.READER = reader;
		this.READER.addObserver (this);

		this.SERIES = new TimeSeries[READER.getLogSettings ().datamask ()
				.activeSignals ().length];

		this.SERIESCOLLECTION = new TimeSeriesCollection ();

		int index = 0;
		for (Signal signal : READER.getLogSettings ().datamask ().signals ())
			if (signal != null)
			{
				SERIES[index] = new TimeSeries ("0x0" + index);
				SERIESCOLLECTION.addSeries (SERIES[index]);
				index++;
			}
	}

	/**
	 * Starts a logging session.
	 */
	public void statLogging ()
	{
		READER.run ();
	}

	/**
	 * Stops a logging session.
	 */
	public void stopLogging ()
	{
		READER.stop ();
	}

	/**
	 * This method is fired every time the SerialReader updates it's databuffer.
	 * It add's new data to the TimeSeriesCollection.
	 */
	@Override
	public void update (Observable arg0, Object arg1)
	{
		Double[] d = READER.getDatabuffer ();
		for (int i = 0; i < SERIES.length; i++)
			SERIES[i].add (new Millisecond (), d[i]);
	}

	/**
	 * Adds a SeriesChangeListener to the last TimeSeries in the collection so
	 * that new data will update any listeners.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 * @see openmicrolabs.controller.NewDataListener#seriesChanged(org.jfree.data.general.SeriesChangeEvent)
	 */
	public void addNewDataListener (SeriesChangeListener l)
	{
		SERIES[SERIES.length - 1].addChangeListener (l);
	}

	/**
	 * Returns the TimeSeriesCollection containing all recorded data.
	 * @return TimeSeriesCollection.
	 */
	public TimeSeriesCollection getData ()
	{
		return SERIESCOLLECTION;
	}
}
