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

package ac.aston.oml.model.com.signals;

import jcummins.maths.DecimalRounder;

/**
 * This extension of the OMLSignal class represents a voltage signal, with an
 * assumed AREF of 5V, thus giving a range of [0, 5]V. With 10 bits of accuracy,
 * the absolute accuray is +/- 9.76mV.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLVoltage extends OMLSignal {

	private static final double VOLTAGE_MULTIPLIER = 0.00488758553;

	@Override
	protected final double signalToValue(final double rawInt) {
		return rawInt * VOLTAGE_MULTIPLIER;
	}

	@Override
	protected final String signalToString(final double rawInt) {
		return (new DecimalRounder(2)).round(toValue(rawInt)) + "V";
	}

	@Override
	protected final String signalName() {
		return "Voltage";
	}

}
