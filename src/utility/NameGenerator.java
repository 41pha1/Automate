package utility;

public class NameGenerator 
{
	public static int i = 0;
	public static String getName()
	{
		i++;
		if(i == 1)return "Harald";
		else return "Gerald";
	}
}
