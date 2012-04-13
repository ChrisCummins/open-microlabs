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

package ac.aston.oml.view.gui;

import ac.aston.oml.view.AdvancedSettingsView;
import ac.aston.oml.view.CommSettingsView;
import ac.aston.oml.view.LogSettingsView;
import ac.aston.oml.view.LoggerView;
import ac.aston.oml.view.ViewGateway;
import jcummins.gui.HTMLFont;
import jcummins.gui.HTMLFontset;

/**
 * This implementation of the ViewGateway interface is responsible for rendering
 * and updating the graphical user interface for user interactions/data
 * feedback.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLView implements ViewGateway
{

	private final OMLCommSettingsView cs;
	private final OMLLogSettingsView ls;
	private final OMLAdvancedSettingsView as;
	private final OMLLoggerView lv;

	public OMLView ()
	{
		this.cs = new OMLCommSettingsView ();
		this.ls = new OMLLogSettingsView ();
		this.as = new OMLAdvancedSettingsView ();
		this.lv = new OMLLoggerView ();
	}

	@Override
	public void showMessage (String msg)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showError (String ms)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean showYesNoPrompt (String msg)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CommSettingsView cs ()
	{
		return cs;
	}

	@Override
	public LogSettingsView os ()
	{
		return ls;
	}

	@Override
	public AdvancedSettingsView as ()
	{
		return as;
	}

	@Override
	public LoggerView lv ()
	{
		return lv;
	}

	public static HTMLFontset font()
	{
		HTMLFontset h = new HTMLFontset ();
		
		HTMLFont label =  new HTMLFont (false, false, false, 3, "Helvetica", HTMLFont.COLOR_BLACK);
		h.add ("label", label);
		label.setBold ();
		h.add ("label-bold", label);
		h.add ("body", new HTMLFont (false, false, false, 2, "Helvetica", HTMLFont.COLOR_BLACK));
		
		return h;
	}
}
