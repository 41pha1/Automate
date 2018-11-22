package animation;

public class Animator 
{
	public static int updates = 0;
	public static int fastAnimation = 0;
	public static int veryFastAnimation = 0;
	public static float u = 0;
	public static float f = 0;
	public static void update()
	{
		u+=0.1;
		if(u>=1)
		{
			u = 0;
			updates++;
		}f+=0.1;
		if(f>=0.5f)
		{
			f = 0;
			fastAnimation++;
		}
		veryFastAnimation++;
	}
}
