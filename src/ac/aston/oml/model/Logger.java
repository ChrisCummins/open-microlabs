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

import java.util.Observable;
import java.util.Observer;


import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.data.signals.OMLSignal;

/**
 * This implementation of the Observer interface is responsible reading and
 * storing data read from the SerialLogger. It also serves as the means for
 * passing data to the Model.
 * 
 * @author Chris Cummins
 * 
 */
public class Logger implements Observer
{

	private final SerialLogger serialLogger;
	private final Thread serialLoggerThread;
	
	private SerialBuffer serialBuffer;
	private Thread serialBufferThread;
	
	private final TimeSeries[] series;
	private final TimeSeriesCollection seriesCollection;

	/**
	 * Creates a new Logger object. It adds itself as an observer to the
	 * SerialLogger argument and creates new TimeSeries and
	 * TimeSeriesCollections to appropriately store the data from the
	 * SerialLogger.
	 * 
	 * @param serialLogger
	 */
	public Logger (SerialLogger serialLogger)
	{
		this.serialLogger = serialLogger;
		this.serialBuffer = new SerialBuffer();

		this.serialLogger.addObserver (serialBuffer);
		this.serialBuffer.addObserver (this);
		
		this.serialLoggerThread = new Thread(serialLogger);
		this.serialBufferThread = new Thread(serialBuffer);

		this.series = new TimeSeries[serialLogger.getLogSettings ().datamask ()
				.activeSignals ().length];

		this.seriesCollection = new TimeSeriesCollection ();

		int index = 0;
		for (OMLSignal signal : serialLogger.getLogSettings ().datamask ().signals ())
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
		serialLoggerThread.start ();
		serialBufferThread.start ();
	}

	/**
	 * Stops a logging session.
	 */
	public void stopLogging ()
	{
		serialLoggerThread.interrupt ();
		serialBufferThread.interrupt ();
	}
	
	public boolean isLogging ()
	{
		return serialLoggerThread.isAlive ();
	}

	
	@Override
	public void update (Observable arg0, Object arg1)
	{
		//TODO: SeriesException handling
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
	 * @see ac.aston.oml.controller.NewDataListener#seriesChanged(org.jfree.data.general.SeriesChangeEvent)
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
