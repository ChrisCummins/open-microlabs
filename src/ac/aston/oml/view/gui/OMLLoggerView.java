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
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import jcummins.gui.HTMLFontset;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

import ac.aston.oml.model.data.AppDetails;
import ac.aston.oml.view.LoggerView;

/**
 * This extension of JFrame displays the data read during a logging session.
 * 
 * @author Chris Cummins
 */
public class OMLLoggerView extends JFrame implements LoggerView
{
	private static final long serialVersionUID = -4475975253216864808L;
	private static final String graphXlabel = "Time (hh:mm:ss)";
	private static final String graphYlabel = null;

	// GUI CONFIG --------------------------------------------------------------
	private static final int frameWidth = 800;
	private static final int frameHeight = 600;
	private static final int graphHeight = frameHeight - 200;
	private static final int btmHeight = 80;

	private JLabel[] chanLabel;
	private JLabel[] typeLabel;
	private JLabel[] valLabel;
	private JLabel[] minLabel;
	private JLabel[] maxLabel;
	private JLabel[] avgLabel;

	private HTMLFontset h;
	private JFreeChart snsChart;

	private final JPanel btmPanel = new JPanel ();
	private final JLabel footerLabel = new JLabel ("");
	private JProgressBar progressBar;
	private final JButton doneButton = new JButton ("Cancel");

	private double graphMinY;
	private double graphMaxY;

	public OMLLoggerView ()
	{
		this.setSize (frameWidth, frameHeight);
		this.setResizable (false);
		this.setBackground (Color.white);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setIconImage (AppDetails.icon ());

	}

	@Override
	public void init (HTMLFontset h, TimeSeriesCollection t, String graphTitle,
			double graphMinY, double graphMaxY, double graphTimeRange,
			String[] signals)
	{
		this.h = h;
		this.graphMinY = graphMinY;
		this.graphMaxY = graphMaxY;
		this.setContentPane (createContentPane (t, graphTitle, graphTimeRange,
				signals));
	}

	@Override
	public void teardown ()
	{
		this.dispose ();
	}

	@Override
	public void setValLabels (String[] s)
	{
		for (int i = 0; i < s.length; i++)
			valLabel[i].setText (h.format ("label-bold", s[i]));
	}

	@Override
	public void setMinLabels (String[] s)
	{
		for (int i = 0; i < s.length; i++)
			minLabel[i].setText (h.format ("body", s[i]));
	}

	@Override
	public void setMaxLabels (String[] s)
	{
		for (int i = 0; i < s.length; i++)
			maxLabel[i].setText (h.format ("body", s[i]));
	}

	@Override
	public void setAvgLabels (String[] s)
	{
		for (int i = 0; i < s.length; i++)
			avgLabel[i].setText (h.format ("body", s[i]));
	}

	@Override
	public void setReadingsLabel (String s)
	{
		this.setTitle (s);
	}

	@Override
	public void setSignalStrenghLabel (String s)
	{
		this.footerLabel.setText (h.format ("label", s));
	}

	@Override
	public void setProgressBar (int progress)
	{
		this.progressBar.setValue (progress);
	}

	@Override
	public void addDoneButtonListener (ActionListener l)
	{
		this.doneButton.addActionListener (l);
	}

