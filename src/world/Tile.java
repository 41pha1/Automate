package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import data.Res;
import game.Simulation;
import texture.TextureLoader;

public class Tile implements Serializable
{
	private static final long serialVersionUID = 6302849209085209706L;
	public static int detail = 16;
	public int x, y;
	public int ID;
	public Building b;
	public Res r;
	public int tex;
	public float[][] terrain;
	int ix, iy;

	public Tile(int x, int y, int ID, float[][] terrain)
	{
		this.x = x;
		this.y = y;
		ix = 0;
		tex = (int) (Math.random() * 4);
		iy = 0;
		this.ID = ID;
		this.terrain = terrain;
		b = new Building(x, y);
		r = new Res(ID);
	}

	public void render(Graphics g)
	{
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		ix = (int) (x - y);
		iy = (int) ((x + y) / 2f);
		g.drawImage(TextureLoader.grounds[0][0][tex], ix, iy, null);
	}

	public void renderBuilding(Graphics2D g)
	{
		b.render(g, ix, iy);
	}

	public void renderCargo(Graphics2D g)
	{
		b.renderCargo(g);
	}

	public void update()
	{
		b.update();
	}
}
