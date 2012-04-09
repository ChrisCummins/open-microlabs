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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import cummins.gui.GUITools;
import cummins.maths.MathsTools;

import openmicrolabs.AppDetails;
import openmicrolabs.settings.GUISettings;
import openmicrolabs.settings.LogSettings;
import openmicrolabs.signals.OMLSignal;

/**
 * This extension of JFrame displays the data read during a logging session.
 * 
 * @author Chris Cummins
 */
public class LoggerView extends JFrame
{
	/** Serial UID. */
	private static final long serialVersionUID = -4475975253216864808L;
	private static final String graphTitle = AppDetails.name () + " "
			+ AppDetails.version ();
	private static final String graphXlabel = null;
	private static final String graphYlabel = null;

	// GUI CONFIG --------------------------------------------------------------
	private static final int frameWidth = 800;
	private static final int frameHeight = 600;
	private static final int graphHeight = frameHeight - 200;
	private static final int btmHeight = 80;

	private final JLabel[] chanLabel;
	private final JLabel[] typeLabel;
	private final JLabel[] valueLabel;
	private final JLabel[] minLabel;
	private final JLabel[] maxLabel;
	private final JLabel[] avgLabel;

	private final JPanel btmPanel = new JPanel ();
	private final JLabel footerLabel = new JLabel ("");
	private JProgressBar progressBar;
	private final JButton doneButton = new JButton ("Cancel");

	private final LogSettings l;
	private final GUISettings g;
	private final TimeSeriesCollection dataset;

	public LoggerView (LogSettings l, GUISettings g,
			TimeSeriesCollection dataset)
	{
		this.l = l;
		this.g = g;
		this.dataset = dataset;

		chanLabel = new JLabel[8];
		typeLabel = new JLabel[l.datamask ().activeSignals ().length];
		valueLabel = new JLabel[l.datamask ().activeSignals ().length];
		minLabel = new JLabel[l.datamask ().activeSignals ().length];
		maxLabel = new JLabel[l.datamask ().activeSignals ().length];
		avgLabel = new JLabel[l.datamask ().activeSignals ().length];

		this.setTitle (AppDetails.name ());
		this.setSize (frameWidth, frameHeight);
		this.setResizable (false);
		this.setBackground (Color.white);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		GUITools.centreFrame (this);
		this.setContentPane (this.createContentPane ());
		this.setIconImage (AppDetails.icon ());
	}

	public void updateView ()
	{
		int currentReads = dataset.getItemCount (0) + 1;

		// Set progressBar.
		progressBar.setValue (currentReads);

		// Set title.
		this.setTitle ("Read " + currentReads + " of " + l.readCount () + " ("
				+ ((l.readCount () - currentReads) * l.readDelay ()) / 1000
				+ "s remaining)");

		// Update read label.
		// TODO: True signal strength calculation.
		if (progressBar.getValue () > 0)
			footerLabel.setText ("<html><b>"
					+ MathsTools.percent (currentReads, l.readCount (), 1) * 2
					+ "% signal strength</b></html>");

		// Update results panel.
		for (int i = 0; i < dataset.getSeriesCount (); i++)
		{
			TimeSeries series = dataset.getSeries (i);
			Double latestData = (Double) series.getValue (currentReads - 1);
			OMLSignal dataSignal = l.datamask ().activeSignals ()[i];
			// Calculate sum of all items in series.
			double totalValue = 0;
			for (Object j : series.getItems ())
			{
				TimeSeriesDataItem n = (TimeSeriesDataItem) j;
				totalValue += n.getValue ().doubleValue ();
			}

			// Update text labels.
			if (latestData != null)
				valueLabel[i].setText (OMLView.LABEL_START + "<b>"
						+ dataSignal.toString (latestData) + "</b>"
						+ OMLView.LABEL_END);
			else
				valueLabel[i].setText (OMLView.LABEL_START
						+ "<b><font color=\"red\">!!!</font></b>"
						+ OMLView.LABEL_END);
			minLabel[i].setText (dataSignal.toString (series.getMinY ()));
			maxLabel[i].setText (dataSignal.toString (series.getMaxY ()));
			avgLabel[i].setText (dataSignal.toString (totalValue
					/ series.getItemCount ()));
		}

	}

	public void addCancelButtonListener (ActionListener l)
	{
		doneButton.addActionListener (l);
	}

