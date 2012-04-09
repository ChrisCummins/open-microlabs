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

import cummins.gui.CreateJComboBox;
import cummins.gui.GUITools;
import cummins.misc.GetSystemProperties;
import cummins.serial.DiscoverPorts;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import gnu.io.SerialPort;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import openmicrolabs.AppDetails;
import openmicrolabs.settings.CommSettings;

/**
 * This extension of JFrame draws a frame that can be used to set the comm
 * settings for serial communications with the microcontroller.
 * 
 * @author Chris Cummins
 * 
 */
public class CommSettingsView extends JFrame
{
	/** Serial UID. */
	private static final long serialVersionUID = -7903183494493646804L;

	private static final int frameWidth = 320;
	private static final int frameHeight = 370;
	private static final int topHeight = 90;
	private static final int btmHeight = 50;
	private static final int midHeight = frameHeight - btmHeight
			- topHeight - 30;
	private static final Object[] baudOptions = { 1200, 2400, 4800, 9600,
			19200, 38400 };
	private static final Object[][] databitOptions = {
			{ 5, 6, 7, 8 },
			{ SerialPort.DATABITS_5, SerialPort.DATABITS_6,
					SerialPort.DATABITS_7, SerialPort.DATABITS_8 } };
	private static final Object[][] stopbitOptions = {
			{ 1, 1.5, 2 },
			{ SerialPort.STOPBITS_1, SerialPort.STOPBITS_1_5,
					SerialPort.STOPBITS_2 } };
	private static final Object[][] parityOptions = {
			{ "None", "Even", "Odd" },
			{ SerialPort.PARITY_NONE, SerialPort.PARITY_EVEN,
					SerialPort.PARITY_ODD } };
	private static final Object[][] flowcontrolOptions = {
			{ "None", "XONOFF In", "XONOFF Out", "RTSCTS In", "RTSCTS Out" },
			{ SerialPort.FLOWCONTROL_NONE, SerialPort.FLOWCONTROL_XONXOFF_IN,
					SerialPort.FLOWCONTROL_XONXOFF_OUT,
					SerialPort.FLOWCONTROL_RTSCTS_IN,
					SerialPort.FLOWCONTROL_RTSCTS_OUT } };

	private JComboBox<Object> comBox;
	private final JComboBox<Object> baudBox = CreateJComboBox.fromObjects (
			baudOptions, 5);
	private final JComboBox<Object> dataBox = CreateJComboBox.fromObjects (
			databitOptions[0], 3);
	private final JComboBox<Object> stopBox = CreateJComboBox.fromObjects (
			stopbitOptions[0], 0);
	private final JComboBox<Object> parityBox = CreateJComboBox.fromObjects (
			parityOptions[0], 0);
	private final JComboBox<Object> flowBox = CreateJComboBox.fromObjects (
			flowcontrolOptions[0], 0);
	private final JButton testButton = new JButton ("Test");
	private final JButton doneButton = new JButton ("Done");

	/**
	 * Creates a CommSettingsView frame and sets content pane.
	 */
	public CommSettingsView ()
	{
		this.setTitle (AppDetails.name ());
		this.setSize (frameWidth, frameHeight);
		this.setResizable (false);
		this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		GUITools.centreFrame (this);
		this.setIconImage (AppDetails.icon ());
		this.setBackground (Color.white);
		this.setContentPane (createContentPane ());
	}

	/**
	 * Gets the selected CommSettings from the Frame Components and creates a
	 * new CommSettings object with them.
	 * 
	 * @return CommSettings.
	 */
	public CommSettings getCommSettings ()
	{
		return new CommSettings (
				DiscoverPorts.listToArray ()[comBox.getSelectedIndex ()],
				(int) baudBox.getSelectedItem (),
				(int) databitOptions[1][dataBox.getSelectedIndex ()],
				(int) stopbitOptions[1][stopBox.getSelectedIndex ()],
				(int) parityOptions[1][parityBox.getSelectedIndex ()],
				(int) flowcontrolOptions[1][flowBox.getSelectedIndex ()]);
	}

	/**
	 * Adds an ActionListener to the "Done" button.
	 * 
	 * @param l
	 *            ActionListener
	 */
	public void addDoneButtonListener (ActionListener l)
	{
		doneButton.addActionListener (l);
	}

	/**
	 * Adds an ActionListener to the "Test" button.
	 * 
	 * @param l
	 *            ActionListener
	 */
	public void addTestButtonListener (ActionListener l)
	{
		testButton.addActionListener (l);
	}

