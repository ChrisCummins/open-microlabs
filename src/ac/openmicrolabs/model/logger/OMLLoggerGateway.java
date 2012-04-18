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

package ac.openmicrolabs.model.logger;

import ac.openmicrolabs.model.CommGateway;
import ac.openmicrolabs.model.LoggerGateway;
import ac.openmicrolabs.model.logger.file.FileLogger;

import java.io.IOException;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

/**
 * This implementation of the LoggerGateway interface provides all of the
 * necessary functionality to adhere to the contract.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLLoggerGateway implements LoggerGateway {

	private SerialLogger serialLogger;
	private Logger logger;
	private AdvancedSettings advancedSettings;
	private FileLogger fileLogger;

	@Override
	public final void startLogging() throws IOException {
		logger = new Logger(serialLogger);
		logger.startLogging();
	}

	@Override
	public final void stopLogging() {
		logger.stopLogging();
	}

	@Override
	public final void setLogSettings(final LogSettings l, final CommGateway c)
			throws IOException {
		serialLogger = new SerialLogger(l, c.getSerialReader());
		c.setSerialReader(serialLogger.getSerialBuffer());

		// Throw a runtime exception if read delay is too small.
		if (c.getSerialReader().getSleepTime() > l.readDelay()) {
			throw new IllegalArgumentException("Minimum valid read delay is "
					+ c.getSerialReader().getSleepTime() + "ms!");
		}

		// If necessary, instantiate a file logger.
		if (l.logPath() != null) {
			fileLogger = new FileLogger(l.logPath(), l.datamask()
					.activeSignals());
		} else {
			fileLogger = null;
		}
	}

	@Override
	public final void setAdvancedSettings(final AdvancedSettings a) {
		this.advancedSettings = a;
	}

	@Override
	public final void addNewDataToLog(final TimeSeriesCollection data) {
		fileLogger.addNewData(data);
	}

	@Override
	public final void addNewDataListener(final SeriesChangeListener l) {
		logger.addNewDataListener(l);
	}

	@Override
	public final boolean isLogging() {
		return logger.isLogging();
	}

	@Override
	public final LogSettings getLogSettings() {
		return serialLogger.getLogSettings();
	}

	@Override
	public final AdvancedSettings getAdvancedSettings() {
		return advancedSettings;
	}

	@Override
	public final TimeSeriesCollection getData() {
		return logger.getData();
	}

}
