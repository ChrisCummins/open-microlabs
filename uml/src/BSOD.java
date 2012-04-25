/* Chris Cummins - 24 Apr 2012
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

package ac.openmicrolabs.include;

import com.jcummins.gui.exhandler.ExHandler;

/**
 * @author Chris Cummins
 * 
 */
public class BSOD extends ExHandler {

	/**
	 * Creates a new BSOD.
	 * 
	 * @param t
	 *            Throwable.
	 */
	public BSOD(final Throwable t) {
		super(t);
	}

}
