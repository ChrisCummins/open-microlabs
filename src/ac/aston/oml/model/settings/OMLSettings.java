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
 * This class contains all of the necessary proram settings to run the OML
 * software.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLSettings {
	private final HTMLFontset fontsetVal;
	private final Object[] baudOptionsVal;
	private final int baudOptionsSelectedVal;
	private final Object[][] databitOptionsVal;
	private final int databitOptionsSelectedVal;
	private final Object[][] stopbitOptionsVal;
	private final int stopbitOptionsSelectedVal;
	private final Object[][] parityOptionsVal;
	private final int parityOptionsSelectedVal;
	private final Object[][] flowOptionsVal;
	private final int flowOptionsSelectedVal;

	private final Object[][] graphTimeRangeOptionsVal;
	private final int graphTimeRangeOptionsSelectedVal;

	private final String[] signalTypeOptionsVal;
	private final OMLSignal[] signalTypesVal;

	/**
	 * Generates a new OMLSettings object.
	 * 
	 * @param fontset
	 *            HTMLFontset.
	 * @param baudOptions
	 *            Baud options.
	 * @param baudOptionsSelected
	 *            Default selected option.
	 * @param databitOptions
	 *            Databit options.
	 * @param databitOptionsSelected
	 *            Databit selected option.
	 * @param stopbitOptions
	 *            Stopbit options.
	 * @param stopbitOptionsSelected
	 *            Stopbit selected option.
	 * @param parityOptions
	 *            Parity options.
	 * @param parityOptionsSelected
	 *            Parity selected option.
	 * @param flowOptions
	 *            Flow control options.
	 * @param flowOptionsSelected
	 *            Flow control selected option.
	 * @param graphTimeRangeOptions
	 *            Time range options.
	 * @param graphTimeRangeOptionsSelected
	 *            Time range selected option.
	 * @param signalTypeOptions
	 *            Signal types options.
	 * @param signalTypes
	 *            Selected type option.
	 */
	public OMLSettings(final HTMLFontset fontset, final Object[] baudOptions,
			final int baudOptionsSelected, final Object[][] databitOptions,
			final int databitOptionsSelected, final Object[][] stopbitOptions,
			final int stopbitOptionsSelected, final Object[][] parityOptions,
			final int parityOptionsSelected, final Object[][] flowOptions,
			final int flowOptionsSelected,
			final Object[][] graphTimeRangeOptions,
			final int graphTimeRangeOptionsSelected,
			final String[] signalTypeOptions, final OMLSignal[] signalTypes) {
		this.fontsetVal = fontset;
		this.baudOptionsVal = baudOptions;
		this.baudOptionsSelectedVal = baudOptionsSelected;
		this.databitOptionsVal = databitOptions;
		this.databitOptionsSelectedVal = databitOptionsSelected;
		this.stopbitOptionsVal = stopbitOptions;
		this.stopbitOptionsSelectedVal = stopbitOptionsSelected;
		this.parityOptionsVal = parityOptions;
		this.parityOptionsSelectedVal = parityOptionsSelected;
		this.flowOptionsVal = flowOptions;
		this.flowOptionsSelectedVal = flowOptionsSelected;

		this.graphTimeRangeOptionsVal = graphTimeRangeOptions;
		this.graphTimeRangeOptionsSelectedVal = graphTimeRangeOptionsSelected;

		this.signalTypeOptionsVal = signalTypeOptions;
		this.signalTypesVal = signalTypes;
	}

	/**
	 * @return the fontset
	 */
	public final HTMLFontset getFontset() {
		return fontsetVal;
	}

	/**
	 * @return the baudOptions
	 */
	public final Object[] getBaudOptions() {
		return baudOptionsVal;
	}

	/**
	 * @return the baudOptionsSelected
	 */
	public final int getBaudOptionsSelected() {
		return baudOptionsSelectedVal;
	}

	/**
	 * @return the databitOptions
	 */
	public final Object[][] getDatabitOptions() {
		return databitOptionsVal;
	}

	/**
	 * @return the databitOptionsSelected
	 */
	public final int getDatabitOptionsSelected() {
		return databitOptionsSelectedVal;
	}

	/**
	 * @return the stopbitOptions
	 */
	public final Object[][] getStopbitOptions() {
		return stopbitOptionsVal;
	}

	/**
	 * @return the stopbitOptionsSelected
	 */
	public final int getStopbitOptionsSelected() {
		return stopbitOptionsSelectedVal;
	}

	/**
	 * @return the parityOptions
	 */
	public final Object[][] getParityOptions() {
		return parityOptionsVal;
	}

	/**
	 * @return the parityOptionsSelected
	 */
	public final int getParityOptionsSelected() {
		return parityOptionsSelectedVal;
	}

	/**
	 * @return the flowOptions
	 */
	public final Object[][] getFlowOptions() {
		return flowOptionsVal;
	}

	/**
	 * @return the flowOptionsSelected
	 */
	public final int getFlowOptionsSelected() {
		return flowOptionsSelectedVal;
	}

	/**
	 * @return the graphTimeRangeOptions
	 */
	public final Object[][] getGraphTimeRangeOptions() {
		return graphTimeRangeOptionsVal;
	}

	/**
	 * @return the graphTimeRangeOptionsSelected
	 */
	public final int getGraphTimeRangeOptionsSelected() {
		return graphTimeRangeOptionsSelectedVal;
	}

	/**
	 * @return the signalTypeOptions
	 */
	public final String[] getSignalTypeOptions() {
		return signalTypeOptionsVal;
	}

	/**
	 * @return the signalTypes
	 */
	public final OMLSignal[] getSignalTypes() {
		return signalTypesVal;
	}

}
