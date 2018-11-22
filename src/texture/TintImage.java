package texture;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TintImage
{
	public BufferedImage tint(float r, float g, float b, float a, BufferedImage sprite)
	{
		BufferedImage tintedSprite = new BufferedImage(sprite.getWidth(), sprite.getHeight(),
				BufferedImage.TRANSLUCENT);
		Graphics2D graphics = tintedSprite.createGraphics();
		graphics.drawImage(sprite, 0, 0, null);
		graphics.dispose();

		for (int i = 0; i < tintedSprite.getWidth(); i++)
		{
			for (int j = 0; j < tintedSprite.getHeight(); j++)
			{
				int ax = tintedSprite.getColorModel().getAlpha(tintedSprite.getRaster().getDataElements(i, j, null));
				int rx = tintedSprite.getColorModel().getRed(tintedSprite.getRaster().getDataElements(i, j, null));
				int gx = tintedSprite.getColorModel().getGreen(tintedSprite.getRaster().getDataElements(i, j, null));
				int bx = tintedSprite.getColorModel().getBlue(tintedSprite.getRaster().getDataElements(i, j, null));
				rx *= r;
				gx *= g;
				bx *= b;
				ax *= a;
				tintedSprite.setRGB(i, j, (ax << 24) | (rx << 16) | (gx << 8) | (bx));
			}
		}
		return tintedSprite;
	}
}
