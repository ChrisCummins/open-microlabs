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

package ac.openmicrolabs.view.gui;

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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.TimeSeriesCollection;

import com.jcummins.html.font.HTMLFontset;

import ac.openmicrolabs.include.AppDetails;
import ac.openmicrolabs.view.LoggerView;

/**
 * This extension of JFrame displays the data read during a logging session.
 * 
 * @author Chris Cummins
 */
public class OMLLoggerView extends JFrame implements LoggerView {
	private static final long serialVersionUID = -4475975253216864808L;
	private static final String GRAPH_X_LABEL = "Time (hh:mm:ss)";
	private static final String GRAPH_Y_LABEL = null;

	private static final int ASCII_OFFSET = 65;

	private static final int PAD5 = 5;
	private static final int PAD8 = 8;
	private static final int PAD10 = 10;
	private static final int PAD15 = 15;
	private static final int PAD20 = 20;
	private static final int PAD22 = 22;
	private static final int PAD30 = 30;
	private static final int PAD40 = 40;
	private static final int PAD120 = 120;
	private static final int PAD200 = 200;

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 600;
	private static final int GRAPH_HEIGHT = FRAME_HEIGHT - PAD200;
	private static final int BTM_HEIGHT = 80;

	private static final int PIN_COUNT = 7;
	private static final int SLAVE_BITS = 1;

	private static final int RESULTS_GRID_ROWS = 6;
	private static final int RESULTS_GRID_COL = 9;
	private static final int RESULTS_COL_WIDTH = 70;

	private static final int LABEL_WIDTH = 140;
	private static final int LABEL_HEIGHT = 30;
	private static final int LABEL_X = 10;
	private static final int LABEL_Y = 18;
	private static final int DONE_WIDTH = 100;
	private static final int DONE_HEIGHT = 30;

	private final JLabel valSideLabel = new JLabel("");
	private final JLabel avgSideLabel = new JLabel("Avg:");

	private JLabel[] chanLabel;
	private JLabel[] typeLabel;
	private JLabel[] valLabel;
	private JLabel[] minLabel;
	private JLabel[] maxLabel;
	private JLabel[] avgLabel;

	private HTMLFontset h;
	private JFreeChart snsChart;

	private final JPanel btmPanel = new JPanel();
	private final JLabel footerLabel = new JLabel("");
	private JProgressBar progressBar;
	private final JButton doneButton = new JButton("Cancel");
	private final JButton reportButton = new JButton("Save");

	private double graphMinY;
	private double graphMaxY;

	/**
	 * Create a new OMLLoggerView frame.
	 */
	public OMLLoggerView() {
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(AppDetails.icon());

	}

	@Override
	public final void init(final HTMLFontset fontset,
			final TimeSeriesCollection t, final String graphTitle,
			final double graphMinYVal, final double graphMaxYVal,
			final double graphTimeRange, final String[] signals) {
		this.h = fontset;
		this.graphMinY = graphMinYVal;
		this.graphMaxY = graphMaxYVal;
		this.setContentPane(createContentPane(t, graphTitle, graphTimeRange,
				signals));
	}

	@Override
	public final void teardown() {
		this.dispose();
	}

	@Override
	public final JFrame fetchFrame() {
		return this;
	}

	@Override
	public final void setViewLoggingCompleted(final double graphTimeRange) {
		final XYPlot plot = snsChart.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(graphTimeRange);
		axis = plot.getRangeAxis();
		axis.setRange(graphMinY, graphMaxY);

		this.setTitle(AppDetails.name());

		valSideLabel.setText(h.format("label-bold", avgSideLabel.getText()));
		avgSideLabel.setText("");
		for (int i = 0; i < avgLabel.length; i++) {
			valLabel[i].setText(h.format("label-bold",
					h.get("body").unformat(avgLabel[i].getText())));
			avgLabel[i].setText("");
		}

		reportButton.setSize(doneButton.getSize());
		reportButton.setIcon(new ImageIcon("img/22x22/save.png"));
		reportButton.setLocation(
				doneButton.getX() - doneButton.getWidth() - PAD10,
				doneButton.getY());
		reportButton.setBackground(Color.white);

		footerLabel.setSize(reportButton.getLocation().x, PAD30);
		footerLabel.setHorizontalAlignment(JLabel.CENTER);

		btmPanel.remove(progressBar);
		btmPanel.add(reportButton);
		btmPanel.repaint();
		doneButton.setText("Done");
		doneButton.setIcon(new ImageIcon("img/22x22/play.png", AppDetails
				.name()));
	}

