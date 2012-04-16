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

package ac.aston.oml.model.logger;

import java.io.IOException;
import java.util.Observable;

import ac.aston.oml.include.AppDetails;
import ac.aston.oml.model.com.SerialReader;

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

	// private boolean isRunning;
	private Double[] databuffer;

	/**
	 * Creates a SerialLogger object form the given arguments. It then sets the
	 * SerialReader sleep time to the required amount calculated, and sets the
	 * size of the databuffer to numer of active signals in the datamask.
	 * 
	 * @param l
	 *            LogSettingsView.
	 * @param b
	 *            SerialReader.
	 */
	public SerialLogger(LogSettings l, SerialReader b) {
		this.logSettings = l;
		this.serialReader = b;
		this.setBufferSleepTime();
		this.restTime = l.readDelay() - serialReader.getSleepTime();
		this.databuffer = new Double[logSettings.datamask().activeSignals().length];
	}

	public void run() {
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
	 * Returns the contents of the databuffer.
	 * 
	 * @return Double array.
	 */
	public Double[] getDatabuffer() {
		return databuffer;
	}

	/**
	 * Returns the SerialReader currently in use.
	 * 
	 * @return SerialReader.
	 */
	public SerialReader getSerialBuffer() {
		return serialReader;
	}

	/**
	 * Returns the LogSettingsView currently in use.
	 * 
	 * @return LogSettingsView.
	 */
	public LogSettings getLogSettings() {
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

	private void takeReading() {
		try {
			// Get information from microcontroller.
			String stringbuffer = serialReader.sendDataRequest(logSettings
					.datamask().asciiChar());

			// Split information into seperate strings.
			String[] splitbuffer = stringbuffer.split(AppDetails
					.serialDelimiter()); //

			// Iterate over databuffer.
			for (int i = 0; i < databuffer.length; i++)
				try {
					// Convert strings to doubles.
					databuffer[i] = Double.parseDouble(splitbuffer[i]);
				} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
					// Else assign them null.
					databuffer[i] = null;
				}
		} catch (IOException e) {
			databuffer = null;
		}
		setChanged();
		notifyObservers(databuffer);
	}
}
