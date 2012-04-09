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

package openmicrolabs.model;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Chris Cummins
 * 
 */
public class SerialBuffer extends Observable implements Observer, Runnable
{

	private LinkedList<Double[]> queue = new LinkedList<Double[]> ();
	private Double[] msg;

	@Override
	public void run ()
	{
		while (true)
		{
			msg = get ();
			setChanged ();
			notifyObservers (msg);
		}
	}

	@Override
	public void update (Observable arg0, Object arg1)
	{
		put ((Double[]) arg1);
	}

	private void put (Double[] d)
	{
		queue.add (d);
	}

	private Double[] get ()
	{
		while (queue.isEmpty ())
			try
			{
				Thread.sleep (1);
			} catch (InterruptedException e)
			{
				// Don't care.
			}
		return queue.removeFirst ();
	}
}
