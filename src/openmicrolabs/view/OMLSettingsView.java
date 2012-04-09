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
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import cummins.gui.CreateJComboBox;
import cummins.gui.GUITools;

import openmicrolabs.AppDetails;
import openmicrolabs.settings.Datamask;
import openmicrolabs.settings.LogSettings;
import openmicrolabs.signals.OMLSignal;

/**
 * This extension of JFrame draws a frame that can be used to set the settings
 * for a logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLSettingsView extends JFrame implements ActionListener
{
	/** Serial UID. */
	private static final long serialVersionUID = -6428374697446518467L;

	private static final int frameWidth = 600;
	private static final int frameHeight = 200;
	private static final int topHeight = 30;
	private static final int btmHeight = 80;
	private static final int midHeight = (frameHeight - topHeight - btmHeight);

	private static final String[] yesNo = { "No", "Yes" };

	private final JCheckBox fileCheckBox = new JCheckBox ("Log to file:");
	private final JButton fileButton = new JButton (new ImageIcon (
			"img/32x32/folder.png", AppDetails.name ()));
	private final JLabel fileLabel = new JLabel (
			System.getProperty ("user.dir") + File.separator + "log.dat");
	@SuppressWarnings ("unchecked")
	private final JComboBox<Object>[] pinOnBox = new JComboBox[8];
	@SuppressWarnings ("unchecked")
	private final JComboBox<Object>[] signalBox = new JComboBox[8];
	private final Object[] signalTypeNames = new Object[OMLView.SIGNAL_TYPES.length];
	private final JTextArea readDelayText = new JTextArea (1, 8);
	private final JTextArea readCountText = new JTextArea (1, 8);
	private final JButton guiButton = new JButton ("Advanced");
	private final JButton doneButton = new JButton ("Done");

	public OMLSettingsView ()
	{
		for (int i = 0; i < OMLView.SIGNAL_TYPES.length; i++)
		{
			signalTypeNames[i] = OMLView.SIGNAL_TYPES[i].name ();
		}

		this.setTitle (AppDetails.name ());
		this.setSize (frameWidth, frameHeight);
		this.setResizable (false);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		GUITools.centreFrame (this);
		this.setIconImage (AppDetails.icon ());
		this.setBackground (Color.white);
		this.setContentPane (this.createContentPane ());
	}

	/**
	 * Interprets actions performed on the file check box or pinOnBoxes.
	 */
	@Override
	public void actionPerformed (ActionEvent a)
	{
		if (a.getSource () == fileCheckBox)
			if (!fileCheckBox.isSelected ())
			{
				fileButton.setEnabled (false);
				fileLabel.setEnabled (false);
			} else
			{
				fileButton.setEnabled (true);
				fileLabel.setEnabled (true);
			}

		for (int i = 0; i < 8; i++)
			if (a.getSource () == pinOnBox[i])
				if (pinOnBox[i].getSelectedIndex () == 0)
					signalBox[i].setEnabled (false);
				else
					signalBox[i].setEnabled (true);
	}

	/**
	 * Adds an action listener to the "Done" button.
	 * 
	 * @param l
	 *            ActionListener
	 */
	public void addDoneButtonListener (ActionListener l)
	{
		doneButton.addActionListener (l);
	}

	/**
	 * Adds an action listener to the "Advanced" button.
	 * 
	 * @param l
	 *            ActionListener
	 */
	public void addGUIButtonListener (ActionListener l)
	{
		guiButton.addActionListener (l);
	}

	/**
	 * Gets and returns the selected Log Settings.
	 * 
	 * @return LogSettings.
	 * @throws NumberFormatException
	 *             If text areas contained non-numeric characters.
	 */
	public LogSettings getLogSettings () throws NumberFormatException
	{
		// Set read count.
		Integer readCount;
		if (readCountText.getText ().replaceAll ("[^\\d]", "")
				.replaceAll ("\\s+", "").length () == 0)
			readCount = null;
		else
			readCount = Integer.parseInt (readCountText.getText ()
					.replaceAll ("[^\\d]", "").replaceAll ("\\s+", ""));

		// Set read delay.
		long readDelay;
		readDelay = Long.parseLong (readDelayText.getText ()
				.replaceAll ("[^\\d]", "").replaceAll ("\\s+", ""));

		// Create datamask.
		OMLSignal[] s = new OMLSignal[8];
		for (int i = 0; i < 8; i++)
		{
			if (signalBox[i].isEnabled ())
				s[i] = OMLView.SIGNAL_TYPES[signalBox[i].getSelectedIndex ()];
			else
				s[i] = null;
		}
		Datamask datamask = new Datamask (s);

		// Set logpath.
		String logpath = null;
		if (fileCheckBox.isSelected ())
			logpath = fileLabel.getText ();

		return new LogSettings (datamask, readDelay, readCount, logpath);
	}

	/*
	 * Creates and populates the content pane.
	 * 
	 * @return JPanel.
	 */
	private JPanel createContentPane ()
	{
		JPanel mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		mainPanel.setBackground (Color.white);

		JPanel topPanel = new JPanel ();
		topPanel.setLayout (null);
		topPanel.setSize (frameWidth, topHeight);
		topPanel.setLocation (0, 0);
		topPanel.setBackground (Color.white);
		mainPanel.add (topPanel);

		fileCheckBox.setSize (80, topHeight);
		fileCheckBox.setLocation (0, 0);
		fileCheckBox.setBackground (Color.white);
		fileCheckBox.addActionListener (this);
		topPanel.add (fileCheckBox);

		fileButton.setSize (60, topHeight);
		fileButton.setLocation (85, 0);
		fileButton.setEnabled (false);
		fileButton.setBackground (Color.white);
		topPanel.add (fileButton);

		fileLabel
				.setSize (frameWidth
						- (fileButton.getLocation ().x
								+ fileButton.getSize ().width + 20), topHeight);
		fileLabel.setBorder (BorderFactory.createLoweredBevelBorder ());
		fileLabel.setLocation (
				fileButton.getLocation ().x + fileButton.getSize ().width + 10,
				0);
		fileLabel.setEnabled (false);
		topPanel.add (fileLabel);

		JPanel midPanel = new JPanel ();
		midPanel.setLayout (new GridLayout (3, 9));
		midPanel.setSize (frameWidth - 15, midHeight - 10);
		midPanel.setLocation (5, topHeight + 10);
		midPanel.setBackground (Color.white);
		mainPanel.add (midPanel);

		midPanel.add (new JLabel ("Channel:"));
		for (int i = 0; i < 8; i++)
		{
			JLabel chanLabel = new JLabel (OMLView.LABEL_START + "0x0" + i
					+ OMLView.LABEL_END);
			chanLabel.setHorizontalAlignment (JLabel.CENTER);
			midPanel.add (chanLabel);
		}
		midPanel.add (new JLabel ("Active:"));
		for (int i = 0; i < 8; i++)
		{
			pinOnBox[i] = CreateJComboBox.fromObjects (yesNo, 1);
			pinOnBox[i].addActionListener (this);
			pinOnBox[i].setBackground (Color.white);
			midPanel.add (pinOnBox[i]);
		}

		midPanel.add (new JLabel ("Type:"));
		for (int i = 0; i < 8; i++)
		{
			signalBox[i] = CreateJComboBox.fromObjects (signalTypeNames, 0);
			signalBox[i].setBackground (Color.white);
			midPanel.add (signalBox[i]);
		}
		JPanel btmPanel = new JPanel ();
		btmPanel.setSize (frameWidth, btmHeight);
		btmPanel.setLocation (0, this.getHeight () - btmHeight);
		btmPanel.setBackground (Color.white);

		mainPanel.add (btmPanel);
		btmPanel.add (new JLabel ("Delay between reads (ms):"));

		readDelayText.setText ("1000");
		readDelayText.setBorder (BorderFactory.createLineBorder (Color.black));
		readDelayText.setBackground (Color.white);
		btmPanel.add (readDelayText);
		btmPanel.add (new JLabel ("Number of readings:"));
		readCountText.setText ("10");
		readCountText.setBorder (BorderFactory.createLineBorder (Color.black));
		btmPanel.add (readCountText);
		guiButton.setToolTipText ("Set advanced display options");
		guiButton.setIcon (new ImageIcon ("img/22x22/advanced.png", AppDetails
				.name ()));
		guiButton.setBackground (Color.white);
		btmPanel.add (guiButton);
		doneButton.setToolTipText ("Continue with these settings");
		doneButton.setIcon (new ImageIcon ("img/22x22/play.png", AppDetails
				.name ()));
		doneButton.setBackground (Color.white);
		btmPanel.add (doneButton);

		return mainPanel;
	}

}
