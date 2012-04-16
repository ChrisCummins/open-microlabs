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

package ac.aston.oml.controller.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;

import ac.aston.oml.controller.OMLController;
import ac.aston.oml.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class LogSettingsFileListener extends OMLController implements
		ActionListener {

	private final ViewGateway v;

	public LogSettingsFileListener(ViewGateway v) {
		this.v = v;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		final JFileChooser fileChooser = new JFileChooser();
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			if (fileChooser.getSelectedFile().exists()) {
				boolean b = v
						.showYesNoPrompt("File exists, replace existing file?");
				if (b)
					v.ls().setFilepathLabel(
							fileChooser.getSelectedFile().getAbsolutePath());
			}
	}

}
