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

import openmicrolabs.signals.OMLSignal;

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

	private final SerialReader reader;
	private final TimeSeries[] series;
	private final TimeSeriesCollection seriesCollection;
	private Thread logSession; 

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
		this.reader = reader;

		this.series = new TimeSeries[reader.getLogSettings ().datamask ()
				.activeSignals ().length];

		this.seriesCollection = new TimeSeriesCollection ();

		int index = 0;
		for (OMLSignal signal : reader.getLogSettings ().datamask ().signals ())
			if (signal != null)
			{
				series[index] = new TimeSeries ("0x0" + index);
				seriesCollection.addSeries (series[index]);
				index++;
			}
	}

	/**
	 * Starts a logging session.
	 */
	public void startLogging ()
	{
		reader.addObserver (this);
		logSession = new Thread(reader);
		logSession.start ();
	}

	/**
	 * Stops a logging session.
	 */
	public void stopLogging ()
	{
		logSession.interrupt ();
	}

	/**
	 * This method is fired every time the SerialReader updates it's databuffer.
	 * It add's new data to the TimeSeriesCollection.
	 */
	@Override
	public void update (Observable arg0, Object arg1)
	{
		Double[] d = (Double[]) arg1;
		for (int i = 0; i < series.length; i++)
			series[i].add (new Millisecond (), d[i]);
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
		series[series.length - 1].addChangeListener (l);
	}

	/**
	 * Returns the TimeSeriesCollection containing all recorded data.
	 * @return TimeSeriesCollection.
	 */
	public TimeSeriesCollection getData ()
	{
		return seriesCollection;
	}
}
