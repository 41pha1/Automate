package world;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.Serializable;

import data.Res;
import display.Frame;
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
		updateIsometric();
		if (onScreen())
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
		updateIsometric();
		if (onScreen())
			g.drawImage(TextureLoader.grounds[0][0][tex], ix, iy, scale, scale, null);
	}

	public boolean onScreen()
	{
		return ix < Frame.width && iy < Frame.height && ix + 129 > 0 && iy + 129 > 0;
	}

	public void renderPipes(Graphics2D g)
	{
		if (piped && onScreen())
		{
			g.drawImage(TextureLoader.pipes[pipeTexture], ix - 15, iy - 45, null);
		}
	}

	public void renderTransparent(Graphics2D g)
	{
		if (onScreen())
		{
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
			render(g);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		}
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
		pipeTexture = TextureLoader.getPipeTexture(x, y);
		Simulation.map.getNextTile(x, y, 0, 1).pipeTexture = TextureLoader
				.getPipeTexture(Simulation.map.getNextTile(x, y, 0, 1).x, Simulation.map.getNextTile(x, y, 0, 1).y);
		Simulation.map.getNextTile(x, y, 1, 1).pipeTexture = TextureLoader
				.getPipeTexture(Simulation.map.getNextTile(x, y, 1, 1).x, Simulation.map.getNextTile(x, y, 1, 1).y);
		Simulation.map.getNextTile(x, y, 2, 1).pipeTexture = TextureLoader
				.getPipeTexture(Simulation.map.getNextTile(x, y, 2, 1).x, Simulation.map.getNextTile(x, y, 2, 1).y);
		Simulation.map.getNextTile(x, y, 3, 1).pipeTexture = TextureLoader
				.getPipeTexture(Simulation.map.getNextTile(x, y, 3, 1).x, Simulation.map.getNextTile(x, y, 3, 1).y);
	}

	public void update()
	{
		b.update();
	}
}
