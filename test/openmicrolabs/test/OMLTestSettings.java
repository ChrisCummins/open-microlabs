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

package openmicrolabs.test;

import ac.aston.oml.model.settings.OMLSettings;
import gnu.io.SerialPort;
import jcummins.gui.CStyle;
import jcummins.gui.HTMLFont;


/**
 * @author Chris Cummins
 * 
 */
public class OMLTestSettings
{

	private static final Object[] baudOptions = { 1200, 2400, 4800, 9600,
			19200, 38400 };
	private static final Object[][] databitOptions = {
			{ 5, 6, 7, 8 },
			{ SerialPort.DATABITS_5, SerialPort.DATABITS_6,
					SerialPort.DATABITS_7, SerialPort.DATABITS_8 } };
	private static final Object[][] stopbitOptions = {
			{ 1, 1.5, 2 },
			{ SerialPort.STOPBITS_1, SerialPort.STOPBITS_1_5,
					SerialPort.STOPBITS_2 } };
	private static final Object[][] parityOptions = {
			{ "None", "Even", "Odd" },
			{ SerialPort.PARITY_NONE, SerialPort.PARITY_EVEN,
					SerialPort.PARITY_ODD } };
	private static final Object[][] flowcontrolOptions = {
			{ "None", "XONOFF In", "XONOFF Out", "RTSCTS In", "RTSCTS Out" },
			{ SerialPort.FLOWCONTROL_NONE, SerialPort.FLOWCONTROL_XONXOFF_IN,
					SerialPort.FLOWCONTROL_XONXOFF_OUT,
					SerialPort.FLOWCONTROL_RTSCTS_IN,
					SerialPort.FLOWCONTROL_RTSCTS_OUT } };

	public static OMLSettings getOMLSettings ()
	{
		HTMLFont label = new HTMLFont (false, false, false, 3, "Verdana",
				HTMLFont.COLOR_BLACK);
		HTMLFont labelBold = new HTMLFont (true, false, false, 3, "Verdana",
				HTMLFont.COLOR_BLACK);
		HTMLFont body = new HTMLFont (false, false, false, 2, "Helvetica",
				HTMLFont.COLOR_BLACK);

		CStyle c = new CStyle ();
		c.addFont ("label", label);
		c.addFont ("label-bold", labelBold);
		c.addFont ("body", body);

		OMLSettings s = new OMLSettings (c, baudOptions, databitOptions,
				stopbitOptions, parityOptions, flowcontrolOptions);
		return s;
	}

}
