/* Chris Cummins - 9 Apr 2012
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

package ac.aston.oml.view;

import java.awt.event.ActionListener;

import javax.swing.JFrame;

import jcummins.gui.HTMLFontset;

/**
 * @author Chris Cummins
 * 
 */
public interface CommSettingsView {

	public void init(HTMLFontset h);

	public void teardown();

	public JFrame fetchFrame();

	public void setComOptions(Object[] o);

	public void setBaudOptions(Object[] o, int index);

	public void setDataOptions(Object[] o, int index);

	public void setStopOptions(Object[] o, int index);

	public void setParityOptions(Object[] o, int index);

	public void setFlowOptions(Object[] o, int index);

	public int getSelectedComOption();

	public int getSelectedBaudOption();

	public int getSelectedDataOption();

	public int getSelectedStopOption();

	public int getSelectedParityOption();

	public int getSelectedFlowOption();

	public void addRefreshButtonListener(ActionListener l);

	public void addDoneButtonListener(ActionListener l);

	public void addTestButtonListener(ActionListener l);

}
