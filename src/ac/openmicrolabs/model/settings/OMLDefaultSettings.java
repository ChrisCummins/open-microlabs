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

package ac.openmicrolabs.model.settings;

import com.jcummins.gui.HTMLFont;
import com.jcummins.gui.HTMLFontset;

import ac.openmicrolabs.model.com.signals.OMLRaw;
import ac.openmicrolabs.model.com.signals.OMLSignal;
import ac.openmicrolabs.model.com.signals.OMLTemperature;
import ac.openmicrolabs.model.com.signals.OMLVoltage;
import gnu.io.SerialPort;

/**
 * @author Chris Cummins
 * 
 */
public abstract class OMLDefaultSettings {

	private static final Object[] BAUD_OPTIONS = { 1200, 2400, 4800, 9600,
			19200, 38400 };
	private static final Object[][] DATABIT_OPTIONS = {
			{ 5, 6, 7, 8 },
			{ SerialPort.DATABITS_5, SerialPort.DATABITS_6,
					SerialPort.DATABITS_7, SerialPort.DATABITS_8 } };
	private static final Object[][] STOPBIT_OPTIONS = {
			{ 1, 1.5, 2 },
			{ SerialPort.STOPBITS_1, SerialPort.STOPBITS_1_5,
					SerialPort.STOPBITS_2 } };
	private static final Object[][] PARITY_OPTIONS = {
			{ "None", "Even", "Odd" },
			{ SerialPort.PARITY_NONE, SerialPort.PARITY_EVEN,
					SerialPort.PARITY_ODD } };
	private static final Object[][] FLOWCONTROL_OPTIONS = {
			{ "None", "XONOFF In", "XONOFF Out", "RTSCTS In", "RTSCTS Out" },
			{ SerialPort.FLOWCONTROL_NONE, SerialPort.FLOWCONTROL_XONXOFF_IN,
					SerialPort.FLOWCONTROL_XONXOFF_OUT,
					SerialPort.FLOWCONTROL_RTSCTS_IN,
					SerialPort.FLOWCONTROL_RTSCTS_OUT } };

	private static final Object[][] TIME_RANGE_OPTIONS = {
			{ "1 second", "2.5 seconds", "5 seconds", "10 seconds",
					"30 seconds", "60 seconds", "10 minutes" },
			{ 1000.0, 2500.0, 5000.0, 10000.0, 30000.0, 60000.0, 600000 } };

	private static final String[] SIGNAL_TYPE_OPTIONS = { "Raw", "Voltage",
			"Temperature" };
	private static final OMLSignal[] SIGNAL_TYPES = { new OMLRaw(),
			new OMLVoltage(), new OMLTemperature() };

	private static final int FONT_SIZE = 3;
	private static final int BAUD_OPTIONS_SELECTED = 5;
	private static final int DATABIT_OPTIONS_SELETED = 3;
	private static final int TIME_RANGE_SELECTED = 3;

	/**
	 * Returns a default set of OMLSettings.
	 * 
	 * @return OMLSettings.
	 */
	public static OMLSettings get() {
		HTMLFont label = new HTMLFont(false, false, false, FONT_SIZE,
				"Verdana", HTMLFont.COLOR_BLACK);
		HTMLFont labelBold = new HTMLFont(true, false, false, FONT_SIZE,
				"Verdana", HTMLFont.COLOR_BLACK);
		HTMLFont labelRed = new HTMLFont(true, false, false, FONT_SIZE,
				"Verdana", HTMLFont.COLOR_RED);
		HTMLFont body = new HTMLFont(false, false, false, FONT_SIZE, "Arial",
				HTMLFont.COLOR_BLACK);

		HTMLFontset h = new HTMLFontset();
		h.add("label", label);
		h.add("label-bold", labelBold);
		h.add("label-red", labelRed);
		h.add("body", body);

		return new OMLSettings(h, BAUD_OPTIONS, BAUD_OPTIONS_SELECTED,
				DATABIT_OPTIONS, DATABIT_OPTIONS_SELETED, STOPBIT_OPTIONS, 0,
				PARITY_OPTIONS, 0, FLOWCONTROL_OPTIONS, 0, TIME_RANGE_OPTIONS,
				TIME_RANGE_SELECTED, SIGNAL_TYPE_OPTIONS, SIGNAL_TYPES);
	}

}
