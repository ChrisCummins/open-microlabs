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

package ac.aston.oml.model.logger;

import java.io.IOException;

import org.jfree.data.general.SeriesChangeListener;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.ComGateway;
import ac.aston.oml.model.LoggerGateway;
import ac.aston.oml.model.logger.file.FileLogger;

/**
 * @author Chris Cummins
 * 
 */
public class OMLLoggerGateway implements LoggerGateway {

	private SerialLogger serialLogger;
	private Logger logger;
	private AdvancedSettings advancedSettings;
	private FileLogger fileLogger;

	@Override
	public void startLogging() throws IOException {
		logger = new Logger(serialLogger);
		logger.startLogging();
	}

	@Override
	public void stopLogging() {
		logger.stopLogging();
	}

	@Override
	public void setLogSettings(LogSettings l, ComGateway c) throws IOException, IllegalArgumentException {
		serialLogger = new SerialLogger(l, c.getSerialReader());
		c.setSerialReader(serialLogger.getSerialBuffer());
		if (c.getSerialReader().getSleepTime() > l.readDelay())
			throw new IllegalArgumentException("Minimum valid read delay is "
					+ c.getSerialReader().getSleepTime() + "ms!");

		// If necessary, instantiate a file logger.
		if (l.logPath() != null)
			fileLogger = new FileLogger(l.logPath(), l.datamask()
					.activeSignals());
		else
			fileLogger = null;
	}

	public void setAdvancedSettings(AdvancedSettings a) {
		this.advancedSettings = a;
	}

	@Override
	public void addNewDataToLog(TimeSeriesCollection data) {
		fileLogger.addNewData(data);
	}

	@Override
	public void addNewDataListener(SeriesChangeListener l) {
		logger.addNewDataListener(l);
	}

	@Override
	public boolean isLogging() {
		return logger.isLogging();
	}

	@Override
	public LogSettings getLogSettings() {
		return serialLogger.getLogSettings();
	}

	public AdvancedSettings getAdvancedSettings() {
		return advancedSettings;
	}

	@Override
	public TimeSeriesCollection getData() {
		return logger.getData();
	}

}
