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
 * This interface specifies the required behaviour of the ViewGateway portion of the
 * Open MicroLabs MCV architecture. It is responsible for obtaining information
 * from the user via graphic interactions as well as providing a graphical
 * feedback of the Model data.
 * 
 * @author Chris Cummins
 * 
 */
public interface ViewGateway
{
	public void showMessage (String msg);
	public void showError (String ms);
	public boolean showYesNoPrompt (String msg);
	
	public CommSettingsView cs ();
	public LogSettingsView os ();
	public AdvancedSettingsView as ();
	public LoggerView lv ();

}
