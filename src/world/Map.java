package world;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;
import java.util.ArrayList;

import data.OpenSimplexNoise;
import data.Schematic;
import data.Transformation;
import display.Frame;
import utility.Vector2D;

public class Map implements Serializable
{
	private static final long serialVersionUID = 1315537856817780322L;
	public int width, height;
	public int res = 128;
	public int selectedEntity = -1;
	public Vector2D selectedBuilding;
	public OpenSimplexNoise OSN;
	public Tile[][] tiles;
	public ArrayList<Cargo> cargo;
	public ArrayList<Entity> entities;
	public Transformation t;
	public Schematic schematic;
	public long framesSinceGeneration;

	public Map(int width, int height)
	{
		init(width, height);
	}

	public void init(int width, int height)
	{
		t = new Transformation(50, 50, 64);
		framesSinceGeneration = 0;
		OSN = new OpenSimplexNoise();
		cargo = new ArrayList<Cargo>();
		entities = new ArrayList<Entity>();
		entities.add(new Human(61, 54, entities.size(), 0));
		this.width = width;
		this.height = height;
		selectedBuilding = new Vector2D(-1, -1);
		schematic = new Schematic(width, height);
		generate();
	}

	public boolean isEntityBuilding()
	{
		if (selectedEntity == -1)
			return false;
		if (entities.get(selectedEntity).getTasks().selectedTask == -1)
			return false;
		return entities.get(selectedEntity).getTasks().tasks
				.get(entities.get(selectedEntity).getTasks().selectedTask).building;
	}

	public void updateTasks()
	{
		if (selectedEntity != -1)
		{
			if (entities.get(selectedEntity).getTasks().selectedTask != -1)
			{
				if (entities.get(selectedEntity).getTasks().tasks
						.get(entities.get(selectedEntity).getTasks().selectedTask).building)
				{
					return;
				}
			}
			entities.get(selectedEntity).getTasks().update();
		}
	}

	public boolean AnyEntitySelected()
	{
		return selectedEntity != -1;
	}

	public boolean EntitySelected()
	{
		for (Entity entity : entities)
		{
			if (entity.isSelected(0.4f))
				return true;
		}
		return false;
	}

	public boolean BuildingSelected()
	{
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				if (tiles[x][y].b.isSelected(0.4f))
					return true;
			}
		}
		return false;
	}

	public Tile getNextTile(int x, int y, int dir, int d)
	{
		if (dir == 0)
			return getTile(x, y - d);
		if (dir == 1)
			return getTile(x - d, y);
		if (dir == 2)
			return getTile(x, y + d);
		if (dir == 3)
			return getTile(x + d, y);
		return getTile(x, y);
	}

	public Tile getTile(int x, int y)
	{
		if (x < 0 || x >= width || y < 0 || y >= height)
		{
			return new Tile(0, 0, 0);
		}
		return tiles[x][y];
	}

	public Tile getTile(Vector2D v)
	{
		return getTile((int) v.x, (int) v.y);
	}

	public void generate()
	{
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				int ID = 1;
				tiles[x][y] = new Tile(x, y, ID);
			}
		}
		tiles[60][55].b = new Rocket(60, 55);
		// tiles[60][54].b = new Smeltery(60,54);
	}

	public void update()
	{
		framesSinceGeneration++;
		for (int i = 0; i < cargo.size(); i++)
		{
			cargo.get(i).id = i;
		}
		for (int x = 0; x < width; x++)
		{
			for (int y = 0; y < height; y++)
			{
				tiles[x][y].update();
			}
		}
		for (Entity entity : entities)
		{
			entity.update();
		}
	}

	public void renderBuildingsCargo(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{
		for (int x = xoff; x < xmax + 2; x++)
		{
			for (int y = yoff; y < ymax + 2; y++)
			{
				if (x < width && y < height)
					tiles[x][y].renderCargo(g);
			}
		}
	}

	public void renderCargo(Graphics g, int xoff, int yoff, int xmax, int ymax)
	{
		for (Cargo c : cargo)
		{
			c.render(g);
		}
	}

	public void renderTiles(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{
		for (int x = xoff; x < xmax + 2; x++)
		{
			for (int y = yoff; y < ymax + 2; y++)
			{
				if (x < width && y < height)
					tiles[x][y].render(g);
			}
		}
	}

	public void renderBuildingsInTheBackground(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{
		for (int x = xoff; x < xmax + 2; x++)
		{
			for (int y = yoff; y < ymax + 2; y++)
			{
				if (x < width && y < height && tiles[x][y].b.getID() == 2)
					tiles[x][y].renderBuilding(g);
			}
		}
	}

	public void renderBuildings(Graphics2D g, int xoff, int yoff, int xmax, int ymax)
	{
		for (int x = xoff; x < xmax + 2; x++)
		{
			for (int y = yoff; y < ymax + 2; y++)
			{
				if (x < width && y < height && tiles[x][y].b.built && tiles[x][y].b.getID() != 2)
					tiles[x][y].renderBuilding(g);
				/* TODO */ renderEntities(g, x, y);
			}
		}
	}

	public void renderEntities(Graphics2D g, int x, int y)
	{
		for (Entity entity : entities)
		{
			float dx = Math.abs(x + 0.3f - entity.x);
			float dy = Math.abs(y + 1.2f - entity.y);
			if (dy <= 0.5f && dx <= 0.5f)
				entity.render(g);
		}
	}

	@Override
	public String toString()
	{
		String out = "";
		for (int i = 0; i < width; i++)
		{
			for (int j = 0; j < height; j++)
			{
				if (tiles[i][j].b.built)
					out += tiles[i][j].b.getName() + ", ";
			}
		}
		return out;
	}

	public boolean entityIsSelecting()
	{
		if (selectedEntity == -1)
			return false;
		if (entities.get(selectedEntity).getTasks().selectingTask != -1)
			return true;
		return false;
	}

	public void renderEntitySelection(Graphics2D g)
	{
		for (Entity entity : entities)
		{
			entity.renderSelection(g);
		}
	}

	public void renderSelection(Graphics2D g)
	{
		if (selectedEntity != -1)
		{
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			entities.get(selectedEntity).getTasks().renderSelection(g);
		}
	}

	public void Render(Graphics2D g)
	{
		int xoff = 0;
		int yoff = 0;
		int xmax = (int) (xoff + Frame.width / t.getSize()) * 2;
		int ymax = (int) (yoff + Frame.height / t.getSize()) * 2;
		if (ymax > width)
			ymax = width;
		if (xmax > height)
			xmax = height;
		if (xoff < 0)
			xoff = 0;
		if (yoff < 0)
			yoff = 0;
		xmax = width;
		ymax = height;
		if (xoff < 0)
			xoff = 0;
		if (yoff < 0)
			yoff = 0;
		renderTiles(g, xoff, yoff, xmax, ymax);
		renderSelection(g);
		renderEntitySelection(g);
		renderCargo(g, xoff, yoff, xmax, ymax);
		if (isEntityBuilding())
		{
			schematic.render(g);
			for (Entity entity : entities)
			{
				entity.render(g);
			}
		} else
		{
			renderBuildingsInTheBackground(g, xoff, yoff, xmax, ymax);
			renderBuildings(g, xoff, yoff, xmax, ymax);
			renderBuildingsCargo(g, xoff, yoff, xmax, ymax);
		}
		if (getTile(selectedBuilding).b.built)
			getTile(selectedBuilding).b.renderHighlighted(g);
	}
}