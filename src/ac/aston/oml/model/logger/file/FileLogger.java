/* Chris Cummins - 16 Apr 2012
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

package ac.aston.oml.model.logger.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.include.AppDetails;
import ac.aston.oml.model.com.signals.OMLSignal;

/**
 * @author Chris Cummins
 * 
 */
public class FileLogger {

	private final String path;
	private final OMLSignal[] signals;

	public FileLogger(String logpath, OMLSignal[] signals) throws IOException {
		this.path = logpath;
		this.signals = signals;

		// Test open and close writer, throws IOException.
		final BufferedWriter out = new BufferedWriter(new FileWriter(path));
		out.close();
		writeHeader();
	}

	public void addNewData(TimeSeriesCollection data) {
		// Setup local variables.
		final int index = data.getItemCount(0) - 1;

		// Add the time.
		String s = data.getSeries(0).getTimePeriod(index).toString()
				+ AppDetails.datDelimiter();

		// Add data values.
		for (int i = 0; i < signals.length; i++) {
			final TimeSeries series = data.getSeries(i);
			final Number value = series.getValue(index);
			if (value != null)
				s += signals[i].toValue(value.doubleValue());
			s += AppDetails.datDelimiter();
		}

		// Write to file, removing last dat delimiter.
		writeLine(s.substring(0, s.length()
				- AppDetails.datDelimiter().length()));
	}

	private void writeHeader() {
		// Add time header.
		String s = "Time" + AppDetails.datDelimiter();

		// Add signal headers.
		for (int i = 0; i < signals.length; i++)
			s += signals[i].name() + AppDetails.datDelimiter();

		// Write to file, removing last dat delimiter.
		writeLine(s.substring(0, s.length()
				- AppDetails.datDelimiter().length()));
	}

	private void writeLine(String s) {
		try {
			final BufferedWriter out = new BufferedWriter(new FileWriter(path,
					true));
			out.write(s + AppDetails.datEOL());
			out.close();
		} catch (IOException e) {
			// Don't care.
		}
	}
}
