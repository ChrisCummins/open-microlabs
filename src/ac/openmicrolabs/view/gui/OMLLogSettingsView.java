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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import com.jcummins.gui.JComboBoxUtils;
import com.jcummins.html.font.HTMLFontset;

import ac.openmicrolabs.include.OMLAppDetails;
import ac.openmicrolabs.view.LogSettingsView;

/**
 * This extension of JFrame draws a frame that can be used to set the settings
 * for a logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLLogSettingsView extends JFrame implements LogSettingsView,
		ActionListener {

	private static final long serialVersionUID = -6428374697446518467L;

	private static final int PAD5 = 5;
	private static final int PAD10 = 10;
	private static final int PAD15 = 15;
	private static final int PAD20 = 20;
	private static final int PAD40 = 40;
	private static final int PAD65 = 65;
	private static final int PAD100 = 100;

	private static final int FRAME_WIDTH = 800;
	private static final int FRAME_HEIGHT = 200;
	private static final int TOP_HEIGHT = 30;
	private static final int BTM_HEIGHT = 80;
	private static final int MID_HEIGHT = FRAME_HEIGHT - TOP_HEIGHT
			- BTM_HEIGHT;

	private static final int FILE_CHECK_BOX_WIDTH = 80;
	private static final int FILE_BUTTON_WIDTH = 60;
	private static final int FILE_BUTTON_X = 85;

	private static final int MID_GRID_ROW = 3;
	private static final int MID_GRID_WIDTH = 70;

	private static final int PINCOUNT = 7;

	private static final String[] YES_NO = { "No", "Yes" };

	private final JPanel midPanel = new JPanel();
	private JScrollPane scrollPane = new JScrollPane(midPanel);

	private final JCheckBox fileCheckBox = new JCheckBox("Log to file:");
	private final JButton fileButton = new JButton(new ImageIcon(
			"img/32x32/folder.png", OMLAppDetails.name()));
	private final JLabel fileLabel = new JLabel();

	private final JTextArea readDelayText = new JTextArea(1, 8);
	private final JTextArea readCountText = new JTextArea(1, 8);
	private final JButton guiButton = new JButton("Advanced");
	private final JButton doneButton = new JButton("Done");

	private JComboBox<Object> slaveBox = new JComboBox<Object>();
	private JComboBox<Object>[] pinOnBox;
	private JComboBox<Object>[] signalBox;

	private HTMLFontset h;
	private String[] signalTypes;

	/**
	 * Constructs a new OMLLogSettingsView frame.
	 */
	public OMLLogSettingsView() {
		this.setTitle(OMLAppDetails.name());
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(OMLAppDetails.icon());
		this.setBackground(Color.white);
	}

	@Override
	public final void init(final HTMLFontset fontset, final int pinCount,
			final String[] signalTypesNames) {
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.h = fontset;
		this.signalTypes = signalTypesNames;
		setContentPane(createContentPane(pinCount, signalTypesNames));
		setPincount(pinCount);
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
	public final void actionPerformed(final ActionEvent a) {
		// File button context behaviour.
		if (a.getSource() == fileCheckBox) {
			if (!fileCheckBox.isSelected()) {
				fileButton.setEnabled(false);
				fileLabel.setEnabled(false);
			} else {
				fileButton.setEnabled(true);
				fileLabel.setEnabled(true);
			}
		}

		// Pin type box context behaviour.
		for (int i = 0; i < pinOnBox.length; i++) {
			if (a.getSource() == pinOnBox[i]) {
				if (pinOnBox[i].getSelectedIndex() == 0) {
					signalBox[i].setEnabled(false);
				} else {
					signalBox[i].setEnabled(true);
				}
			}
		}
	}

	@Override
	public final void setSlaveBoxOptions(final Object[] o) {
		JComboBoxUtils.updateContents(slaveBox, o, 0);
	}

	@Override
	public final void setFilepathLabel(final String s) {
		fileLabel.setText(h.format("label", s));
	}

	@Override
	public final boolean getLogToFile() {
		return fileCheckBox.isSelected();
	}

	@Override
	public final String getFilepathText() {
		return h.get("label").unformat(fileLabel.getText());
	}

	@Override
	public final int getSlaveBoxSelectedIndex() {
		return slaveBox.getSelectedIndex();
	}

	@Override
	public final String getReadCountText() {
		return readCountText.getText();
	}

	@Override
	public final String getReadDelayText() {
		return readDelayText.getText();
	}

	@Override
	public final Integer[] getSignalTypeOptions() {
		Integer[] ret = new Integer[signalBox.length];

		for (int i = 0; i < ret.length; i++) {
			if (pinOnBox[i].getSelectedIndex() == 1) {
				ret[i] = signalBox[i].getSelectedIndex();
			} else {
				ret[i] = null;
			}
		}

		return ret;
	}

	@Override
	public final void addFileButtonListener(final ActionListener l) {
		fileButton.addActionListener(l);
	}

	@Override
	public final void addDoneButtonListener(final ActionListener l) {
		doneButton.addActionListener(l);
	}

	@Override
	public final void addAdvancedButtonListener(final ActionListener l) {
		guiButton.addActionListener(l);
	}

	@Override
	public final void addSlaveOptionsListener(final ActionListener l) {
		slaveBox.addActionListener(l);
	}

	@Override
	@SuppressWarnings("unchecked")
	public final void setPincount(final int p) {
		midPanel.removeAll();
		midPanel.setLayout(new GridLayout(MID_GRID_ROW, p + 1));

		// Set results panel size.
		midPanel.setPreferredSize(new Dimension(PAD40 + (p * MID_GRID_WIDTH),
				scrollPane.getHeight() - PAD100));
		midPanel.revalidate();

		midPanel.add(new JLabel("Channel:"));

		pinOnBox = new JComboBox[p];
		signalBox = new JComboBox[p];

		final String[] chans = new String[p];

		for (int i = 0; i < p; i++) {
			chans[i] = (char) ((i / PINCOUNT) + PAD65) + "-0x"
					+ String.format("%02x", (i % PINCOUNT) + 1).toUpperCase();

			final JLabel chanLabel = new JLabel(h.format("label", chans[i]));
			chanLabel.setHorizontalAlignment(JLabel.CENTER);
			midPanel.add(chanLabel);
		}
		midPanel.add(new JLabel("Active:"));
		for (int i = 0; i < p; i++) {
			pinOnBox[i] = JComboBoxUtils.create(YES_NO, 1);
			pinOnBox[i].addActionListener(this);
			pinOnBox[i].setToolTipText("Select whether to take readings on"
					+ " the respective pin (" + chans[i] + ")");
			pinOnBox[i].setBackground(Color.white);
			midPanel.add(pinOnBox[i]);
		}

		midPanel.add(new JLabel("Type:"));
		for (int i = 0; i < p; i++) {
			signalBox[i] = JComboBoxUtils.create(signalTypes, 0);
			signalBox[i].setToolTipText("Select the type of sensor attached to"
					+ " the respective pin (" + chans[i] + ")");
			signalBox[i].setBackground(Color.white);
			midPanel.add(signalBox[i]);
		}
	}

	/*
	 * Creates and populates the content pane.
	 */
	private JPanel createContentPane(final int pinCount,
			final String[] signalTypeNames) {
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		mainPanel.setBackground(Color.white);

		final JPanel topPanel = new JPanel();
		topPanel.setLayout(null);
		topPanel.setSize(FRAME_WIDTH, TOP_HEIGHT);
		topPanel.setLocation(0, 0);
		topPanel.setBackground(Color.white);
		mainPanel.add(topPanel);

		fileCheckBox.setSize(FILE_CHECK_BOX_WIDTH, TOP_HEIGHT);
		fileCheckBox.setToolTipText("Set logging to file on or off");
		fileCheckBox.setLocation(0, 0);
		fileCheckBox.setBackground(Color.white);
		fileCheckBox.addActionListener(this);
		topPanel.add(fileCheckBox);

		fileButton.setToolTipText("Select location for log file");
		fileButton.setSize(FILE_BUTTON_WIDTH, TOP_HEIGHT);
		fileButton.setLocation(FILE_BUTTON_X, 0);
		fileButton.setEnabled(false);
		fileButton.setBackground(Color.white);
		topPanel.add(fileButton);

		fileLabel.setSize(
				FRAME_WIDTH
						- (fileButton.getLocation().x
								+ fileButton.getSize().width + PAD20),
				TOP_HEIGHT);
		fileLabel.setBorder(BorderFactory.createLoweredBevelBorder());
		fileLabel.setToolTipText("Log file location");
		fileLabel.setLocation(fileButton.getLocation().x
				+ fileButton.getSize().width + PAD10, 0);
		fileLabel.setEnabled(false);
		topPanel.add(fileLabel);

		// Set up mid panel.
		midPanel.setLayout(new GridLayout(MID_GRID_ROW, pinCount + 1));
		midPanel.setBackground(Color.white);

		// Set up scroll pane.
		scrollPane.setSize(FRAME_WIDTH - PAD15, MID_HEIGHT - PAD10);
		scrollPane.setLocation(PAD5, TOP_HEIGHT + PAD10);
		scrollPane.setBackground(Color.white);
		scrollPane
				.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		final JPanel btmPanel = new JPanel();
		btmPanel.setSize(FRAME_WIDTH, BTM_HEIGHT);
		btmPanel.setLocation(0, this.getHeight() - BTM_HEIGHT);
		btmPanel.setBackground(Color.white);

		slaveBox.setToolTipText("Set the number of slave microcontrollers in"
				+ " use");
		btmPanel.add(slaveBox);

		btmPanel.add(new JLabel("Delay between reads (s):"));

		readDelayText.setText("1");
		readDelayText.setToolTipText("Set the amount of time between "
				+ "individual readings being made");
		readDelayText.setBorder(BorderFactory.createLineBorder(Color.black));
		readDelayText.setBackground(Color.white);
		btmPanel.add(readDelayText);
		btmPanel.add(new JLabel("Number of readings:"));
		readCountText.setText("10");
		readCountText.setToolTipText("Set the number of individual readings"
				+ " to take");
		readCountText.setBorder(BorderFactory.createLineBorder(Color.black));
		btmPanel.add(readCountText);

		guiButton.setToolTipText("Show advanced options");
		guiButton.setIcon(new ImageIcon("img/22x22/advanced.png", OMLAppDetails
				.name()));
		guiButton.setBackground(Color.white);
		btmPanel.add(guiButton);

		doneButton.setToolTipText("Start logging with these settings");
		doneButton.setIcon(new ImageIcon("img/22x22/play.png", OMLAppDetails
				.name()));
		doneButton.setBackground(Color.white);
		btmPanel.add(doneButton);

		mainPanel.add(scrollPane);
		mainPanel.add(btmPanel);

		return mainPanel;

	}

}
