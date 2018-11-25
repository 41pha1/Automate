package world;

import java.awt.AlphaComposite;
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
	public int pipeTexture = 1;
	private boolean piped;
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
		updateIsometric();
		g.drawImage(TextureLoader.grounds[0][0][tex], ix, iy, null);
	}

	public void updateIsometric()
	{
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		ix = (int) (x - y);
		iy = (int) ((x + y) / 2f);
	}

	public void render(Graphics2D g, int scale)
	{
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		ix = (int) (x - y);
		iy = (int) ((x + y) / 2f);
		g.drawImage(TextureLoader.grounds[0][0][tex], ix, iy, scale, scale, null);
	}

	public void renderPipes(Graphics2D g)
	{
		if (piped)
		{
			g.drawImage(TextureLoader.pipes[pipeTexture], ix - 15, iy - 45, null);
		}
	}

	public void renderTransparent(Graphics2D g)
	{
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		render(g);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}

	public int getPipeTexture()
	{
		boolean up = Simulation.map.getNextTile(x, y, 0, 1).piped;
		boolean left = Simulation.map.getNextTile(x, y, 1, 1).piped;
		boolean down = Simulation.map.getNextTile(x, y, 2, 1).piped;
		boolean right = Simulation.map.getNextTile(x, y, 3, 1).piped;
		boolean building = Simulation.map.getTile(x, y).b.connectedToPipes();

		if (!building)
		{
			if (up && right && down && left)
				return 2;
			if (left && right && down)
				return 3;
			if (up && right && down)
				return 4;
			if (up && right && left)
				return 5;
			if (up && down && left)
				return 6;
			if (right && down)
				return 7;
			if (up && right)
				return 8;
			if (up && left)
				return 9;
			if (left && down)
				return 10;
			if (up || down)
				return 0;
		} else
		{
			if (up && right && down && left)
				return 17;
			if (left && right && down)
				return 18;
			if (up && right && down)
				return 19;
			if (up && right && left)
				return 20;
			if (up && down && left)
				return 21;
			if (right && down)
				return 22;
			if (up && right)
				return 23;
			if (up && left)
				return 24;
			if (left && down)
				return 25;
			if (up && down)
				return 15;
			if (left && right)
				return 16;
			if (down)
				return 11;
			if (right)
				return 12;
			if (up)
				return 13;
			if (left)
				return 14;
		}
		return 1;
	}

	public void renderBuilding(Graphics2D g)
	{
		b.render(g, ix, iy);
	}

	public void renderCargo(Graphics2D g)
	{
		b.renderCargo(g);
	}

	public boolean isPiped()
	{
		return piped;
	}

	public void setPiped(boolean piped)
	{
		this.piped = piped;
		updatePipes();
	}

	public void updatePipes()
	{
		pipeTexture = getPipeTexture();
		Simulation.map.getNextTile(x, y, 0, 1).pipeTexture = Simulation.map.getNextTile(x, y, 0, 1).getPipeTexture();
		Simulation.map.getNextTile(x, y, 1, 1).pipeTexture = Simulation.map.getNextTile(x, y, 1, 1).getPipeTexture();
		Simulation.map.getNextTile(x, y, 2, 1).pipeTexture = Simulation.map.getNextTile(x, y, 2, 1).getPipeTexture();
		Simulation.map.getNextTile(x, y, 3, 1).pipeTexture = Simulation.map.getNextTile(x, y, 3, 1).getPipeTexture();
	}

	public void update()
	{
		b.update();
	}
}
