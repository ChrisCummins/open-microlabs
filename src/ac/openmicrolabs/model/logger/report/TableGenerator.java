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
 * @author Chris Cummins
 * 
 */
public abstract class TableGenerator {

	private static final int TABLE_BORDER_WIDTH = 1;

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

		table.add(generateHeader(activeSignals));

		for (int i = 0; i < t.getItemCount(0); i++) {
			table.add(generateRow(t, activeSignals, i));
		}

		return table;
	}

	private static HTMLTableRow generateHeader(final OMLSignal[] activeSignals) {

		final HTMLTableRow tr = new HTMLTableRow();
		tr.add(new HTMLTableHeader("Time"));
		for (OMLSignal s : activeSignals) {
			tr.add(new HTMLTableHeader(s.name()));
		}

		return tr;
	}

	private static HTMLTableRow generateRow(final TimeSeriesCollection t,
			final OMLSignal[] activeSignals, final int index) {

		final HTMLTableRow tr = new HTMLTableRow();

		tr.add(new HTMLTableData(t.getSeries(0).getTimePeriod(index).toString()));

		for (int i = 0; i < t.getSeriesCount(); i++) {
			final Number value = t.getSeries(i).getValue(index);

			if (value != null) {
				tr.add(new HTMLTableData(activeSignals[i].toString(value
						.doubleValue())));
			} else {
				tr.add(new HTMLTableData("-"));
			}
		}

		return null;
	}

}
