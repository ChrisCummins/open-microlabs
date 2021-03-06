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

package ac.openmicrolabs.test.view;

import ac.openmicrolabs.model.settings.OMLSettings;
import ac.openmicrolabs.test.OMLTestSettings;
import ac.openmicrolabs.view.gui.OMLLoggerView;

import com.jcummins.gui.GUITools;

/**
 * Testing class for OMLLoggerView.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class DrawLoggerView {

	private static final double MIN_Y = 0.0;
	private static final double MAX_Y = 1023.0;
	private static final double GRAPH_RANGE = 30000;

	/**
	 * Renders a OMLLoggerView frame.
	 * 
	 * @param args
	 *            None.
	 */
	public static void main(final String[] args) {
		final OMLSettings c = OMLTestSettings.getOMLSettings();

		GUITools.setNativeLookAndFeel();

		final OMLLoggerView frame = new OMLLoggerView();
		frame.init(c.getFontset(), OMLTestSettings.getTimeSeriesCollection(),
				"[Graph title]", MIN_Y, MAX_Y, GRAPH_RANGE,
				OMLTestSettings.SIGNALS);

		String[] tmp = new String[OMLTestSettings.ACTIVE_SIGNAL_COUNT];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = "[?]";
		}

		frame.setValLabels(tmp);
		frame.setMinLabels(tmp);
		frame.setMaxLabels(tmp);
		frame.setAvgLabels(tmp);
		frame.setReadingsLabel("<?>");
		frame.repaint();
		GUITools.centreFrame(frame);
		
		frame.setSignalStrenghLabel("90%");

		frame.setViewLoggingCompleted(GRAPH_RANGE);
		
		frame.setVisible(true);
	}

}
