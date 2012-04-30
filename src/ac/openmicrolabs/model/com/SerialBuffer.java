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

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class acts as a FIFO queue between the SerialReader and any higher level
 * loggers.
 * 
 * @author Chris Cummins
 * 
 */
public class SerialBuffer extends Observable implements Observer, Runnable {

	private LinkedBlockingQueue<Double[]> queue = new LinkedBlockingQueue<Double[]>();
	private Double[] msg;
	private boolean stopRequest = false;

	@Override
	public final void run() {
		while (true) {
			try {
				if (!stopRequest) {
					msg = get();
					setChanged();
					notifyObservers(msg);
				} else {
					return;
				}
			} catch (NullPointerException e1) {
				// Don't care.
			} catch (InterruptedException e2) {
				return;
			}
		}
	}

	@Override
	public final void update(final Observable arg0, final Object arg1) {
		try {
			put((Double[]) arg1);
		} catch (InterruptedException e) {
			// Don't care.
		}
	}

	/**
	 * Signal that the thread should stop execution.
	 */
	public final void stopThread() {
		stopRequest = true;
	}

	/*
	 * Add latest data to queue.
	 */
	private void put(final Double[] d) throws InterruptedException {
		queue.put(d);
	}

	/*
	 * Waits until there is data, then returns it.
	 */
	private Double[] get() throws InterruptedException {
		return queue.take();
	}

}
