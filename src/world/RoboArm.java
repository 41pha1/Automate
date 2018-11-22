package world;

import java.awt.Graphics2D;

import game.Simulation;
import texture.TextureLoader;

public class RoboArm extends Building
{
	private static final long serialVersionUID = -2432699011042396008L;
	public static final int ID = 1;
	public Cargo c;
	public int fastAnimation;
	public float f = 0;
	public boolean lifting = false;

	public RoboArm()
	{

	}

	public RoboArm(int x, int y)
	{
		c = new Cargo(0, 0, 0);
		this.dir = 0;
		this.x = x;
		this.y = y;
		this.built = true;
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public boolean accepts(int ID)
	{
		return false;
	}

	@Override
	public Cargo getCargo()
	{
		return c;
	}

	@Override
	public void update()
	{
		f += 0.2;
		if (f >= 1f)
		{
			f = 0;
			fastAnimation++;
		}
		c.update();
		Tile child = Simulation.map.getNextTile(this.x, this.y, dir, -1);
		if (fastAnimation % 32 == 31)
		{
			fastAnimation = 0;
			lifting = false;
		}
		if (c.ID != 0 && fastAnimation % 32 == 15)
		{
			if (child.b.accepts(c.ID))
			{
				child.b.setCargo(new Cargo(child.x, child.y, c.ID));
				c = new Cargo(0, 0, 0);
			}
		} else if (!lifting)
		{
			Tile parent = Simulation.map.getNextTile(this.x, this.y, dir, 1);
			Cargo cargo = parent.b.getCargo();
			float dis = Math.abs(cargo.vx) + Math.abs(cargo.vy);
			if ((parent.b.getCargo().ID != 0 && dis < 0.1f) && child.b.accepts(parent.b.getCargo().ID))
			{
				c = new Cargo(this.x, this.y, parent.b.takeCargo().ID);
				lifting = true;
				fastAnimation = 0;
			}
		}
	}

	@Override
	public void setCargo(Cargo c)
	{
		this.c = c;
	}

	@Override
	public String getName()
	{
		return "Robotic Arm";
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.MOTOR] = 2;
		price[Cargo.PROCESSOR] = 1;
		price[Cargo.IRON] = 2;
		return price;
	}

	@Override
	public void render(Graphics2D g, int x, int y, int size)
	{
		g.drawImage(TextureLoader.getTexture(this), (int) (x - size / 2f), y, size * 2, size, null);
	}

	@Override
	public void render(Graphics2D g, int x, int y)
	{
		int img = 0;
		if (lifting)
		{
			img = fastAnimation % 32;
			if (img > 15)
				img = 31 - img;
		}
		g.drawImage(TextureLoader.textures[3][dir][img], x - 89, y - 55, null);
	}
}
