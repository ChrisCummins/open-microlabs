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

import javax.swing.JOptionPane;

import org.jfree.data.time.TimeSeriesCollection;

import openmicrolabs.data.AppDetails;
import openmicrolabs.data.CommSettings;
import openmicrolabs.data.GraphSettings;
import openmicrolabs.data.LogSettings;
import openmicrolabs.signals.OMLVoltage;
import openmicrolabs.signals.OMLSignal;

/**
 * This implementation of the View interface is responsible for rendering and
 * updating the graphical user interface for user interactions/data feedback.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLView implements View
{
	public static final String LABEL_START = "<html><font size=\"3\" "
			+ "face=\"verdana\" color=\"black\">";
	public static final String LABEL_END = "</font></html>";

	private static final OMLSignal o1 = new OMLSignal ();
	private static final OMLVoltage o2 = new OMLVoltage ();
	public static final OMLSignal[] SIGNAL_TYPES = { o1, o2 };

	private CommSettingsView comView;
	private OMLSettingsView omlView;
	private GraphSettingsView graphView;
	private LoggerView logView;
	private FileLogger fileLogger;

	private static final double defaultTimeRange = 30000.0;
	private GraphSettings g = new GraphSettings (defaultTimeRange, 0, 1023);

	public OMLView ()
	{
		comView = new CommSettingsView ();
		comView.setVisible (true);
	}

	public static void showMessageBox (String msg)
	{
		JOptionPane.showMessageDialog (null, msg, AppDetails.name () + " "
				+ AppDetails.version (), JOptionPane.PLAIN_MESSAGE);
	}

	public static void showErrorBox (String msg)
	{
		JOptionPane.showMessageDialog (null, msg, AppDetails.name () + " "
				+ AppDetails.version (), JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void showMessage (String msg)
	{
		showMessageBox (msg);
	}

	@Override
	public void showError (String msg)
	{
		showErrorBox (msg);
	}

	@Override
	public boolean showYesNoPrompt (String msg)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CommSettings getCommSettings ()
	{
		return comView.getCommSettings ();
	}

	@Override
	public void showOMLSettings ()
	{
		comView.setVisible (false);
		omlView = new OMLSettingsView ();
		omlView.setVisible (true);
		graphView = new GraphSettingsView ();
	}

	@Override
	public void showGraphSettings ()
	{
		graphView.setVisible (true);
	}

	@Override
	public void graphSettingsComplete ()
	{
		// TODO: Update g from graphView.
		graphView.setVisible (false);
	}

	@Override
	public LogSettings getLogSettings ()
	{
		return omlView.getLogSettings ();
	}

	@Override
	public void loggingStarted (LogSettings l, TimeSeriesCollection t)
	{
		System.out.println("total time: " + (l.readCount () * l.readDelay () + ". timeRange: " + g.timeRange ()));
		if ((l.readCount () * l.readDelay ()) < defaultTimeRange)
			g.timeRange ((double) (l.readCount () * l.readDelay ()));
		else
			g.timeRange (defaultTimeRange);
		omlView.setVisible (false);
		System.out.println("g: " + g.timeRange ());
		logView = new LoggerView (l, g, t);
		logView.setVisible (true);
	}

	@Override
	public void updateViews ()
	{
		// TODO: update filelogger.
		logView.updateView ();
	}

	@Override
	public void addCommSettingsListener (ActionListener l)
	{
		comView.addDoneButtonListener (l);
	}

	@Override
	public void addTestConnectionListener (ActionListener l)
	{
		comView.addTestButtonListener (l);
	}

	@Override
	public void addShowGUISettingsListener (ActionListener l)
	{
		omlView.addGUIButtonListener (l);
	}

	@Override
	public void addGUISettingsListener (ActionListener l)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void addStartLoggingListener (ActionListener l)
	{
		omlView.addDoneButtonListener (l);
	}

	@Override
	public void addCancelLoggingListener (ActionListener l)
	{
		logView.addCancelButtonListener (l);
	}

	@Override
	public void returnFromLogScreen ()
	{
		omlView.setVisible (true);
		logView.setVisible (false);
		logView.dispose ();
	}

}
