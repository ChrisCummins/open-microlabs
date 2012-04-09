package openmicrolabs;

import java.util.Observable;

public class TestObj extends Observable implements Runnable
{

	@Override
	public void run ()
	{
		while (true)
		{
			try
			{
				Thread.sleep (1000);
			} catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace ();
			}
			System.out.println ("Update called!");
			setChanged();
			notifyObservers ("hello");
		}
	}

}
