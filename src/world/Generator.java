package world;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import gui.Gui;
import texture.TextureLoader;

public class Generator extends Building
{
	private static final long serialVersionUID = 934756892734658723l;
	public static final int ID = 9;
	public static BufferedImage body;
	public boolean general = true, smeltingSelector = false;

	public Generator(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.built = true;
	}

	public Generator()
	{

	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public void update()
	{

	}

	@Override
	public void renderBody(Graphics2D g, int posx, int posy)
	{
		body = new BufferedImage(Gui.width, Gui.height - 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = body.createGraphics();
		b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		b.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.drawImage(body, posx, posy, null);
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.IRON] = 6;
		price[Cargo.MOTOR] = 1;
		price[Cargo.CABLE] = 2;
		return price;
	}

	@Override
	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(TextureLoader.getTexture(this), x - 73, y - 65, null);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int size)
	{
		g.drawImage(TextureLoader.getTexture(this), (int) (x - size / 2f), y, size * 2, size, null);
	}

	@Override
	public String getName()
	{
		return "Generator";
	}
}