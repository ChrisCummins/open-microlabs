/* Chris Cummins - 15 Apr 2012
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
import ac.aston.oml.model.com.signals.OMLVoltage;
import jcummins.gui.HTMLFont;
import jcummins.gui.HTMLFontset;
import gnu.io.SerialPort;

/**
 * @author Chris Cummins
 * 
 */
public class OMLDefaultSettings
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

	private static final Object[][] graphTimeRangeOptions = {
			{ "1 second", "2.5 seconds", "5 seconds", "10 seconds",
					"30 seconds", "60 seconds", "10 minutes" },
			{ 1000.0, 2500.0, 5000.0, 10000.0, 30000.0, 60000.0, 600000 } };
	
	private static final String[] signalTypeOptions = { "Raw", "Voltage" };
	private static final OMLSignal[] signalTypes = { new OMLSignal (), new OMLVoltage () };

	public static OMLSettings get ()
	{
		HTMLFont label = new HTMLFont (false, false, false, 3, "Verdana",
				HTMLFont.COLOR_BLACK);
		HTMLFont labelBold = new HTMLFont (true, false, false, 3, "Verdana",
				HTMLFont.COLOR_BLACK);
		HTMLFont labelRed = new HTMLFont (true, false, false, 3, "Verdana",
				HTMLFont.COLOR_RED);
		HTMLFont body = new HTMLFont (false, false, false, 3, "Arial",
				HTMLFont.COLOR_BLACK);

		HTMLFontset h = new HTMLFontset ();
		h.add ("label", label);
		h.add ("label-bold", labelBold);
		h.add("label-red", labelRed);
		h.add ("body", body);

		return new OMLSettings (h, baudOptions, 5, databitOptions, 3,
				stopbitOptions, 0, parityOptions, 0, flowcontrolOptions, 0,
				graphTimeRangeOptions, 3, signalTypeOptions, signalTypes);
	}

}