	private JPanel createContentPane ()
	{
		// Set up main panel.
		JPanel mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		mainPanel.setBackground (Color.white);

		// Create graph.
		final JFreeChart snsChart = ChartFactory.createTimeSeriesChart (
				graphTitle, graphXlabel, graphYlabel, dataset, true, true,
				false);
		final XYPlot plot = snsChart.getXYPlot ();
		ValueAxis axis = plot.getDomainAxis ();
		axis.setAutoRange (true);
		if (g.timeRange () != null)
			axis.setFixedAutoRange (g.timeRange ());
		else
			axis.setFixedAutoRange (l.readCount () * l.readDelay ());
		axis = plot.getRangeAxis ();
		axis.setRange (0.0, 1023.0);
		ChartPanel snsChartPanel = new ChartPanel (snsChart);
		snsChartPanel.setPreferredSize (new Dimension (frameWidth - 20,
				graphHeight - 10));

		// Set up graph panel.
		JPanel graphPanel = new JPanel ();
		graphPanel.setSize (frameWidth - 20, graphHeight);
		graphPanel.setLocation (0, 0);
		graphPanel.setBackground (Color.white);
		graphPanel.add (snsChartPanel);

		// Set up results panel.
		JPanel resultsPanel = new JPanel ();
		resultsPanel.setLayout (new GridLayout (6, 9));
		resultsPanel
				.setSize (frameWidth, frameHeight - btmHeight - graphHeight);
		resultsPanel.setLocation (5, graphHeight + 5);
		resultsPanel.setBackground (Color.white);

		// Set up bottom panel.
		btmPanel.setLayout (null);
		btmPanel.setSize (frameWidth, btmHeight);
		btmPanel.setLocation (0, this.getHeight () - btmHeight);
		btmPanel.setBackground (Color.white);

		// Instantiate results panel objects.

		for (int j = 0; j < l.datamask ().activeSignals ().length; j++)
		{
			typeLabel[j] = new JLabel (
					l.datamask ().activeSignals ()[j].name ());
			typeLabel[j].setHorizontalAlignment (JLabel.CENTER);
			valueLabel[j] = new JLabel ("");
			valueLabel[j].setHorizontalAlignment (JLabel.CENTER);
			minLabel[j] = new JLabel ("");
			minLabel[j].setHorizontalAlignment (JLabel.CENTER);
			maxLabel[j] = new JLabel ("");
			maxLabel[j].setHorizontalAlignment (JLabel.CENTER);
			avgLabel[j] = new JLabel ("");
			avgLabel[j].setHorizontalAlignment (JLabel.CENTER);
		}

		resultsPanel.add (new JLabel (""));
		for (int i = 0; i < 8; i++)
		{
			chanLabel[i] = new JLabel (OMLView.LABEL_START + "0x0" + i
					+ OMLView.LABEL_END);
			chanLabel[i].setHorizontalAlignment (JLabel.CENTER);
			if (!l.datamask ().pin (i))
				chanLabel[i].setEnabled (false);
			resultsPanel.add (chanLabel[i]);
		}

		resultsPanel.add (new JLabel (""));
		int index = 0;
		for (int i = 0; i < 8; i++)
		{
			if (chanLabel[i].isEnabled ())
			{
				resultsPanel.add (typeLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));
		}

		resultsPanel.add (new JLabel (""));
		index = 0;
		for (int i = 0; i < 8; i++)
		{
			if (chanLabel[i].isEnabled ())
			{
				resultsPanel.add (valueLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));
		}

		resultsPanel.add (new JLabel ("Min:"));
		index = 0;
		for (int i = 0; i < 8; i++)
		{
			if (chanLabel[i].isEnabled ())
			{
				resultsPanel.add (minLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));
		}

		resultsPanel.add (new JLabel ("Max:"));
		index = 0;
		for (int i = 0; i < 8; i++)
		{
			if (chanLabel[i].isEnabled ())
			{
				resultsPanel.add (maxLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));
		}

		resultsPanel.add (new JLabel ("Avg:"));
		index = 0;
		for (int i = 0; i < 8; i++)
		{
			if (chanLabel[i].isEnabled ())
			{
				resultsPanel.add (avgLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));
		}

		// Instantiate bottom panel objects.
		footerLabel.setSize (140, 30);
		footerLabel.setLocation (10, 18);
		doneButton.setIcon (new ImageIcon ("img/22x22/stop.png"));
		doneButton.setSize (100, 30);
		doneButton.setLocation (frameWidth - 20 - doneButton.getWidth (), 15);
		progressBar = new JProgressBar (0, l.readCount ());
		progressBar.setSize (frameWidth - 40 - doneButton.getWidth ()
				- footerLabel.getWidth (), 20);
		progressBar.setValue (0);
		progressBar.setLocation (footerLabel.getWidth () + 10, 22);

		// Populate bottom panel.
		btmPanel.add (footerLabel);
		btmPanel.add (progressBar);
		btmPanel.add (doneButton);

		// Populate main panel.
		mainPanel.add (graphPanel);
		mainPanel.add (resultsPanel);
		mainPanel.add (btmPanel);

		return mainPanel;
	}

}
