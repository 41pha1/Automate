package texture;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class getRandomColor 
{
	public static Color randomColor(BufferedImage b)
	{
		while(true)
		{
			int x = (int) (Math.random()*b.getWidth());
			int y = (int) (Math.random()*b.getHeight());
			Color c = new Color(b.getRGB(x, y), true);
			if(c.getAlpha()>200)return c;
		}
	}
}
