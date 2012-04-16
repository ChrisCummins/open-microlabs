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

package ac.aston.oml;

import ac.aston.oml.controller.OMLController;
import ac.aston.oml.model.OMLModelGateway;
import ac.aston.oml.view.gui.OMLView;
import jcummins.gui.GUITools;

/**
 * This class is responsible for instantiating the Open MicroLabs program and
 * should be the only class that is run by users.
 * 
 * @author Chris Cummins
 * 
 */
public class OpenMicroLabs {

	/**
	 * Hide constructor of utility class.
	 */
	protected OpenMicroLabs() {
	}

	/**
	 * Instantiates a Controller and runs it.
	 * 
	 * @param args
	 *            none.
	 */
	public static void main(final String[] args) {
		GUITools.setNativeLookAndFeel();

		OMLController c = new OMLController();
		c.init(new OMLModelGateway(), new OMLView());
	}

}
