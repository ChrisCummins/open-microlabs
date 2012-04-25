/* Chris Cummins - 19 Apr 2012
 *
 * This file is part of openmicrolabs.
 *
 * openmicrolabs is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * openmicrolabs is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with openmicrolabs.  If not, see <http://www.gnu.org/licenses/>.
 */

package ac.openmicrolabs.controller.listeners;

import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.view.ViewGateway;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;

/**
 * @author Chris Cummins
 * 
 */
public class LoggerSaveReportListener implements ActionListener {

	private static final String FILE_EXTENSION = ".html";

	private final ModelGateway m;
	private final ViewGateway v;

	/**
	 * 
	 * @param model
	 *            ModelGateway for generating report file.
	 * @param view
	 *            ViewGateway for presenting feedback to the user.
	 */
	public LoggerSaveReportListener(final ModelGateway model,
			final ViewGateway view) {
		this.m = model;
		this.v = view;
	}

	@Override
	public final void actionPerformed(final ActionEvent arg0) {
		// Create session state data.
		final JFileChooser fileChooser = new JFileChooser();

		// Get report filepath from user.
		if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {

			final File file = new File(fileChooser.getSelectedFile()
					.getAbsolutePath() + FILE_EXTENSION);

			if (file.exists()) {
				boolean b = fileOverwrite(fileChooser.getSelectedFile());
				if (!b) {
					return;
				}
			}

			generateReport(fileChooser.getSelectedFile().getAbsolutePath());
		} else {
			return;
		}
	}

	/*
	 * Prompt user for file overwrite.
	 */
	private boolean fileOverwrite(final File file) {
		return v.showYesNoPrompt("File exists, replace existing file?");
	}

	/*
	 * Attempt to generate and save report.
	 */
	private void generateReport(final String path) {
		try {
			m.logger().saveReport(path + FILE_EXTENSION);
		} catch (IOException e) {
			v.showError("File write error! Unable to save report.");
		}
	}

}
