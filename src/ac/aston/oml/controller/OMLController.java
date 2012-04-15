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

package ac.aston.oml.controller;

import ac.aston.oml.model.ModelGateway;
import ac.aston.oml.model.settings.OMLSettings;
import ac.aston.oml.view.ViewGateway;

import jcummins.gui.GUITools;

/**
 * This implementation of the Controller interface performs the duties of
 * instantiating a model and view, then attaching the required listeners.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLController implements Controller
{
	protected ModelGateway m;
	protected ViewGateway v;

	@Override
	public void init (ModelGateway m, ViewGateway v)
	{
		this.m = m;
		this.v = v;

		attachListeners ();

		renderCommSettingsView ();
	}

	private void attachListeners ()
	{
		v.cs ().addRefreshButtonListener (
				new CommSettingsRefreshPortsListener (m, v));
		v.cs ().addTestButtonListener (new CommSettingsTestListener (m, v));
		v.cs ().addDoneButtonListener (new CommSettingsDoneListener (m, v));

		v.ls ().addFileButtonListener (new LogSettingsFileListener (m, v));
		v.ls ().addAdvancedButtonListener (
				new AdvancedSettingsShowListener (m, v));
		v.ls ().addDoneButtonListener (new LogSettingsDoneListener (m, v));
		v.ls ().addSlaveOptionsListener (
				new LogSettingsSlaveOptionsListener (v));

		v.lv ().addDoneButtonListener (new LoggerDoneListener (m, v));

		v.as ()
				.addDoneButtonListener (
						new AdvancedSettingsDoneListenner (m, v));

		// m.addNewDataListener (new ModelNewDataListener (v));
	}

	private void renderCommSettingsView ()
	{
		final OMLSettings c = m.getOMLSettings ();
		m.c ().refreshCommPorts ();

		v.cs ().init (m.getOMLSettings ().fontset);
		v.cs ().setBaudOptions (c.baudOptions, c.baudOptionsSelected);
		v.cs ().setComOptions (m.c ().getCommPorts ()[0]);
		v.cs ().setDataOptions (c.databitOptions[0], c.databitOptionsSelected);
		v.cs ().setStopOptions (c.stopbitOptions[0], c.stopbitOptionsSelected);
		v.cs ().setParityOptions (c.parityOptions[0], c.parityOptionsSelected);
		v.cs ().setFlowOptions (c.flowOptions[0], c.flowOptionsSelected);

		GUITools.centreFrame (v.cs ().fetchFrame ());
		v.cs ().fetchFrame ().setVisible (true);
	}

}
