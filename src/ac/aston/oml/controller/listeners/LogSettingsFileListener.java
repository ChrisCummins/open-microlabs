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

import ac.aston.oml.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

/**
 * This implementation of the ActionListener interface is responsible for
 * responding to choose file requests from the log settings view.
 * 
 * @author Chris Cummins
 * 
 */
public class LogSettingsFileListener implements ActionListener {

	private final ViewGateway v;

	/**
	 * Creates a new Action Listener.
	 * 
	 * @param view
	 *            ViewGateway for setting screen state data.
	 */
	public LogSettingsFileListener(final ViewGateway view) {
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {
		// Create session state data.
		final JFileChooser fileChooser = new JFileChooser();

		// Display file chooser.
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			if (fileChooser.getSelectedFile().exists()) {
				fileOverwrite(fileChooser.getSelectedFile());
			}
		}
	}

	/*
	 * Prompt user for file overwrite.
	 */
	private void fileOverwrite(final File file) {
		boolean b = v.showYesNoPrompt("File exists, replace existing file?");

		if (b) {
			v.ls().setFilepathLabel(file.getAbsolutePath());
		}
	}

}
