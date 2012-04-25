/* Chris Cummins - 21 Apr 2012
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

package ac.openmicrolabs.controller;

import ac.openmicrolabs.model.ModelGateway;
import ac.openmicrolabs.model.logger.LogSettings;
import ac.openmicrolabs.view.ViewGateway;

/**
 * @author Chris Cummins
 * 
 */
public class LoggingFinishedRunnable implements Runnable {

	private final ModelGateway m;
	private final ViewGateway v;
	private final LogSettings l;

	/**
	 * This class updates the LoggerView to show a finished logging session.
	 * 
	 * @param logSettings
	 *            Log Settings.
	 * @param model
	 *            ModelGateway for getting reading counts.
	 * @param view
	 *            ViewGateway for updating LoggerView.
	 */
	public LoggingFinishedRunnable(final LogSettings logSettings,
			final ModelGateway model, final ViewGateway view) {
		this.l = logSettings;
		this.m = model;
		this.v = view;
	}

	@Override
	public final void run() {
		v.lv().setViewLoggingCompleted(l.readCount() * l.readDelay());

		String s = "<b>" + m.logger().getReadingCount() + " readings complete";
		if (m.logger().getNullReadingCount() > 0) {
			s += " (" + m.logger().getNullReadingCount() + " reading";
			if (m.logger().getNullReadingCount() > 1) {
				s += "s";
			}
			s += " lost)";
		}
		s += ".</b>";
		v.lv().setSignalStrenghLabel(s);
	}
}
