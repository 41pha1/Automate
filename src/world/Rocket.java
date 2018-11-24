package world;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import data.Tank;
import game.Simulation;
import gui.Gui;
import gui.ScrollBar;
import texture.TextureLoader;

public class Rocket extends Building
{
	private static final long serialVersionUID = -6160063933783878753L;
	public static final int ID = 5;
	public Cargo c;
	public Tank H, O;
	public int[] materials;
	static BufferedImage body;
	ScrollBar sb;

	public Rocket(int x, int y)
	{
		H = new Tank(0, 5000, 10000);
		O = new Tank(1, 5000, 20000);
		sb = new ScrollBar(0, 0, 12, 120);
		this.x = x;
		this.y = y;
		this.built = true;
		materials = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 1; j < materials.length - 1; j++)
		{
			materials[j] = 1000;
		}
		c = new Cargo(0, 0, 0);
	}

	@Override
	public int[] getInventory()
	{
		return materials;
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public boolean accepts(int ID)
	{
		return true;
	}

	@Override
	public void setCargo(Cargo c)
	{
		this.c = c;
	}

	@Override
	public Cargo getCargo()
	{
		return c;
	}

	@Override
	public void update()
	{
		if (c.ID != 0)
		{
			materials[c.ID]++;
			c = new Cargo(0, 0, 0);
		}
	}

	@Override
	public String getName()
	{
		return "Rocket";
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.IRON] = 10;
		price[Cargo.LOG] = 6;
		return price;
	}

	@Override
	public void renderBody(Graphics2D g, int posx, int posy)
	{
		body = new BufferedImage(Gui.width, Gui.height - 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = body.createGraphics();
		b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		b.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		sb.update(Gui.getLengthOfInventory(materials) / 4 * 50 - 100, 380, 15);
		sb.render(b);
		int yoff = -sb.ca;
		Gui.showInventory(materials, b, 0, 10 + yoff);
		H.render(b, 240, 10);
		O.render(b, 310, 10);
		g.drawImage(body, posx, posy, null);
	}

	@Override
	public void render(Graphics2D g, int x, int y)
	{
		int i = Simulation.intro;
		if (i < 100)
		{
			g.drawImage(TextureLoader.rocketAnimation[i], x - 10 - 256, y - 65 - 256 - 128, null);
		} else if (i < 130)
		{
			float opacity = (i - 100) / 30f;
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g.drawImage(TextureLoader.getTexture(this), x - 10, y - 65, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1 - opacity));
			g.drawImage(TextureLoader.rocketAnimation[99], x - 10 - 256, y - 65 - 256 - 128, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		} else
			g.drawImage(TextureLoader.getTexture(this), x - 10, y - 65, null);
	}
}
