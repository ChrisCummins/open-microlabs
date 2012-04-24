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

import ac.openmicrolabs.include.OMLAppDetails;
import ac.openmicrolabs.view.CommSettingsView;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import com.jcummins.gui.JComboBoxUtils;
import com.jcummins.html.font.HTMLFontset;
import com.jcummins.misc.GetSystemProperties;


/**
 * This extension of JFrame draws a frame that can be used to set the comm
 * settings for serial communications with the microcontroller.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLCommSettingsView extends JFrame implements CommSettingsView {
	/** Serial UID. */
	private static final long serialVersionUID = -7903183494493646804L;

	private static final int FRAME_WIDTH = 320;
	private static final int FRAME_HEIGHT = 370;

	private static final int PAD5 = 5;
	private static final int PAD10 = 10;
	private static final int PAD15 = 15;
	private static final int PAD20 = 20;
	private static final int PAD30 = 30;

	private static final int TOP_GRID_ROWS = 4;
	private static final int TOP_GRID_COL = 2;
	private static final int GRID_VGAP = 10;
	private static final int GRID_HGAP = 5;

	private static final int MID_GRID_ROWS = 6;
	private static final int MID_GRID_COL = 2;

	private static final int TOP_HEIGHT = 90;
	private static final int BTM_HEIGHT = 50;
	private static final int MID_HEIGHT = FRAME_HEIGHT - BTM_HEIGHT
			- TOP_HEIGHT - PAD30;

	private HTMLFontset h;

	private final JLabel comPortCountLabel = new JLabel();
	private JComboBox<Object> comBox = new JComboBox<Object>();
	private JComboBox<Object> baudBox = new JComboBox<Object>();
	private JComboBox<Object> dataBox = new JComboBox<Object>();
	private JComboBox<Object> stopBox = new JComboBox<Object>();
	private JComboBox<Object> parityBox = new JComboBox<Object>();
	private JComboBox<Object> flowBox = new JComboBox<Object>();
	private final JButton comRefreshButton = new JButton();
	private final JButton testButton = new JButton("Test");
	private final JButton doneButton = new JButton("Done");

	/**
	 * Creates a OMLCommSettingsView frame and sets content pane.
	 */
	public OMLCommSettingsView() {
		this.setTitle(OMLAppDetails.name());
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(OMLAppDetails.icon());
		this.setBackground(Color.white);
	}

	@Override
	public final void init(final HTMLFontset fontset) {
		this.h = fontset;
		this.setContentPane(createContentPane());
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
	public final void setComOptions(final Object[] o) {
		JComboBoxUtils.updateContents(comBox, o);
	}

	@Override
	public final void setBaudOptions(final Object[] is, final int index) {
		JComboBoxUtils.updateContents(baudBox, is, index);
	}

	@Override
	public final void setDataOptions(final Object[] o, final int index) {
		JComboBoxUtils.updateContents(dataBox, o, index);
	}

	@Override
	public final void setStopOptions(final Object[] o, final int index) {
		JComboBoxUtils.updateContents(stopBox, o, index);
	}

	@Override
	public final void setParityOptions(final Object[] o, final int index) {
		JComboBoxUtils.updateContents(parityBox, o, index);
	}

	@Override
	public final void setFlowOptions(final Object[] o, final int index) {
		JComboBoxUtils.updateContents(flowBox, o, index);
	}

	@Override
	public final void addRefreshButtonListener(final ActionListener l) {
		comRefreshButton.addActionListener(l);
	}

	@Override
	public final void addDoneButtonListener(final ActionListener l) {
		doneButton.addActionListener(l);
	}

	@Override
	public final void addTestButtonListener(final ActionListener l) {
		testButton.addActionListener(l);
	}

	/*
	 * Draws the main content pane for the OMLCommSettingsView frame.
	 */
	private JPanel createContentPane() {
		// PANEL OBJECTS -------------------------------------------------------
		TitledBorder borderTop = BorderFactory
				.createTitledBorder("System Settings");
		borderTop.setTitleJustification(TitledBorder.CENTER);

		TitledBorder borderMid = BorderFactory
				.createTitledBorder("Microcontroller Settings");
		borderMid.setTitleJustification(TitledBorder.CENTER);

		// MAIN ----------------------------------------------------------------
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setLocation(0, 0);
		mainPanel.setSize(FRAME_WIDTH - PAD20, FRAME_HEIGHT - PAD20);
		mainPanel.setBackground(Color.white);

		// TOP -----------------------------------------------------------------
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(TOP_GRID_ROWS, TOP_GRID_COL,
				GRID_VGAP, GRID_HGAP));
		topPanel.setLocation(PAD5, PAD5);
		topPanel.setSize(FRAME_WIDTH - PAD15, TOP_HEIGHT);
		topPanel.setBorder(borderTop);
		topPanel.setBackground(Color.white);
		mainPanel.add(topPanel);

		topPanel.add(new JLabel(h.format("label", "Operating System:")));
		topPanel.add(new JLabel(h.format("label", GetSystemProperties.os()),
				new ImageIcon("img/12x12/Desktop 2.png", OMLAppDetails.name()),
				JLabel.LEFT));
		topPanel.add(new JLabel(h.format("label", "OS Version:")));
		topPanel.add(new JLabel(h.format("label",
				GetSystemProperties.osVersion()), new ImageIcon(
				"img/12x12/advanced.png", OMLAppDetails.name()), JLabel.LEFT));
		topPanel.add(new JLabel(h.format("label", "System Architecture:")));
		topPanel.add(new JLabel(
				h.format("label", GetSystemProperties.osArch()), new ImageIcon(
						"img/12x12/64 bit.png", OMLAppDetails.name()), JLabel.LEFT));
		topPanel.add(new JLabel(h.format("label", "Com Ports:")));
		topPanel.add(comPortCountLabel);

		// MID -----------------------------------------------------------------
		JPanel midPanel = new JPanel();
		midPanel.setLayout(new GridLayout(MID_GRID_ROWS, MID_GRID_COL, PAD20,
				PAD5));
		midPanel.setLocation(PAD5, TOP_HEIGHT + PAD5);
		midPanel.setSize(FRAME_WIDTH - PAD15, MID_HEIGHT);
		midPanel.setBorder(borderMid);
		midPanel.setBackground(Color.white);
		mainPanel.add(midPanel);

		JLabel midComLabel = new JLabel(h.format("label", "Com Port:"));
		midComLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midComLabel);

		JPanel midComPanel = new JPanel();
		midComPanel.setLayout(new BorderLayout());
		midComPanel.setBackground(Color.white);

		comBox.setBackground(Color.white);
		midComPanel.add(comBox, BorderLayout.WEST);
		comRefreshButton.setIcon(new ImageIcon("img/12x12/refresh.png", this
				.getTitle()));
		comRefreshButton.setBackground(Color.white);
		midComPanel.add(comRefreshButton, BorderLayout.EAST);

		midPanel.add(midComPanel);

		JLabel midBaudLabel = new JLabel(h.format("label", "Baud Rate:"));
		midBaudLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midBaudLabel);

		baudBox.setBackground(Color.white);
		midPanel.add(baudBox);

		JLabel midDataLabel = new JLabel(h.format("label", "Data Bits:"));
		midDataLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midDataLabel);

		dataBox.setBackground(Color.white);
		midPanel.add(dataBox);

		JLabel midStopLabel = new JLabel(h.format("label", "Stop Bits:"));
		midStopLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midStopLabel);

		stopBox.setBackground(Color.white);
		midPanel.add(stopBox);

		JLabel midParityLabel = new JLabel(h.format("label", "Parity:"));
		midParityLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midParityLabel);

		parityBox.setBackground(Color.white);
		midPanel.add(parityBox);

		JLabel midFlowLabel = new JLabel(h.format("label", "Flow Control"));
		midFlowLabel.setHorizontalAlignment(JLabel.LEFT);
		midPanel.add(midFlowLabel);

		flowBox.setBackground(Color.white);
		midPanel.add(flowBox);

		// BOTTOM --------------------------------------------------------------
		JPanel btmPanel = new JPanel();
		btmPanel.setLayout(null);
		btmPanel.setLocation(PAD5, midPanel.getLocation().y + MID_HEIGHT);
		btmPanel.setSize(FRAME_WIDTH - PAD15, BTM_HEIGHT - PAD10);
		btmPanel.setBackground(Color.white);
		mainPanel.add(btmPanel);

		testButton.setToolTipText("Test microcontroller with these settings");
		testButton.setIcon(new ImageIcon("img/22x22/plus.png", OMLAppDetails
				.name()));
		testButton.setLocation(PAD5, PAD5);
		testButton.setSize((btmPanel.getSize().width / 2) - PAD10,
				btmPanel.getSize().height - PAD10);
		testButton.setBackground(Color.white);
		btmPanel.add(testButton);

		doneButton.setToolTipText("Continue with these settings.");
		doneButton.setIcon(new ImageIcon("img/22x22/play.png", OMLAppDetails
				.name()));
		doneButton.setLocation(
				(testButton.getLocation().x + testButton.getWidth()) + PAD10,
				PAD5);
		doneButton.setSize((btmPanel.getSize().width / 2) - PAD10,
				btmPanel.getSize().height - PAD10);
		doneButton.setBackground(Color.white);
		btmPanel.add(doneButton);

		return mainPanel;
	}

	@Override
	public final int getSelectedComOption() {
		return comBox.getSelectedIndex();
	}

	@Override
	public final int getSelectedBaudOption() {
		return baudBox.getSelectedIndex();
	}

	@Override
	public final int getSelectedDataOption() {
		return dataBox.getSelectedIndex();
	}

	@Override
	public final int getSelectedStopOption() {
		return stopBox.getSelectedIndex();
	}

	@Override
	public final int getSelectedParityOption() {
		return parityBox.getSelectedIndex();
	}

	@Override
	public final int getSelectedFlowOption() {
		return flowBox.getSelectedIndex();
	}

}
