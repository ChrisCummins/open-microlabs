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

package openmicrolabs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * This abstract class contains shared constants for all of the Open MicroLabs
 * project files.
 * 
 * @author Chris Cummins
 * 
 */
public abstract class AppDetails
{
	private static final String NAME = "Open MicroLabs";
	private static final String VERSION = "v0.3 Beta";
	private static final String AUTHOR = "Chris Cummins";
	private static final String DATA_DELIMITER = ", ";
	private static final String SERIAL_DELIMITER = ",";
	private static final long SLEEP_TIME = 25;

	/**
	 * Returns the program name.
	 * 
	 * @return Name string.
	 */
	public static final String name ()
	{
		return NAME;
	}

	/**
	 * Returns the program version.
	 * 
	 * @return Version string.
	 */
	public static final String version ()
	{
		return VERSION;
	}

	/**
	 * Returns the program author.
	 * 
	 * @return Author string.
	 */
	public static final String author ()
	{
		return AUTHOR;
	}

	/**
	 * Returns the delimiter to be used during File Writing.
	 * 
	 * @return Delimiter string.
	 */
	public static final String datDelimiter ()
	{
		return DATA_DELIMITER;
	}

	/**
	 * Returns the delimiter used by the microcontroller to seperate ADC values
	 * during serial comms.
	 * 
	 * @return Delimiter string.
	 */
	public static final String serialDelimiter ()
	{
		return SERIAL_DELIMITER;
	}

	/**
	 * Draws and returns the program logo.
	 * 
	 * @return Program logo image.
	 */
	public static final Image icon ()
	{
		BufferedImage bi = new BufferedImage (16, 16,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.getGraphics ();
		g.setColor (Color.white);
		g.fillRect (0, 0, 15, 15);
		g.setColor (Color.red);
		g.fillOval (0, 0, 15, 15);
		g.setColor (Color.black);
		g.fillOval (5, 4, 6, 8);
		g.fillRect (6, 7, 10, 3);
		g.dispose ();

		return bi;
	}

	/**
	 * Returns the time in milliseconds that should be allowed for the
	 * microcontroller to perform one ADC conversion and transmit the result to
	 * USART.
	 * 
	 * @return long sleep time per channel.
	 */
	public static final long sleepTime ()
	{
		return SLEEP_TIME;
	}

}
