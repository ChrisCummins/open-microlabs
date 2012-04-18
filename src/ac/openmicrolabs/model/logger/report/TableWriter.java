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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import com.jcummins.html.table.HTMLTable;

/**
 * @author Chris Cummins
 * 
 */
public class TableWriter {

	/**
	 * 
	 * @param path
	 *            File path to output html file.
	 * @param t
	 *            Table to be written.
	 * @throws IOException
	 *             In case of IO file error.
	 */
	public TableWriter(final HTMLTable t, final String path) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(path));

		out.write(t.toString());
		out.close();
	}

}
