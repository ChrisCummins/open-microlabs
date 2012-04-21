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

import ac.openmicrolabs.include.AppDetails;
import ac.openmicrolabs.model.com.SerialReader;

import java.io.IOException;
import java.util.Observable;

/**
 * This observable class performs the actions of reading data through the
 * SerialReader and then interpreting it and updating any observers of this new
 * data which can be accessed via the <code>getDatabuffer</code> method. Once
 * started, it will perform as many readings as specified by the
 * LogSettingsView.
 * 
 * @author Chris Cummins
 * 
 */
public class SerialLogger extends Observable implements Runnable {

	private final LogSettings logSettings;
	private final SerialReader serialReader;
	private final long restTime;
	private final int databufferSize;

	/**
	 * Creates a SerialLogger object form the given arguments. It then sets the
	 * SerialReader sleep time to the required amount calculated, and sets the
	 * size of the databuffer to the number of active signals in the datamask.
	 * 
	 * @param l
	 *            LogSettingsView.
	 * @param b
	 *            SerialReader.
	 * @throws IOException
	 *             In case of IO error.
	 */
	public SerialLogger(final LogSettings l, final SerialReader b)
			throws IOException {
		this.logSettings = l;
		this.serialReader = b;
		this.setBufferSleepTime();
		this.restTime = l.readDelay() - serialReader.getSleepTime();
		databufferSize = logSettings.datamask().activeSignals().length;
	}

	/**
	 * Takes a reading from the serial port.
	 */
	public final void run() {
		for (int i = 0; i < logSettings.readCount(); i++) {
			takeReading();
			try {
				Thread.sleep(restTime);
			} catch (InterruptedException e) {
				return;
			}
		}
	}

	/**
	 * Returns the SerialReader currently in use.
	 * 
	 * @return SerialReader.
	 */
	public final SerialReader getSerialBuffer() {
		return serialReader;
	}

	/**
	 * Returns the LogSettingsView currently in use.
	 * 
	 * @return LogSettingsView.
	 */
	public final LogSettings getLogSettings() {
		return logSettings;
	}

	/*
	 * Calculates the required SerialReader sleep time for the given datamask
	 * and sets it.
	 */
	private void setBufferSleepTime() {
		serialReader.setSleepTime(logSettings.datamask().activeSignals().length
				* AppDetails.sleepTime());
	}

	/*
	 * Performs a serial read and interprets data.
	 */
	private void takeReading() {
		// Setup databuffer.
		final Double[] databuffer = new Double[databufferSize];

		try {
			// Get information from microcontroller.
			final String stringbuffer = serialReader
					.sendDataRequest(logSettings.datamask().asciiChar());

			// Set splitbuffer.
			final String[] splitbuffer = stringbuffer.split(AppDetails
					.serialDelimiter()); //

			// Iterate over databuffer.
			for (int i = 0; i < databuffer.length; i++) {
				try {
					// Convert strings to doubles (remove non-numeric
					// characters).
					databuffer[i] = Double.parseDouble(splitbuffer[i]
							.replaceAll("[^\\d]", ""));
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					// Else assign them null.
					databuffer[i] = null;
				}
			}
		} catch (IOException e) {
			for (int i = 0; i < databuffer.length; i++) {
				databuffer[i] = null;
			}
		}

		// Update observers.
		setChanged();
		notifyObservers(databuffer);
	}
}