	/*
	 * Draws the main content pane for the CommSettingsView frame.
	 * 
	 * @return Content pane.
	 */
	private JPanel createContentPane ()
	{
		// PANEL OBJECTS -------------------------------------------------------
		TitledBorder borderTop = BorderFactory
				.createTitledBorder ("System Settings");
		borderTop.setTitleJustification (TitledBorder.CENTER);

		TitledBorder borderMid = BorderFactory
				.createTitledBorder ("Microcontroller Settings");
		borderMid.setTitleJustification (TitledBorder.CENTER);

		// MAIN ----------------------------------------------------------------
		JPanel mainPanel = new JPanel ();
		mainPanel.setLayout (null);
		mainPanel.setLocation (0, 0);
		mainPanel.setSize (frameWidth - 20, frameHeight - 20);
		mainPanel.setBackground (Color.white);

		// TOP -----------------------------------------------------------------
		JPanel topPanel = new JPanel ();
		topPanel.setLayout (new GridLayout (4, 2, 10, 5));
		topPanel.setLocation (5, 5);
		topPanel.setSize (frameWidth - 15, topHeight);
		topPanel.setBorder (borderTop);
		topPanel.setBackground (Color.white);
		mainPanel.add (topPanel);

		topPanel.add (new JLabel (OMLView.LABEL_START + "Operating System:"
				+ OMLView.LABEL_END));
		topPanel.add (new JLabel (OMLView.LABEL_START
				+ GetSystemProperties.os () + OMLView.LABEL_END, new ImageIcon (
				"img/12x12/Desktop 2.png", AppDetails.name ()), JLabel.LEFT));
		topPanel.add (new JLabel (OMLView.LABEL_START + "OS Version:"
				+ OMLView.LABEL_END));
		topPanel.add (new JLabel (OMLView.LABEL_START
				+ GetSystemProperties.osVersion () + OMLView.LABEL_END,
				new ImageIcon ("img/12x12/advanced.png", AppDetails.name ()),
				JLabel.LEFT));
		topPanel.add (new JLabel (OMLView.LABEL_START + "System Architecture:"
				+ OMLView.LABEL_END));
		topPanel.add (new JLabel (OMLView.LABEL_START
				+ GetSystemProperties.osArch () + OMLView.LABEL_END,
				new ImageIcon ("img/12x12/64 bit.png", AppDetails.name ()),
				JLabel.LEFT));
		topPanel.add (new JLabel (OMLView.LABEL_START + "Com ports:"
				+ OMLView.LABEL_END));
		JLabel topComRLabel = new JLabel ();
		try
		{
			topComRLabel = new JLabel (OMLView.LABEL_START
					+ DiscoverPorts.size () + OMLView.LABEL_END, new ImageIcon (
					"img/12x12/forward_alt.png", AppDetails.name ()),
					JLabel.LEFT);
		} catch (Throwable e)
		{
			OMLView.showErrorBox ("The required RXTX libraries were unable to"
					+ "start!\nPlease ensure that they are correctly "
					+ "installed and restart the application.");
		}
		topPanel.add (topComRLabel);

		// MID -----------------------------------------------------------------
		JPanel midPanel = new JPanel ();
		midPanel.setLayout (new GridLayout (6, 2, 20, 5));
		midPanel.setLocation (5, topHeight + 5);
		midPanel.setSize (frameWidth - 15, midHeight);
		midPanel.setBorder (borderMid);
		midPanel.setBackground (Color.white);
		mainPanel.add (midPanel);

		JLabel midComLabel = new JLabel (OMLView.LABEL_START + "Com port:"
				+ OMLView.LABEL_END);
		midComLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midComLabel);

		JPanel midComPanel = new JPanel ();
		midComPanel.setLayout (new BorderLayout ());
		midComPanel.setBackground (Color.white);

		try
		{
			comBox = CreateJComboBox.fromObjects (DiscoverPorts.listToArray (),
					0);
			midPanel.add (comBox);
		} catch (Throwable e)
		{
			String[] options = { "-NONE-" };
			comBox = CreateJComboBox.fromObjects (options, 0);
		}
		comBox.setBackground (Color.white);
		midComPanel.add (comBox, BorderLayout.WEST);
		JButton midComRefreshButton = new JButton (new ImageIcon (
				"img/12x12/refresh.png", AppDetails.name ()));
		midComRefreshButton.setBackground (Color.white);
		midComPanel.add (midComRefreshButton, BorderLayout.EAST);

		midPanel.add (midComPanel);

		JLabel midBaudLabel = new JLabel (OMLView.LABEL_START + "Baud rate:"
				+ OMLView.LABEL_END);
		midBaudLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midBaudLabel);

		baudBox.setBackground (Color.white);
		midPanel.add (baudBox);

		JLabel midDataLabel = new JLabel (OMLView.LABEL_START + "Data bits:"
				+ OMLView.LABEL_END);
		midDataLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midDataLabel);

		dataBox.setBackground (Color.white);
		midPanel.add (dataBox);

		JLabel midStopLabel = new JLabel (OMLView.LABEL_START + "Stop bits:"
				+ OMLView.LABEL_END);
		midStopLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midStopLabel);

		stopBox.setBackground (Color.white);
		midPanel.add (stopBox);

		JLabel midParityLabel = new JLabel (OMLView.LABEL_START + "Parity:"
				+ OMLView.LABEL_END);
		midParityLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midParityLabel);

		parityBox.setBackground (Color.white);
		midPanel.add (parityBox);

		JLabel midFlowLabel = new JLabel (OMLView.LABEL_START + "Flow control:"
				+ OMLView.LABEL_END);
		midFlowLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midFlowLabel);

		flowBox.setBackground (Color.white);
		midPanel.add (flowBox);

		// BOTTOM --------------------------------------------------------------
		JPanel btmPanel = new JPanel ();
		btmPanel.setLayout (null);
		btmPanel.setLocation (5, midPanel.getLocation ().y + midHeight);
		btmPanel.setSize (frameWidth - 15, btmHeight - 10);
		btmPanel.setBackground (Color.white);
		mainPanel.add (btmPanel);

		testButton.setToolTipText ("Test microcontroller with these settings");
		testButton.setIcon (new ImageIcon ("img/22x22/plus.png", AppDetails
				.name ()));
		testButton.setLocation (5, 5);
		testButton.setSize ((btmPanel.getSize ().width / 2) - 10,
				btmPanel.getSize ().height - 10);
		testButton.setBackground (Color.white);
		btmPanel.add (testButton);

		doneButton.setToolTipText ("Continue with these settings.");
		doneButton.setIcon (new ImageIcon ("img/22x22/play.png", AppDetails
				.name ()));
		doneButton.setLocation (
				(testButton.getLocation ().x + testButton.getWidth ()) + 10, 5);
		doneButton.setSize ((btmPanel.getSize ().width / 2) - 10,
				btmPanel.getSize ().height - 10);
		doneButton.setBackground (Color.white);
		btmPanel.add (doneButton);

		return mainPanel;
	}

}
