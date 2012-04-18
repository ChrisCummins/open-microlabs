/* Chris Cummins - 18 Apr 2012
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

package ac.aston.oml.test.model;

import ac.aston.oml.model.com.Datamask;
import ac.aston.oml.test.OMLTestSettings;

import jcummins.text.DividerGenerator;

/**
 * This class tests the functionality of the Datamask class.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class DatamaskTest {

	private static final String TITLE = "Datamask test";

	/**
	 * Runs the Datamask test.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main(final String[] args) {

		final Datamask d = new Datamask(OMLTestSettings.SIGNAL);
		final String asciiChar = new String(d.asciiChar());

		System.out
				.print(TITLE + "\n" + DividerGenerator.genDivider(TITLE, '='));
		System.out.println("Data request size: " + asciiChar.length());
		System.out.println("Character array: " + asciiChar);
		System.out.println("Binary string: " + d.binaryCode());

	}

}
