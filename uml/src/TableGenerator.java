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

import com.jcummins.html.table.HTMLTable;
import com.jcummins.html.table.HTMLTableData;
import com.jcummins.html.table.HTMLTableHeader;
import com.jcummins.html.table.HTMLTableRow;

import org.jfree.data.time.TimeSeriesCollection;

/**
 * This utility class is used to generate a HTMLTable from a
 * TimeSeriesCollection of OMLData readings.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class TableGenerator {

	private static final int TABLE_BORDER_WIDTH = 1;
	private static final String NULL_READING_TEXT = "-";

	/**
	 * Returns a representation of the TimeSeriesCollection as a table.
	 * 
	 * @param t
	 *            TimeSeriesCollection.
	 * @param activeSignals
	 *            An array of Strings representing the active signal names.
	 * @return Generated HTMLTable.
	 */
	public static final HTMLTable getTable(final TimeSeriesCollection t,
			final OMLSignal[] activeSignals) {
		final HTMLTable table = new HTMLTable(TABLE_BORDER_WIDTH);

		// Add header row.
		table.add(generateHeader(activeSignals));

		// Add data rows.
		for (int i = 0; i < t.getItemCount(0); i++) {
			table.add(generateRow(t, activeSignals, i));
		}

		return table;
	}

	/*
	 * Creates and returns a table row containing headers.
	 */
	private static HTMLTableRow generateHeader(final OMLSignal[] activeSignals) {

		final HTMLTableRow tr = new HTMLTableRow();

		// Add time header.
		tr.add(new HTMLTableHeader("Time"));

		// Add series headers.
		for (OMLSignal s : activeSignals) {
			tr.add(new HTMLTableHeader(s.name()));
		}

		return tr;
	}

	/*
	 * Creates and returns a table row containing data values.
	 */
	private static HTMLTableRow generateRow(final TimeSeriesCollection t,
			final OMLSignal[] activeSignals, final int index) {

		final HTMLTableRow tr = new HTMLTableRow();

		// Add time reading.
		tr.add(new HTMLTableData(t.getSeries(0).getTimePeriod(index).toString()));

		// Add series data readings.
		for (int i = 0; i < t.getSeriesCount(); i++) {
			// Get reading.
			final Number value = t.getSeries(i).getValue(index);

			// Add reading, or NULL_READING_TEXT if reading is null.
			tr.add(new HTMLTableData((value != null) ? activeSignals[i]
					.toString(value.doubleValue()) : NULL_READING_TEXT));
		}

		return tr;
	}
}
