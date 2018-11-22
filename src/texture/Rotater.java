package texture;
import java.awt.image.BufferedImage;

public class Rotater 
{
	public static BufferedImage rotate(BufferedImage i, int dir)
	{
		BufferedImage rotated = new BufferedImage(i.getWidth(), i.getHeight(), BufferedImage.TYPE_INT_ARGB);
		if(dir==0)return i;
		if(dir==1)
		{
			int w = i.getWidth();
			int h = i.getHeight();
			for(int x = 0; x < w; x++)
			{
				for(int y = 0; y < h; y++)
				{
					int rgb = i.getRGB(w-x-1,y);
 	                rotated.setRGB(x, y, rgb);
				}
			}
		}return rotated;
	}
	public static BufferedImage[] rotate(BufferedImage[] textures, int dir)
	{
		BufferedImage[] rotatedTextures = new BufferedImage[textures.length];
		for(int i = 0; i < textures.length; i++) 
		{
			BufferedImage toRotate = textures[i];
			BufferedImage rotated = new BufferedImage(toRotate.getWidth(), toRotate.getHeight(), BufferedImage.TYPE_INT_ARGB);
			if(dir==0)rotated = toRotate;
			if(dir==1)
			{
				int w = toRotate.getWidth();
				int h = toRotate.getHeight();
				for(int x = 0; x < w; x++)
				{
					for(int y = 0; y < h; y++)
					{
						int rgb = toRotate.getRGB(w-x-1,y);
	 	                rotated.setRGB(x, y, rgb);
					}
				}
			}
			rotatedTextures[i] = rotated;
		}
		return rotatedTextures;
	}
}
