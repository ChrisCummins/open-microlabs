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

import ac.aston.oml.model.AppDetails;
import ac.aston.oml.view.LogSettingsView;

import jcummins.gui.HTMLFontset;
import jcummins.gui.JComboBoxUtils;

/**
 * This extension of JFrame draws a frame that can be used to set the settings
 * for a logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLLogSettingsView extends JFrame implements LogSettingsView,
		ActionListener
{
	/** Serial UID. */
	private static final long serialVersionUID = -6428374697446518467L;

	private static final int frameWidth = 800;
	private static final int frameHeight = 200;
	private static final int topHeight = 30;
	private static final int btmHeight = 80;
	private static final int midHeight = (frameHeight - topHeight - btmHeight);

	private static final String[] yesNo = { "No", "Yes" };

	private final JPanel midPanel = new JPanel ();
	private JScrollPane scrollPane = new JScrollPane (midPanel);

	private final JCheckBox fileCheckBox = new JCheckBox ("Log to file:");
	private final JButton fileButton = new JButton (new ImageIcon (
			"img/32x32/folder.png", AppDetails.name ()));
	private final JLabel fileLabel = new JLabel ();

	private final JTextArea readDelayText = new JTextArea (1, 8);
	private final JTextArea readCountText = new JTextArea (1, 8);
	private final JButton guiButton = new JButton ("Advanced");
	private final JButton doneButton = new JButton ("Done");

	private JComboBox<Object> slaveBox = new JComboBox<Object> ();
	private JComboBox<Object>[] pinOnBox;
	private JComboBox<Object>[] signalBox;

	private HTMLFontset h;
	private String[] signalTypes;

	public OMLLogSettingsView ()
	{
		this.setTitle (AppDetails.name ());
		this.setResizable (false);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		this.setIconImage (AppDetails.icon ());
		this.setBackground (Color.white);
	}

	public void init (HTMLFontset h, int pinCount, String[] signalTypes)
	{
		this.setSize (frameWidth, frameHeight);
		this.h = h;
		this.signalTypes = signalTypes;
		setContentPane (createContentPane (pinCount, signalTypes));
		setPincount (pinCount);
	}

	public void teardown ()
	{
		this.dispose ();
	}

	public JFrame fetchFrame ()
	{
		return this;
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

		for (int i = 0; i < pinOnBox.length; i++)
			if (a.getSource () == pinOnBox[i])
				signalBox[i].setEnabled (!signalBox[i].isEnabled ());
	}

	public void setSlaveBoxOptions (Object[] o)
	{
		JComboBoxUtils.updateContents (slaveBox, o, 0);
	}

	public void setFilepathLabel (String s)
	{
		fileLabel.setText (h.format ("label", s));
	}

	public String getFilepathText ()
	{
		return h.get ("label").unformat (fileLabel.getText ());
	}

	public int getSlaveBoxSelectedIndex ()
	{
		return slaveBox.getSelectedIndex ();
	}

	public String getReadCountText ()
	{
		return readCountText.getText ();
	}

	public String getReadDelayText ()
	{
		return readDelayText.getText ();
	}

	public Integer[] getSignalTypeOptions ()
	{
		Integer[] ret = new Integer[signalBox.length];

		for (int i = 0; i < ret.length; i++)
			if (pinOnBox[i].getSelectedIndex () == 1)
				ret[i] = signalBox[i].getSelectedIndex ();
			else
				ret[i] = null;
		return ret;
	}

	@Override
	public void addFileButtonListener (ActionListener l)
	{
		fileButton.addActionListener (l);
	}

	@Override
	public void addDoneButtonListener (ActionListener l)
	{
		doneButton.addActionListener (l);
	}

	@Override
	public void addAdvancedButtonListener (ActionListener l)
	{
		guiButton.addActionListener (l);
	}

	public void addSlaveOptionsListener (ActionListener l)
	{
		slaveBox.addActionListener (l);
	}

	/*
	 * Creates and populates the content pane.
	 * 
	 * @return JPanel.
	 */
	private JPanel createContentPane (int pinCount, String[] signalTypes)
	{
		final JPanel mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		mainPanel.setBackground (Color.white);

		final JPanel topPanel = new JPanel ();
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

		fileLabel.setSize (
				frameWidth
						- (fileButton.getLocation ().x
								+ fileButton.getSize ().width + 20), topHeight);
		fileLabel.setBorder (BorderFactory.createLoweredBevelBorder ());
		fileLabel.setLocation (
				fileButton.getLocation ().x + fileButton.getSize ().width + 10,
				0);
		fileLabel.setEnabled (false);
		topPanel.add (fileLabel);

		// Set up mid panel.
		midPanel.setLayout (new GridLayout (3, pinCount + 1));
		midPanel.setBackground (Color.white);

		// Set up scroll pane.
		scrollPane.setSize (frameWidth - 15, midHeight - 10);
		scrollPane.setLocation (5, topHeight + 10);
		scrollPane.setBackground (Color.white);
		scrollPane
				.setHorizontalScrollBarPolicy (ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy (ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

		final JPanel btmPanel = new JPanel ();
		btmPanel.setSize (frameWidth, btmHeight);
		btmPanel.setLocation (0, this.getHeight () - btmHeight);
		btmPanel.setBackground (Color.white);

		btmPanel.add (slaveBox);

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

		mainPanel.add (scrollPane);
		mainPanel.add (btmPanel);

		return mainPanel;
	}

	@SuppressWarnings ("unchecked")
	public void setPincount (int p)
	{
		midPanel.removeAll ();
		midPanel.setLayout (new GridLayout (3, p + 1));
		
		// Set results panel size.
		midPanel.setPreferredSize (new Dimension (40 + (p * 70), scrollPane
				.getHeight () - 100));
		midPanel.revalidate ();

		midPanel.add (new JLabel ("Channel:"));

		pinOnBox = new JComboBox[p];
		signalBox = new JComboBox[p];

		for (int i = 0; i < p; i++)
		{
			final JLabel chanLabel = new JLabel (h.format (
					"label",
					(char) ((i / 7) + 65)
							+ "-0x"
							+ String.format ("%02x", (i % 7) + 1)
									.toUpperCase ()));
			chanLabel.setHorizontalAlignment (JLabel.CENTER);
			midPanel.add (chanLabel);
		}
		midPanel.add (new JLabel ("Active:"));
		for (int i = 0; i < p; i++)
		{
			pinOnBox[i] = JComboBoxUtils.create (yesNo, 1);
			pinOnBox[i].addActionListener (this);
			pinOnBox[i].setBackground (Color.white);
			midPanel.add (pinOnBox[i]);
		}

		midPanel.add (new JLabel ("Type:"));
		for (int i = 0; i < p; i++)
		{
			signalBox[i] = JComboBoxUtils.create (signalTypes, 0);
			signalBox[i].setBackground (Color.white);
			midPanel.add (signalBox[i]);
		}
	}
}
