/* Chris Cummins - 12 Apr 2012
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

package ac.aston.oml.model.settings;

import jcummins.gui.CStyle;
import jcummins.gui.HTMLFont;
import jcummins.gui.HTMLFontset;

/**
 * @author Chris Cummins
 * 
 */
public class OMLSettings
{
	private final CStyle style;
	private final Object[] baudOptions;
	private final Object[][] databitOptions;
	private final Object[][] stopbitOptions;
	private final Object[][] parityOptions;
	private final Object[][] flowOptions;

	public OMLSettings (CStyle s, Object[] baudOptions, Object[][] databitOptions,
			Object[][] stopbitOptions, Object[][] parityOptions,
			Object[][] flowOptions)
	{
		this.style = s;
		this.baudOptions = baudOptions;
		this.databitOptions = databitOptions;
		this.stopbitOptions = stopbitOptions;
		this.parityOptions = parityOptions;
		this.flowOptions = flowOptions;
	}

	public CStyle style ()
	{
		return style;
	}

	public HTMLFont font (String label)
	{
		return style.font (label);
	}
	
	public HTMLFontset fontset ()
	{
		return style.fontset ();
	}

	/**
	 * @return the baudOptions
	 */
	public Object[] baudOptions ()
	{
		return baudOptions;
	}

	/**
	 * @return the databitOptions
	 */
	public Object[][] databitOptions ()
	{
		return databitOptions;
	}

	/**
	 * @return the stopbitOptions
	 */
	public Object[][] stopbitOptions ()
	{
		return stopbitOptions;
	}

	/**
	 * @return the parityOptions
	 */
	public Object[][] parityOptions ()
	{
		return parityOptions;
	}

	/**
	 * @return the flowOptions
	 */
	public Object[][] flowOptions ()
	{
		return flowOptions;
	}

}
