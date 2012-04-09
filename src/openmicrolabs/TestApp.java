
package openmicrolabs;

public class TestApp
{

	/**
	 * @param args
	 */
	public static void main (String[] args)
	{
		TestObj t = new TestObj();
		TestObs o = new TestObs();
		t.addObserver (o);
		t.run ();
	}

}
