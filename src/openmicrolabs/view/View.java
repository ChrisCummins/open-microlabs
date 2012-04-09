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

package openmicrolabs.view;

import java.awt.event.ActionListener;

import org.jfree.data.time.TimeSeriesCollection;

import openmicrolabs.settings.CommSettings;
import openmicrolabs.settings.LogSettings;

/**
 * This interface specifies the required behaviour of the View portion of the
 * Open MicroLabs MCV architecture. It is responsible for obtaining information
 * from the user via graphic interactions as well as providing a graphical
 * feedback of the Model data.
 * 
 * @author Chris Cummins
 * 
 */
public interface View
{

	/**
	 * Display an information message to the user.
	 * 
	 * @param msg
	 *            Message to be displayed.
	 */
	public void showMessage (String msg);

	/**
	 * Display an error message to the user.
	 * 
	 * @param ms
	 *            Message to be displayed.
	 */
	public void showError (String ms);

	public boolean showYesNoPrompt (String msg);

	/**
	 * Get CommSettings from GUI.
	 * 
	 * @return CommSettings.
	 * @see openmicrolabs.settings.CommSettings#CommSettings()
	 */
	public CommSettings getCommSettings ();

	public void showOMLSettings ();

	/**
	 * Get LogSettings from GUI.
	 * 
	 * @return LogSettings.
	 * @see openmicrolabs.settings.LogSettings#LogSettings()
	 */
	public LogSettings getLogSettings ();

	public void showGraphSettings ();

	public void graphSettingsComplete ();

	/**
	 * Informs the view that logging has begun.
	 */
	public void loggingStarted (LogSettings l, TimeSeriesCollection t);

	/**
	 * Informs the view that new data has been added to the data model.
	 */
	public void updateViews ();

	public void returnFromLogScreen ();

	/**
	 * Add an action listener for when CommSettings are set.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.CommSettingsListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addCommSettingsListener (ActionListener l);

	/**
	 * Add an action listener for a request to test the connection.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.TestConnectionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addTestConnectionListener (ActionListener l);

	/**
	 * Add an action listener for a request to display the GUISettings view.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.ShowGUISettingsListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addShowGUISettingsListener (ActionListener l);

	/**
	 * Add an action listener for when GUISettings are set.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.GUISettingsListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addGUISettingsListener (ActionListener l);

	/**
	 * Add an action listener for a request to start logging.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.StartLoggingListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addStartLoggingListener (ActionListener l);

	/**
	 * Add an action listener for a request to cancel logging.
	 * 
	 * @param l
	 *            ActionListener.
	 * @see openmicrolabs.controller.CancelLoggingListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void addCancelLoggingListener (ActionListener l);

}
