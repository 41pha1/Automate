package utility;

public class Counter 
{
	static int n;
	public static int lastCount = 0;
	static long t = System.currentTimeMillis();
	public static void update()
	{
		if(System.currentTimeMillis()-t>1000)
		{
			lastCount = n;
			n=0;
			t=System.currentTimeMillis();
		}
	}
	public static void count()
	{
		n++;
	}
}
