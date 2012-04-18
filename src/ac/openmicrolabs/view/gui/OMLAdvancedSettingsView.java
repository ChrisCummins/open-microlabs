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

import ac.openmicrolabs.include.AppDetails;
import ac.openmicrolabs.view.AdvancedSettingsView;

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
import javax.swing.JTextArea;

import com.jcummins.gui.JComboBoxUtils;
import com.jcummins.html.font.HTMLFontset;


/**
 * This extension of JFrame draws a frame that can be used to set the Advanced
 * Settings for a logging session.
 * 
 * @author Chris Cummins
 * 
 */
public class OMLAdvancedSettingsView extends JFrame implements
		AdvancedSettingsView {
	private static final long serialVersionUID = -1475245631891524654L;
	private static final int FRAME_WIDTH = 260;
	private static final int FRAME_HEIGHT = 150;
	private static final int GRID_PANEL_VGAP = 5;

	private HTMLFontset h;

	private final JComboBox<Object> timeRangeBox = new JComboBox<Object>();
	private final JTextArea minYText = new JTextArea(1, 8);
	private final JTextArea maxYText = new JTextArea(1, 8);
	private final JButton doneButton = new JButton("Done");

	/**
	 * Create a new OMLAdvancedSettingsView frame.
	 */
	public OMLAdvancedSettingsView() {
		this.setTitle(AppDetails.name());
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(AppDetails.icon());
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
	public final void setTimeRangeOptions(final Object[] o,
			final int selectedIndex) {
		JComboBoxUtils.updateContents(timeRangeBox, o, selectedIndex);
	}

	@Override
	public final void setMinYText(final String s) {
		minYText.setText(s);
	}

	@Override
	public final void setMaxYText(final String s) {
		maxYText.setText(s);
	}

	@Override
	public final void addDoneButtonListener(final ActionListener l) {
		doneButton.addActionListener(l);
	}

	@Override
	public final int getTimeRangeSelectedIndex() {
		return timeRangeBox.getSelectedIndex();
	}

	@Override
	public final String getMinYText() {
		return minYText.getText();
	}

	@Override
	public final String getMaxYText() {
		return maxYText.getText();
	}

	/*
	 * Create JComponents and populate panel.
	 */
	private JPanel createContentPane() {
		final JPanel contentPanel = new JPanel();
		final GridLayout g = new GridLayout(4, 1);
		g.setVgap(GRID_PANEL_VGAP);
		contentPanel.setLayout(g);
		contentPanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);

		final JPanel minYPanel = new JPanel();
		minYPanel.setLayout(new GridLayout(1, 2));
		minYPanel.add(new JLabel(h.format("label", "Min Y vale:")));
		minYText.setText("0");
		minYText.setBorder(BorderFactory.createLineBorder(Color.black));
		minYPanel.add(minYText);

		final JPanel maxYPanel = new JPanel();
		maxYPanel.setLayout(new GridLayout(1, 2));
		maxYPanel.add(new JLabel(h.format("label", "Max Y vale:")));
		maxYText.setText("1023");
		maxYText.setBorder(BorderFactory.createLineBorder(Color.black));
		maxYPanel.add(maxYText);

		final JPanel timeRangePanel = new JPanel();
		timeRangePanel.setLayout(new GridLayout(1, 2));
		timeRangePanel.add(new JLabel(h.format("label", "Graph Time Range:")));
		timeRangePanel.add(timeRangeBox);

		doneButton.setIcon(new ImageIcon("img/22x22/play.png", AppDetails
				.name()));

		contentPanel.add(minYPanel);
		contentPanel.add(maxYPanel);
		contentPanel.add(timeRangePanel);
		contentPanel.add(doneButton);

		return contentPanel;
	}

}