	private JPanel createContentPane (TimeSeriesCollection t,
			String graphTitle, double graphTimeRange, String[] signals)
	{
		chanLabel = new JLabel[signals.length];

		int activeSignalCount = 0;
		for (int i = 0; i < signals.length; i++)
		{
			chanLabel[i] = new JLabel (h.format ("label", (char) ((i / 7) + 65)
					+ "-0x"
					+ String.format ("%02x", (i % 7) + 1).toUpperCase ()));
			chanLabel[i].setHorizontalAlignment (JLabel.CENTER);
			if (signals[i] != null)
				activeSignalCount++;
			else
				chanLabel[i].setEnabled (false);
		}

		typeLabel = new JLabel[activeSignalCount];
		valLabel = new JLabel[activeSignalCount];
		minLabel = new JLabel[activeSignalCount];
		maxLabel = new JLabel[activeSignalCount];
		avgLabel = new JLabel[activeSignalCount];

		int index = 0;
		for (int i = 0; i < signals.length; i++)
			if (signals[i] != null)
			{
				typeLabel[index] = new JLabel (h.format ("body", signals[i]));
				typeLabel[index].setHorizontalAlignment (JLabel.CENTER);
				index++;
			}

		for (int i = 0; i < activeSignalCount; i++)
		{
			valLabel[i] = new JLabel ();
			valLabel[i].setHorizontalAlignment (JLabel.CENTER);
			minLabel[i] = new JLabel ();
			minLabel[i].setHorizontalAlignment (JLabel.CENTER);
			maxLabel[i] = new JLabel ();
			maxLabel[i].setHorizontalAlignment (JLabel.CENTER);
			avgLabel[i] = new JLabel ();
			avgLabel[i].setHorizontalAlignment (JLabel.CENTER);
		}

		// Set up main panel.
		JPanel mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		mainPanel.setBackground (Color.white);

		// Create graph.
		snsChart = ChartFactory.createTimeSeriesChart (graphTitle, graphXlabel,
				graphYlabel, t, true, true, false);
		final XYPlot plot = snsChart.getXYPlot ();
		ValueAxis axis = plot.getDomainAxis ();
		axis.setAutoRange (true);
		axis.setFixedAutoRange (graphTimeRange);
		axis = plot.getRangeAxis ();
		axis.setRange (graphMinY, graphMaxY);

		ChartPanel snsChartPanel = new ChartPanel (snsChart);
		snsChartPanel.setPreferredSize (new Dimension (frameWidth - 20,
				graphHeight - 10));

		// Set up graph panel.
		final JPanel graphPanel = new JPanel ();
		graphPanel.setSize (frameWidth - 20, graphHeight);
		graphPanel.setLocation (0, 0);
		graphPanel.setBackground (Color.white);
		graphPanel.add (snsChartPanel);

		// Set up results panel.
		final JPanel resultsPanel = new JPanel ();
		resultsPanel.setLayout (new GridLayout (6, 9));
		resultsPanel.setBackground (Color.white);

		// Set up scroll pane.
		final JScrollPane scrollPane = new JScrollPane (resultsPanel);
		scrollPane.setSize (frameWidth - 20, frameHeight - btmHeight
				- graphHeight + 8);
		scrollPane.setLocation (5, graphHeight);
		scrollPane.setBackground (Color.white);
		scrollPane
				.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		// Set results panel size.
		resultsPanel.setPreferredSize (new Dimension (
				40 + (signals.length * 70), frameHeight - btmHeight
						- graphHeight - 120));
		resultsPanel.revalidate ();

		// Set up bottom panel.
		btmPanel.setLayout (null);
		btmPanel.setSize (frameWidth, btmHeight);
		btmPanel.setLocation (0, this.getHeight () - btmHeight);
		btmPanel.setBackground (Color.white);

		resultsPanel.add (new JLabel (""));
		for (JLabel l : chanLabel)
			resultsPanel.add (l);

		index = 0;
		resultsPanel.add (new JLabel (""));
		for (JLabel l : chanLabel)
			if (l.isEnabled ())
			{
				resultsPanel.add (typeLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));

		resultsPanel.add (new JLabel (""));
		index = 0;
		for (JLabel l : chanLabel)
			if (l.isEnabled ())
			{
				resultsPanel.add (valLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));

		resultsPanel.add (new JLabel ("Min:"));
		index = 0;
		for (JLabel l : chanLabel)
			if (l.isEnabled ())
			{
				resultsPanel.add (minLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));

		resultsPanel.add (new JLabel ("Max:"));
		index = 0;
		for (JLabel l : chanLabel)
			if (l.isEnabled ())
			{
				resultsPanel.add (maxLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));

		resultsPanel.add (new JLabel ("Avg:"));
		index = 0;
		for (JLabel l : chanLabel)
			if (l.isEnabled ())
			{
				resultsPanel.add (avgLabel[index]);
				index++;
			} else
				resultsPanel.add (new JLabel (""));

		// Instantiate bottom panel objects.
		footerLabel.setSize (140, 30);
		footerLabel.setLocation (10, 18);
		doneButton.setIcon (new ImageIcon ("img/22x22/stop.png"));
		doneButton.setSize (100, 30);
		doneButton.setLocation (frameWidth - 20 - doneButton.getWidth (), 15);
		progressBar = new JProgressBar ();
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
		mainPanel.add (scrollPane);
		mainPanel.add (btmPanel);

		return mainPanel;
	}
	/*
	 * private void finishReading (double graphTimeRange) { // Set graph to full
	 * view of readings. final XYPlot plot = snsChart.getXYPlot (); ValueAxis
	 * axis = plot.getDomainAxis (); axis.setAutoRange (true);
	 * axis.setFixedAutoRange (graphTimeRange); axis = plot.getRangeAxis ();
	 * axis.setRange (g.minY (), g.maxY ());
	 * 
	 * this.setTitle (AppDetails.name ());
	 * 
	 * footerLabel.setSize (doneButton.getLocation ().x, 30);
	 * footerLabel.setText (OMLView.LABEL_START + "<b>" + dataset.getItemCount
	 * (0) + " readings complete." + "</b>" + OMLView.LABEL_END);
	 * footerLabel.setHorizontalAlignment (JLabel.CENTER);
	 * 
	 * btmPanel.remove (progressBar); btmPanel.repaint (); doneButton.setText
	 * ("Done"); doneButton.setIcon (new ImageIcon ("img/22x22/play.png",
	 * AppDetails .name ())); }
	 */
}
