package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import display.Frame;
import world.Building;
import world.Cargo;

public class InfoTag
{
	public static int width = 200, height = 100, buildingsize = 64, yoff = 0, dir = 1;

	public static void render(Graphics2D g, Building b, int x, int y)
	{
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(x, y, width, height);
		g.setFont(new Font("Serial", Font.BOLD, 16));
		g.setColor(Color.WHITE);
		g.drawString(b.getName() + "", x + 10, y + 20);
		g.drawRect(x, y, width - 4, height - 4);
		g.setColor(new Color(255, 255, 255, 25));
		g.fillRect(x + width - buildingsize, y, buildingsize, buildingsize);
		b.render(g, x + width - buildingsize, y, buildingsize);
		renderPrice(g, b.getPrice(), x, y + 24);
	}

	public static void renderPrice(Graphics2D g, int[] price, int x, int y)
	{
		yoff += dir;
		BufferedImage priceTag = new BufferedImage(Frame.width - x, Frame.height - y, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tag = priceTag.createGraphics();
		int j = 0;
		int k = 0;
		for (int i : price)
		{
			if (i > 0)
			{
				Cargo c = new Cargo(0, 0, k);
				c.renderAt(tag, 10, yoff + j * 32, 32, 32);
				tag.setFont(new Font("Serial", Font.BOLD, 10));
				tag.setColor(Color.WHITE);
				tag.drawString(i + "x " + c, 48, yoff + 20 + j * 32);
				j++;
			}
			k++;
		}
		if (j <= 2)
			yoff = 0;
		else if (((j * 32) - 48) <= -yoff)
			dir = 1;
		else if (yoff >= 16)
			dir = -1;
		g.drawImage(priceTag, x, y, priceTag.getWidth(), priceTag.getHeight(), null);
	}
}
