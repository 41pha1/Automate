package animation;

public class Animator
{
	public static int updates = 0;
	public static int fastAnimation = 0;
	public static int veryFastAnimation = 0;
	public static int dayNight = 90;
	public static float dn = 0;
	public static float u = 0;
	public static float f = 0;

	public static void update()
	{
		dn += 0.05;
		if (dn >= 1)
		{
			dn = 0;
			dayNight++;
		}
		u += 0.1;
		if (u >= 1)
		{
			u = 0;
			updates++;
		}
		f += 0.1;
		if (f >= 0.5f)
		{
			f = 0;
			fastAnimation++;
		}
		veryFastAnimation++;
	}
}
