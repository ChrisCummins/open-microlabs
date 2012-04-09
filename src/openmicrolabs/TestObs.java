
package openmicrolabs;

import java.util.Observable;
import java.util.Observer;

public class TestObs implements Observer
{
	TestObj t;
	

	@Override
	public void update (Observable arg0, Object arg1)
	{
		System.out.println("Observer updated!");
	}

	
}
