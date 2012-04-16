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

package ac.aston.oml.view;

/**
 * This interface specifies the required behaviour of the ViewGateway portion of
 * the Open MicroLabs MCV architecture. It is responsible for obtaining
 * information from the user via graphic interactions as well as providing a
 * graphical feedback of the Model data.
 * 
 * @author Chris Cummins
 * 
 */
public interface ViewGateway {
	/**
	 * Display an information message to the user.
	 * 
	 * @param msg
	 *            Message to be displayed.
	 */
	void showMessage(String msg);

	/**
	 * Display an error message to the user.
	 * 
	 * @param ms
	 *            Error message to be displayed.
	 */
	void showError(String ms);

	/**
	 * Display a yes no prompt to the user and get result.
	 * 
	 * @param msg
	 *            Yes no message.
	 * @return <code>true</code> if user answered yes, else <code>false</code>
	 */
	boolean showYesNoPrompt(String msg);

	/**
	 * Returns an object which implements the CommSettingsView interface.
	 * 
	 * @return CommSettingsView.
	 */
	CommSettingsView cs();

	/**
	 * Returns an object which implements the LogSettingsView interface.
	 * 
	 * @return LogSettingsView.
	 */
	LogSettingsView ls();

	/**
	 * Returns an object which implements the AdvancedSettingsView interface.
	 * 
	 * @return AdvancedSettingsView.
	 */
	AdvancedSettingsView as();

	/**
	 * Returns an object which implements the LoggerView interface.
	 * 
	 * @return LoggerView.
	 */
	LoggerView lv();

}
