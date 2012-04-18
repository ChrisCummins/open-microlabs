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

package ac.openmicrolabs.model.logger;

/**
 * This class contains the settings required by the SerialLogger GUI.
 * 
 * @author Chris Cummins
 * 
 */
public class AdvancedSettings {

	private static final double MIN_LEGAL_Y = 0.0;
	private static final double MAX_LEGAL_Y = 1023.0;

	private Double timeRangeVal;
	private final double minYVal;
	private final double maxYVal;

	/**
	 * Creates an AdvancedSettings object from arguments. Note that in case of
	 * invalid arguments, an IllegalArgumentException runtime exception will be
	 * thrown. See the exception message for further run time information.
	 * 
	 * @param timeRange
	 *            The time range to be displayed on the graph (ms), null if full
	 *            range.
	 * @param minY
	 *            The minimum Y value to be displayed on the graph.
	 * @param maxY
	 *            The maximum Y value to be displayed on the graph.
	 * @throws IllegalArgumentException
	 * 
	 */
	public AdvancedSettings(final Double timeRange, final double minY,
			final double maxY) {
		if (minY < MIN_LEGAL_Y || maxY < MIN_LEGAL_Y) {
			throw new IllegalArgumentException(
					"Y axis arguments too low! Acceptable range: [0, 1023.0]");
		}
		if (minY > MAX_LEGAL_Y || maxY > MAX_LEGAL_Y) {
			throw new IllegalArgumentException(
					"Y axis arguments too high! Acceptable range: [0, 1023.0]");
		}
		if (minY > maxY) {
			throw new IllegalArgumentException(
					"Y axis min value is larger than max value!");
		}
		this.timeRangeVal = timeRange;
		this.minYVal = minY;
		this.maxYVal = maxY;
	}

	/**
	 * Return the time range for the graph (ms). If the time range is equal to
	 * the entire duration of the experiment, returns <code>null</code>.
	 * 
	 * @return Double.
	 */
	public final Double timeRange() {
		return timeRangeVal;
	}

	/**
	 * Sets a new timeRange value, specified by argument.
	 * 
	 * @param timeRange
	 *            New timeRange value.
	 */
	public final void timeRange(final Double timeRange) {
		this.timeRangeVal = timeRange;
	}

	/**
	 * Return the minimum Y value for the graph.
	 * 
	 * @return Double.
	 */
	public final double minY() {
		return minYVal;
	}

	/**
	 * Return the maximum Y value for the graph.
	 * 
	 * @return Double.
	 */
	public final double maxY() {
		return maxYVal;
	}

}
