/* Chris Cummins - 12 Apr 2012
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

package ac.aston.oml.model.settings;

import ac.aston.oml.model.com.signals.OMLSignal;
import jcummins.gui.HTMLFontset;

/**
 * @author Chris Cummins
 * 
 */
public class OMLSettings {
	public final HTMLFontset fontset;
	public final Object[] baudOptions;
	public final int baudOptionsSelected;
	public final Object[][] databitOptions;
	public final int databitOptionsSelected;
	public final Object[][] stopbitOptions;
	public final int stopbitOptionsSelected;
	public final Object[][] parityOptions;
	public final int parityOptionsSelected;
	public final Object[][] flowOptions;
	public final int flowOptionsSelected;

	public final Object[][] graphTimeRangeOptions;
	public final int graphTimeRangeOptionsSelected;

	public final String[] signalTypeOptions;
	public final OMLSignal[] signalTypes;

	public OMLSettings(HTMLFontset fontset, Object[] baudOptions,
			int baudOptionsSelected, Object[][] databitOptions,
			int databitOptionsSelected, Object[][] stopbitOptions,
			int stopbitOptionsSelected, Object[][] parityOptions,
			int parityOptionsSelected, Object[][] flowOptions,
			int flowOptionsSelected, Object[][] graphTimeRangeOptions,
			int graphTimeRangeOptionsSelected, String[] signalTypeOptions,
			OMLSignal[] signalTypes) {
		this.fontset = fontset;
		this.baudOptions = baudOptions;
		this.baudOptionsSelected = baudOptionsSelected;
		this.databitOptions = databitOptions;
		this.databitOptionsSelected = databitOptionsSelected;
		this.stopbitOptions = stopbitOptions;
		this.stopbitOptionsSelected = stopbitOptionsSelected;
		this.parityOptions = parityOptions;
		this.parityOptionsSelected = parityOptionsSelected;
		this.flowOptions = flowOptions;
		this.flowOptionsSelected = flowOptionsSelected;

		this.graphTimeRangeOptions = graphTimeRangeOptions;
		this.graphTimeRangeOptionsSelected = graphTimeRangeOptionsSelected;

		this.signalTypeOptions = signalTypeOptions;
		this.signalTypes = signalTypes;
	}

}
