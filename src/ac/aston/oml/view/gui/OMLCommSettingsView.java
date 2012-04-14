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

import ac.aston.oml.model.data.AppDetails;
import ac.aston.oml.view.CommSettingsView;

import jcummins.gui.HTMLFontset;
import jcummins.gui.JComboBoxUtils;
import jcummins.misc.GetSystemProperties;


/**
 * This extension of JFrame draws a frame that can be used to set the comm
 * settings for serial communications with the microcontroller.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLCommSettingsView extends JFrame implements CommSettingsView
{
	/** Serial UID. */
	private static final long serialVersionUID = -7903183494493646804L;

	private static final int frameWidth = 320;
	private static final int frameHeight = 370;
	private static final int topHeight = 90;
	private static final int btmHeight = 50;
	private static final int midHeight = frameHeight - btmHeight - topHeight
			- 30;

	private HTMLFontset h;

	private final JLabel comPortCountLabel = new JLabel ();
	private JComboBox<Object> comBox = new JComboBox<Object> ();
	private JComboBox<Object> baudBox = new JComboBox<Object> ();
	private JComboBox<Object> dataBox = new JComboBox<Object> ();
	private JComboBox<Object> stopBox = new JComboBox<Object> ();
	private JComboBox<Object> parityBox = new JComboBox<Object> ();
	private JComboBox<Object> flowBox = new JComboBox<Object> ();
	private final JButton comRefreshButton = new JButton ();
	private final JButton testButton = new JButton ("Test");
	private final JButton doneButton = new JButton ("Done");

	/**
	 * Creates a OMLCommSettingsView frame and sets content pane.
	 */
	public OMLCommSettingsView ()
	{
		this.setTitle (AppDetails.name ());
		this.setSize (frameWidth, frameHeight);
		this.setResizable (false);
		this.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
		this.setIconImage (AppDetails.icon ());
		this.setBackground (Color.white);
	}

	@Override
	public void init (HTMLFontset h)
	{
		this.h = h;
		this.setContentPane (createContentPane ());
	}

	@Override
	public void teardown ()
	{
		this.dispose ();
	}

	public void addRefreshPortsButtonListener (ActionListener l)
	{
		comRefreshButton.addActionListener (l);
	}

	@Override
	public void setComOptions (Object[] o)
	{
		JComboBoxUtils.updateContents (comBox, o);
	}

	@Override
	public void setBaudOptions (Object[] is, int index)
	{
		JComboBoxUtils.updateContents (baudBox, is, index);
	}

	@Override
	public void setDataOptions (Object[] o, int index)
	{
		JComboBoxUtils.updateContents (dataBox, o, index);
	}

	@Override
	public void setStopOptions (Object[] o, int index)
	{
		JComboBoxUtils.updateContents (stopBox, o, index);
	}

	@Override
	public void setParityOptions (Object[] o, int index)
	{
		JComboBoxUtils.updateContents (parityBox, o, index);
	}

	@Override
	public void setFlowOptions (Object[] o, int index)
	{
		JComboBoxUtils.updateContents (flowBox, o, index);
	}

	@Override
	public void addRefreshButtonListener (ActionListener l)
	{
		comRefreshButton.addActionListener (l);
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
	 * Draws the main content pane for the OMLCommSettingsView frame.
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

		topPanel.add (new JLabel (h.format ("label", "Operating System:")));
		topPanel.add (new JLabel (h.format ("label", GetSystemProperties.os ()), new ImageIcon (
				"img/12x12/Desktop 2.png", AppDetails.name ()), JLabel.LEFT));
		topPanel.add (new JLabel (h.format ("label", "OS Version:")));
		topPanel.add (new JLabel (h.format ("label", GetSystemProperties.osVersion ()), new ImageIcon (
				"img/12x12/advanced.png", AppDetails.name ()), JLabel.LEFT));
		topPanel.add (new JLabel (h.format ("label", "System Architecture:")));
		topPanel.add (new JLabel (h.format ("label", GetSystemProperties.osArch ()), new ImageIcon (
				"img/12x12/64 bit.png", AppDetails.name ()), JLabel.LEFT));
		topPanel.add (new JLabel (h.format ("label", "Com Ports:")));
		topPanel.add (comPortCountLabel);

		// MID -----------------------------------------------------------------
		JPanel midPanel = new JPanel ();
		midPanel.setLayout (new GridLayout (6, 2, 20, 5));
		midPanel.setLocation (5, topHeight + 5);
		midPanel.setSize (frameWidth - 15, midHeight);
		midPanel.setBorder (borderMid);
		midPanel.setBackground (Color.white);
		mainPanel.add (midPanel);

		JLabel midComLabel = new JLabel (h.format ("label", "Com Port:"));
		midComLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midComLabel);

		JPanel midComPanel = new JPanel ();
		midComPanel.setLayout (new BorderLayout ());
		midComPanel.setBackground (Color.white);

		comBox.setBackground (Color.white);
		midComPanel.add (comBox, BorderLayout.WEST);
		comRefreshButton.setIcon (new ImageIcon ("img/12x12/refresh.png", this
				.getTitle ()));
		comRefreshButton.setBackground (Color.white);
		midComPanel.add (comRefreshButton, BorderLayout.EAST);

		midPanel.add (midComPanel);

		JLabel midBaudLabel = new JLabel (h.format ("label", "Baud Rate:"));
		midBaudLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midBaudLabel);

		baudBox.setBackground (Color.white);
		midPanel.add (baudBox);

		JLabel midDataLabel = new JLabel (h.format ("label", "Data Bits:"));
		midDataLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midDataLabel);

		dataBox.setBackground (Color.white);
		midPanel.add (dataBox);

		JLabel midStopLabel = new JLabel (h.format ("label", "Stop Bits:"));
		midStopLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midStopLabel);

		stopBox.setBackground (Color.white);
		midPanel.add (stopBox);

		JLabel midParityLabel = new JLabel (h.format ("label", "Parity:"));
		midParityLabel.setHorizontalAlignment (JLabel.LEFT);
		midPanel.add (midParityLabel);

		parityBox.setBackground (Color.white);
		midPanel.add (parityBox);

		JLabel midFlowLabel = new JLabel (h.format ("label", "Flow Control"));
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

	@Override
	public int getSelectedComOption ()
	{
		return comBox.getSelectedIndex ();
	}

	@Override
	public int getSelectedBaudOption ()
	{
		return baudBox.getSelectedIndex ();
	}

	@Override
	public int getSelectedDataOption ()
	{
		return dataBox.getSelectedIndex ();
	}

	@Override
	public int getSelectedStopOption ()
	{
		return stopBox.getSelectedIndex ();
	}

	@Override
	public int getSelectedParityOption ()
	{
		return parityBox.getSelectedIndex ();
	}

	@Override
	public int getSelectedFlowOption ()
	{
		return flowBox.getSelectedIndex ();
	}

}
