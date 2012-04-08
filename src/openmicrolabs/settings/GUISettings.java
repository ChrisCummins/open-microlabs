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

package openmicrolabs.settings;

/**
 * This class contains the settings required by the Logger GUI.
 * 
 * @author Chris Cummins
 * 
 */
public class GUISettings
{

	private final double TIMERANGE;
	private final double MINY;
	private final double MAXY;

	/**
	 * Creates a GUISettings object from arguments.
	 * 
	 * @param timeRange
	 *            The time range to be displayed on the graph (ms), null if full
	 *            range.
	 * @param minY
	 *            The minimum Y value to be displayed on the graph.
	 * @param maxY
	 *            The maximum Y value to be displayed on the graph.
	 * @throws IllegalArgument
	 *             Exception If any of the values are out of range.
	 */
	public GUISettings (double timeRange, double minY, double maxY)
	{
		if (timeRange < 0 || minY < 0 || maxY < 0)
			throw new IllegalArgumentException (
					"Arguments must be greater than 0!");
		if (minY > 1023.0 || maxY > 1023.0)
			throw new IllegalArgumentException (
					"Y axis arguments too high! Range [0, 1023.0]");

		this.TIMERANGE = timeRange;
		this.MINY = minY;
		this.MAXY = maxY;
	}

	/**
	 * Return the time range for the graph (ms). If the time range is equal to
	 * the entire duration of the experiment, returns <code>null</code>.
	 * 
	 * @return Double.
	 */
	public double timeRange ()
	{
		return TIMERANGE;
	}

	/**
	 * Return the minimum Y value for the graph.
	 * 
	 * @return Double.
	 */
	public double minY ()
	{
		return MINY;
	}

	/**
	 * Return the maximum Y value for the grah.
	 * 
	 * @return Double.
	 */
	public double maxY ()
	{
		return MAXY;
	}

}
