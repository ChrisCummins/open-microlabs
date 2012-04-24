/* Chris Cummins - 18 Apr 2012
 *
 * This file is part of openmicrolabs.
 *
 * openmicrolabs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * openmicrolabs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with openmicrolabs.  If not, see <http://www.gnu.org/licenses/>.
 */

package ac.openmicrolabs.model.logger.report;

import ac.openmicrolabs.model.com.signals.OMLSignal;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.jfree.data.time.TimeSeriesCollection;

import com.jcummins.html.font.HTMLFont;

/**
 * This class constructs HTML report files from.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class ReportWriter {

	private static final int TITLE_FONT_SIZE = 5;

	private static final HTMLFont TITLE_FONT = new HTMLFont(false, false,
			false, TITLE_FONT_SIZE, "Verdana", HTMLFont.COLOR_BLACK);

	private static final String HTML_START = "<html>";
	private static final String HTML_END = "</html>";

	/**
	 * Constructs a new report writer object.
	 * 
	 * @param path
	 *            File path.
	 * @param activeSignals
	 *            An array of OMLSignals representing the active signals.
	 * @param logData
	 *            The TimeSeriesCollection containing the logging session data.
	 * @throws IOException
	 *             In case of file IO error.
	 */
	public static void generateReport(final String path,
			final OMLSignal[] activeSignals, final TimeSeriesCollection logData)
			throws IOException {

		final BufferedWriter out = new BufferedWriter(new FileWriter(path));

		out.write(HTML_START);

		out.write(TITLE_FONT.format("Open MicroLabs Logging Report<br/><br/>"));

		// Write table.
		out.write(TableGenerator.getTable(logData, activeSignals).toString());
		out.write(HTML_END);

		out.close();

	}

}
