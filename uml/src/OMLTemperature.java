/* Chris Cummins - 17 Apr 2012
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

package ac.openmicrolabs.model.com.signals;

import com.jcummins.maths.DecimalRounder;

/**
 * This extension of the OMLSignal class represents a temperature signal.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLTemperature extends OMLSignal {

	private static final double VOLTAGE_MULTIPLIER = 0.00488758553;
	private static final double VOLTAGE_OFFSET = -1.375;
	private static final double VOLTAGE_DIVIDOR = 0.0225;

	@Override
	public final double signalToValue(final double rawInt) {
		return ((rawInt * VOLTAGE_MULTIPLIER) + VOLTAGE_OFFSET)
				/ VOLTAGE_DIVIDOR;
	}

	@Override
	public final String signalToString(final double rawInt) {
		return (new DecimalRounder(2)).round(toValue(rawInt)) + "C";
	}

	@Override
	protected final String signalName() {
		return "Temperature";
	}

}
