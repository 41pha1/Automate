package world;

import java.awt.Graphics2D;

import texture.TextureLoader;

public class StorePipe extends Building
{
	private static final long serialVersionUID = 1L;
	public static final int ID = 10;

	public StorePipe(int x, int y)
	{
		super(x, y);
	}

	@Override
	public int getID()
	{
		return ID;
	}

	public StorePipe()
	{

	}

	@Override
	public void render(Graphics2D g, int x, int y, int size)
	{
		g.drawImage(TextureLoader.pipes[10], x, y, size, size, null);
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.PIPE] = 1;
		return price;
	}

	@Override
	public String toString()
	{
		return "Pipe";
	}
}