	@Override
	public final void setValLabels(final String[] s) {
		for (int i = 0; i < s.length; i++) {
			if (s[i] != null) {
				valLabel[i].setText(h.format("label-bold", s[i]));
			} else {
				valLabel[i].setText(h.format("label-red", "!!!"));
			}
		}
	}

	@Override
	public final void setMinLabels(final String[] s) {
		for (int i = 0; i < s.length; i++) {
			minLabel[i].setText(h.format("body", s[i]));
		}
	}

	@Override
	public final void setMaxLabels(final String[] s) {
		for (int i = 0; i < s.length; i++) {
			maxLabel[i].setText(h.format("body", s[i]));
		}
	}

	@Override
	public final void setAvgLabels(final String[] s) {
		for (int i = 0; i < s.length; i++) {
			avgLabel[i].setText(h.format("body", s[i]));
		}
	}

	@Override
	public final void setReadingsLabel(final String s) {
		this.setTitle(s);
	}

	@Override
	public final void setSignalStrenghLabel(final String s) {
		this.footerLabel.setText(h.format("label", s));
	}

	@Override
	public final void setProgressBar(final int current, final int max) {
		this.progressBar.setMaximum(max);
		this.progressBar.setValue(current);
	}

	@Override
	public final void addDoneButtonListener(final ActionListener l) {
		this.doneButton.addActionListener(l);
	}
	
	@Override
	public final void addReportButtonListener(final ActionListener l) {
		this.reportButton.addActionListener(l);
	}

	/*
	 * Create JComponents and populate main panel.
	 */
	private JPanel createContentPane(final TimeSeriesCollection t,
			final String graphTitle, final double graphTimeRange,
			final String[] signals) {
		chanLabel = new JLabel[signals.length];

		int activeSignalCount = 0;
		for (int i = 0; i < signals.length; i++) {
			chanLabel[i] = new JLabel(
					h.format(
							"label",
							(char) ((i / PIN_COUNT) + ASCII_OFFSET)
									+ "-0x"
									+ String.format("%02x",
											(i % PIN_COUNT) + SLAVE_BITS)
											.toUpperCase()));
			chanLabel[i].setHorizontalAlignment(JLabel.CENTER);
			if (signals[i] != null) {
				activeSignalCount++;
			} else {
				chanLabel[i].setEnabled(false);
			}
		}

		typeLabel = new JLabel[activeSignalCount];
		valLabel = new JLabel[activeSignalCount];
		minLabel = new JLabel[activeSignalCount];
		maxLabel = new JLabel[activeSignalCount];
		avgLabel = new JLabel[activeSignalCount];

		int index = 0;
		for (int i = 0; i < signals.length; i++) {
			if (signals[i] != null) {
				typeLabel[index] = new JLabel(h.format("body", signals[i]));
				typeLabel[index].setHorizontalAlignment(JLabel.CENTER);
				index++;
			}
		}

		for (int i = 0; i < activeSignalCount; i++) {
			valLabel[i] = new JLabel();
			valLabel[i].setHorizontalAlignment(JLabel.CENTER);
			minLabel[i] = new JLabel();
			minLabel[i].setHorizontalAlignment(JLabel.CENTER);
			maxLabel[i] = new JLabel();
			maxLabel[i].setHorizontalAlignment(JLabel.CENTER);
			avgLabel[i] = new JLabel();
			avgLabel[i].setHorizontalAlignment(JLabel.CENTER);
		}

		// Set up main panel.
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.white);

		// Create graph.
		snsChart = ChartFactory.createTimeSeriesChart(graphTitle,
				GRAPH_X_LABEL, GRAPH_Y_LABEL, t, true, true, false);
		final XYPlot plot = snsChart.getXYPlot();
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		axis.setFixedAutoRange(graphTimeRange);
		axis = plot.getRangeAxis();
		axis.setRange(graphMinY, graphMaxY);

		ChartPanel snsChartPanel = new ChartPanel(snsChart);
		snsChartPanel.setPreferredSize(new Dimension(FRAME_WIDTH - PAD20,
				GRAPH_HEIGHT - PAD10));

