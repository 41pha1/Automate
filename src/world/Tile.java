package world;

import java.awt.Graphics2D;
import java.io.Serializable;

import data.Res;
import game.Simulation;
import texture.TextureLoader;

public class Tile implements Serializable
{
	private static final long serialVersionUID = 6302849209085209706L;
	public int x, y;
	public int ID;
	public Building b;
	public Res r;
	public int tex;
	public boolean piped;
	int ix, iy;

	public Tile(int x, int y, int ID)
	{
		this.x = x;
		this.y = y;
		ix = 0;
		tex = (int) (Math.random() * 4);
		iy = 0;
		this.ID = ID;
		b = new Building(x, y);
		r = new Res(ID);
	}

	public void render(Graphics2D g)
	{
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		ix = (int) (x - y);
		iy = (int) ((x + y) / 2f);
		g.drawImage(TextureLoader.grounds[0][0][tex], ix, iy, null);
		renderPipes(g);
	}

	public void renderPipes(Graphics2D g)
	{
		if (piped)
		{
			g.drawImage(TextureLoader.pipes[getPipeTexture()], ix - 15, iy - 45, null);
		}
	}

	public int getPipeTexture()
	{
		boolean up = Simulation.map.getNextTile(x, y, 0, 1).piped;
		boolean left = Simulation.map.getNextTile(x, y, 1, 1).piped;
		boolean down = Simulation.map.getNextTile(x, y, 2, 1).piped;
		boolean right = Simulation.map.getNextTile(x, y, 3, 1).piped;

		if (up && right && down && left)
			return 0;
		if (up && down && left)
			return 5;
		if (left && right && down)
			return 6;
		if (up && right && down)
			return 7;
		if (up && right && left)
			return 8;
		if (left && down)
			return 1;
		if (right && down)
			return 2;
		if (up && right)
			return 3;
		if (up && left)
			return 4;
		if (up || down)
			return 9;
		return 10;
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
