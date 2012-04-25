/* Chris Cummins - 25 Apr 2012
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

package ac.openmicrolabs.model.com.signals;

import com.jcummins.maths.DecimalRounder;

/**
 * This class provides an abstraction for sound level measuring. Range 0 - 40
 * dB.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLSound extends OMLSignal {

	private static final double VOLTAGE_MULTIPLIER = 0.00488758553;
	private static final double SOUND_MULTIPLIER = 8.4;

	@Override
	protected final double signalToValue(final double rawInt) {
		return (rawInt * VOLTAGE_MULTIPLIER) * SOUND_MULTIPLIER;
	}

	@Override
	protected final String signalToString(final double rawInt) {
		return (new DecimalRounder(1)).round(signalToValue(rawInt)) + " dB";
	}

	@Override
	protected final String signalName() {
		// TODO Auto-generated method stub
		return null;
	}

}
