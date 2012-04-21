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

package ac.openmicrolabs.model.com;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

/**
 * This class acts as a FIFO queue between the SerialReader and any higher level
 * loggers.
 * 
 * @author Chris Cummins
 * 
 */
public class SerialBuffer extends Observable implements Observer, Runnable {

	private LinkedList<Double[]> queue = new LinkedList<Double[]>();
	private Double[] msg;

	@Override
	public final void run() {
		while (true) {
			msg = get();
			setChanged();
			notifyObservers(msg);
		}
	}

	@Override
	public final void update(final Observable arg0, final Object arg1) {
		put((Double[]) arg1);
	}

	/*
	 * Add latest data to queue.
	 */
	private void put(final Double[] d) {
		queue.add(d);
	}

	/*
	 * Waits until there is data, then returns it.
	 */
	private Double[] get() {
		while (queue.isEmpty()) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// Don't care.
			}
		}

		return ((!queue.isEmpty()) ? queue.removeFirst() : null);
	}

}