		// Set up graph panel.
		final JPanel graphPanel = new JPanel();
		graphPanel.setSize(FRAME_WIDTH - PAD20, GRAPH_HEIGHT);
		graphPanel.setLocation(0, 0);
		graphPanel.setBackground(Color.white);
		graphPanel.add(snsChartPanel);

		// Set up results panel.
		final JPanel resultsPanel = createResultsPane();

		// Set up scroll pane.
		final JScrollPane scrollPane = new JScrollPane(resultsPanel);
		scrollPane.setSize(FRAME_WIDTH - PAD20, FRAME_HEIGHT - BTM_HEIGHT
				- GRAPH_HEIGHT + PAD8);
		scrollPane.setLocation(PAD5, GRAPH_HEIGHT);
		scrollPane.setBackground(Color.white);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		// Set results panel size.
		resultsPanel.setPreferredSize(new Dimension(PAD40
				+ (signals.length * RESULTS_COL_WIDTH), FRAME_HEIGHT
				- BTM_HEIGHT - GRAPH_HEIGHT - PAD120));
		resultsPanel.revalidate();

		// Set up bottom panel.
		btmPanel.setLayout(null);
		btmPanel.setSize(FRAME_WIDTH, BTM_HEIGHT);
		btmPanel.setLocation(0, this.getHeight() - BTM_HEIGHT);
		btmPanel.setBackground(Color.white);

		resultsPanel.add(new JLabel(""));
		for (JLabel l : chanLabel) {
			resultsPanel.add(l);
		}

		// Instantiate bottom panel objects.
		footerLabel.setSize(LABEL_WIDTH, LABEL_HEIGHT);
		footerLabel.setLocation(LABEL_X, LABEL_Y);
		doneButton.setIcon(new ImageIcon("img/22x22/stop.png"));
		doneButton.setSize(DONE_WIDTH, DONE_HEIGHT);
		doneButton.setLocation(FRAME_WIDTH - PAD20 - doneButton.getWidth(),
				PAD15);
		progressBar = new JProgressBar();
		progressBar.setSize(FRAME_WIDTH - PAD40 - doneButton.getWidth()
				- footerLabel.getWidth(), PAD20);
		progressBar.setValue(0);
		progressBar.setLocation(footerLabel.getWidth() + PAD10, PAD22);

		// Populate bottom panel.
		btmPanel.add(footerLabel);
		btmPanel.add(progressBar);
		btmPanel.add(doneButton);

		// Populate main panel.
		mainPanel.add(graphPanel);
		mainPanel.add(scrollPane);
		mainPanel.add(btmPanel);

		return mainPanel;
	}

	private JPanel createResultsPane() {
		final JPanel resultsPanel = new JPanel();
		resultsPanel.setLayout(new GridLayout(RESULTS_GRID_ROWS,
				RESULTS_GRID_COL));
		resultsPanel.setBackground(Color.white);

		int index = 0;
		resultsPanel.add(new JLabel(""));
		for (JLabel l : chanLabel) {
			if (l.isEnabled()) {
				resultsPanel.add(typeLabel[index]);
				index++;
			} else {
				resultsPanel.add(new JLabel(""));
			}
		}

		resultsPanel.add(valSideLabel);
		index = 0;
		for (JLabel l : chanLabel) {
			if (l.isEnabled()) {
				resultsPanel.add(valLabel[index]);
				index++;
			} else {
				resultsPanel.add(new JLabel(""));
			}
		}

		resultsPanel.add(new JLabel("Min:"));
		index = 0;
		for (JLabel l : chanLabel) {
			if (l.isEnabled()) {
				resultsPanel.add(minLabel[index]);
				index++;
			} else {
				resultsPanel.add(new JLabel(""));
			}
		}

		resultsPanel.add(new JLabel("Max:"));
		index = 0;
		for (JLabel l : chanLabel) {
			if (l.isEnabled()) {
				resultsPanel.add(maxLabel[index]);
				index++;
			} else {
				resultsPanel.add(new JLabel(""));
			}
		}

		resultsPanel.add(avgSideLabel);
		index = 0;
		for (JLabel label : chanLabel) {
			if (label.isEnabled()) {
				resultsPanel.add(avgLabel[index]);
				index++;
			} else {
				resultsPanel.add(new JLabel(""));
			}
		}

		return resultsPanel;
	}

}
