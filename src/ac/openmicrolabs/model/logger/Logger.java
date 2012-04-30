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

import ac.openmicrolabs.model.com.SerialBuffer;
import ac.openmicrolabs.model.com.signals.OMLSignal;
import ac.openmicrolabs.model.logger.report.ReportWriter;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the Observer interface is responsible reading and
 * storing data read from the SerialLogger. It also serves as the means for
 * passing data to the Model.
 * 
 * @author Chris Cummins
 * 
 */
public class Logger implements Observer {

	private static final int PAD65 = 65;

	private final SerialLogger serialLogger;
	private final Thread serialLoggerThread;

	private SerialBuffer serialBuffer;
	private Thread serialBufferThread;

	private final TimeSeries[] series;
	private final TimeSeriesCollection seriesCollection;

	private long readingCount;
	private long nullReadingCount;

	/**
	 * Creates a new Logger object. It adds itself as an observer to the
	 * SerialLogger argument and creates new TimeSeries and
	 * TimeSeriesCollections to appropriately store the data from the
	 * SerialLogger.
	 * 
	 * @param s
	 *            SerialLogger.
	 * @throws IOException
	 *             In case of File Logger error.
	 */
	public Logger(final SerialLogger s) throws IOException {
		this.serialLogger = s;
		this.serialBuffer = new SerialBuffer();

		this.serialLogger.addObserver(serialBuffer);
		this.serialBuffer.addObserver(this);

		this.serialLoggerThread = new Thread(s);
		this.serialBufferThread = new Thread(serialBuffer);

		this.series = new TimeSeries[s.getLogSettings().datamask()
				.activeSignals().length];

		this.seriesCollection = new TimeSeriesCollection();

		int index = 0;
		final OMLSignal[] signals = s.getLogSettings().datamask().signals();
		for (int i = 0; i < signals.length; i++) {
			if (signals[i] != null) {
				series[index] = new TimeSeries(
						(char) ((i / signals.length) + PAD65)
								+ "-0x"
								+ String.format("%02x",
										(i % signals.length) + 1).toUpperCase()
								+ " " + signals[i].name());
				seriesCollection.addSeries(series[index]);
				index++;
			}
		}

	}

	/**
	 * Starts a logging session.
	 */
	public final void startLogging() {
		serialLoggerThread.start();
		serialBufferThread.start();
	}

	/**
	 * Stops a logging session.
	 */
	public final void stopLogging() {
		serialLogger.stopThread();
		serialBuffer.stopThread();
		serialLoggerThread.interrupt();
		serialBufferThread.interrupt();
	}

	/**
	 * Saves an HTML report of a logging session to the specified filepath.
	 * 
	 * @param filepath
	 *            File path.
	 * @throws IOException
	 *             In case of file IO error.
	 */
	public final void saveReport(final String filepath) throws IOException {
		ReportWriter.generateReport(filepath, serialLogger.getLogSettings()
				.datamask().activeSignals(), seriesCollection);
	}

	/**
	 * Returns whether logging is active or not.
	 * 
	 * @return <code>true</code> if active, else <code>false</code>.
	 */
	public final boolean isLogging() {
		return serialLoggerThread.isAlive();
	}

	@Override
	public final void update(final Observable arg0, final Object arg1) {
		Double[] d = (Double[]) arg1;
		for (int i = 0; i < series.length; i++) {
			readingCount++;

			series[i].addOrUpdate(new Millisecond(), d[i]);

			if (d[i] == null) {
				nullReadingCount++;
			}
		}
	}

	/**
	 * Adds a SeriesChangeListener to the last TimeSeries in the collection so
	 * that new data will update any listeners.
	 * 
	 * @param l
	 *            SeriesChangeListener.
	 * @see ac.openmicrolabs.controller.listeners.LoggerNewDataListener#seriesChanged(org.jfree.data.general.SeriesChangeEvent)
	 */
	public final void addNewDataListener(final SeriesChangeListener l) {
		series[series.length - 1].addChangeListener(l);
	}

	/**
	 * Returns the TimeSeriesCollection containing all recorded data.
	 * 
	 * @return TimeSeriesCollection.
	 */
	public final TimeSeriesCollection getData() {
		return seriesCollection;
	}

	/**
	 * Returns the total number of readings made.
	 * 
	 * @return Long.
	 */
	public final long getReadingCount() {
		return readingCount;
	}

	/**
	 * Returns the number of null readings made.
	 * 
	 * @return Long.
	 */
	public final long getNullReadingCount() {
		return nullReadingCount;
	}

}
