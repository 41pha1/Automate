package data;

import java.awt.Graphics2D;
import java.io.Serializable;

import animation.Animator;
import game.Simulation;
import world.Building;

public class Schematic implements Serializable
{
	private static final long serialVersionUID = 1L;
	public Building[][] buildings;
	public Building[][] save;
	public boolean[][] pipesToBuild, pipesToDestroy;

	public boolean saved = false;
	int w, h;

	public Schematic(int width, int height)
	{
		w = width;
		h = height;
		buildings = new Building[w][h];
		save = new Building[w][h];
		pipesToBuild = new boolean[w][h];
		pipesToDestroy = new boolean[w][h];

		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				save[x][y] = new Building(x, y);
				pipesToBuild[x][y] = false;
				pipesToDestroy[x][y] = false;
			}
		}
	}

	public void load()
	{
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				buildings[i][j] = save[i][j];
				if (Simulation.map.tiles[i][j].b.built)
					buildings[i][j] = Simulation.map.tiles[i][j].b.clone();
			}
		}
	}

	public Building getNextBuilding(int x, int y, int dir, int d)
	{
		if (dir == 0)
			return getBuilding(x, y - d);
		if (dir == 1)
			return getBuilding(x - d, y);
		if (dir == 2)
			return getBuilding(x, y + d);
		if (dir == 3)
			return getBuilding(x + d, y);
		return getBuilding(x, y);
	}

	public Building getBuilding(int x, int y)
	{
		if (x < 0 || x >= w || y < 0 || y >= h)
		{
			return new Building(0, 0);
		}
		return buildings[x][y];
	}

	public void discard()
	{
		saved = true;
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				buildings[x][y] = new Building(0, 0);
			}
		}
	}

	public void save()
	{
		for (int i = 0; i < w; i++)
		{
			for (int j = 0; j < h; j++)
			{
				save[i][j] = buildings[i][j];
				buildings[i][j] = new Building(0, 0);
			}
		}
		saved = true;
	}

	public void render(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{
		for (int x = xoff; x < xmax; x++)
		{
			for (int y = yoff; y < ymax; y++)
			{
				if (buildings[x][y].built)
				{
					if (buildings[x][y].compare(Simulation.map.tiles[x][y].b))
						buildings[x][y].render(g);
					else
						buildings[x][y].renderHighlighted(g);
				} else if (Simulation.map.tiles[x][y].b.built)
				{
					if (Animator.updates / 4 % 2 == 0)
						Simulation.map.tiles[x][y].b.renderHighlighted(g);
				}
			}
		}
	}

	public void renderPipes(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{

		for (int x = xoff; x < xmax; x++)
		{
			for (int y = yoff; y < ymax; y++)
			{
				if (!pipesToDestroy[x][y])
					Simulation.map.tiles[x][y].renderPipes(g);
			}
		}
		for (int x = xoff; x < xmax; x++)
		{
			for (int y = yoff; y < ymax; y++)
			{
				Simulation.map.tiles[x][y].renderTransparent(g);
			}
		}
	}

	public void update()
	{
		for (int x = 0; x < w; x++)
		{
			for (int y = 0; y < h; y++)
			{
				buildings[x][y].updateInSchematic();
			}
		}
	}
}
