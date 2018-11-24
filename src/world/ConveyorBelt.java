package world;

import java.awt.Graphics2D;

import game.Simulation;
import texture.TextureLoader;

public class ConveyorBelt extends Building
{
	private static final long serialVersionUID = -3308391773605798072L;
	public static final int ID = 2;
	public boolean uc = true;
	public Cargo c;
	public int curve = 0;
	public static float speed = 0.01f;

	public ConveyorBelt()
	{

	}

	public ConveyorBelt(int x, int y)
	{
		c = new Cargo(0, 0, 0);
		this.dir = 0;
		this.x = x;
		this.y = y;
		this.built = true;
		try
		{
			updateDir();
		} catch (NullPointerException e)
		{
		}
	}

	public ConveyorBelt(int x, int y, int dir)
	{
		c = new Cargo(0, 0, 0);
		this.dir = 0;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.built = true;
		try
		{
			updateDir();
		} catch (NullPointerException e)
		{
		}
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public void setCargo(Cargo c)
	{
		this.c = c;
	}

	@Override
	public boolean accepts(int ID)
	{
		return c.ID == 0;
	}

	@Override
	public int getConnections()
	{
		int c = 0;
		Tile u = Simulation.map.getNextTile(x, y, 0, 1);
		Tile l = Simulation.map.getNextTile(x, y, 1, 1);
		Tile d = Simulation.map.getNextTile(x, y, 2, 1);
		Tile r = Simulation.map.getNextTile(x, y, 3, 1);
		if (u.b.getID() == 2)
			c++;
		if (l.b.getID() == 2)
			c++;
		if (d.b.getID() == 2)
			c++;
		if (r.b.getID() == 2)
			c++;
		return c;
	}

	@Override
	public void update()
	{
		if (uc)
		{
			updateCurve();
			uc = !uc;
		}
		c.update();
		if (c.ID != 0 && c.arrived())
		{
			Tile child = Simulation.map.getNextTile(this.x, this.y, dir, 1);
			if (child.b.accepts(c.ID))
			{
				child.b.setCargo(new Cargo(child.x, child.y, c.ID, dir));
				c = new Cargo(0, 0, 0);
			}
		}
	}

	@Override
	public void setUC(boolean uc)
	{
		this.uc = uc;
	}

	@Override
	public int getCurvature()
	{
		return curve;
	}

	public void updateDir()
	{
		Tile u = Simulation.map.getNextTile(x, y, 0, 1);
		Tile l = Simulation.map.getNextTile(x, y, 1, 1);
		Tile d = Simulation.map.getNextTile(x, y, 2, 1);
		Tile r = Simulation.map.getNextTile(x, y, 3, 1);
		if (u.b.getID() == 2)
		{
			if (u.b.dir == 1 || u.b.dir == 3)
			{
				dir = 2;
				if (u.b.getConnections() < 2)
				{
					u.b.dir = 2;
					u.b.setUC(true);
				}
			}
		}
		if (l.b.getID() == 2)
		{
			if (l.b.dir == 0 || l.b.dir == 2)
			{
				dir = 3;
				if (l.b.getConnections() < 2)
				{
					l.b.dir = 3;
					l.b.setUC(true);
				}
			}
		}
		if (d.b.getID() == 2)
		{
			if (d.b.dir == 3 || d.b.dir == 1)
			{
				dir = 0;
				if (d.b.getConnections() < 2)
				{
					d.b.dir = 0;
					d.b.setUC(true);
				}
			}
		}
		if (r.b.getID() == 2)
		{
			if (r.b.dir == 0 || r.b.dir == 2)
			{
				dir = 1;
				if (r.b.getConnections() < 2)
				{
					r.b.dir = 1;
					r.b.setUC(true);
				}
			}
		}
		if (u.b.getID() == 2)
		{
			if (u.b.dir == 0)
				dir = 0;
			if (u.b.dir == 2)
				dir = 2;
		}
		if (l.b.getID() == 2)
		{
			if (l.b.dir == 3)
				dir = 3;
			if (l.b.dir == 1)
				dir = 1;
		}
		if (d.b.getID() == 2)
		{
			if (d.b.dir == 2)
				dir = 2;
			if (d.b.dir == 0)
				dir = 0;
		}
		if (r.b.getID() == 2)
		{
			if (r.b.dir == 1)
				dir = 1;
			if (r.b.dir == 3)
				dir = 3;
		}
	}

	@Override
	public void updateDirSchematic()
	{
		Building u = Simulation.map.schematic.getNextBuilding(x, y, 0, 1);
		Building l = Simulation.map.schematic.getNextBuilding(x, y, 1, 1);
		Building d = Simulation.map.schematic.getNextBuilding(x, y, 2, 1);
		Building r = Simulation.map.schematic.getNextBuilding(x, y, 3, 1);
		if (u.getID() == 2)
		{
			if (u.dir == 1 || u.dir == 3)
			{
				dir = 2;
				if (u.getConnections() < 2)
				{
					u.dir = 2;
					u.setUC(true);
				}
			}
		}
		if (l.getID() == 2)
		{
			if (l.dir == 0 || l.dir == 2)
			{
				dir = 3;
				if (l.getConnections() < 2)
				{
					l.dir = 3;
					l.setUC(true);
				}
			}
		}
		if (d.getID() == 2)
		{
			if (d.dir == 3 || d.dir == 1)
			{
				dir = 0;
				if (d.getConnections() < 2)
				{
					d.dir = 0;
					d.setUC(true);
				}
			}
		}
		if (r.getID() == 2)
		{
			if (r.dir == 0 || r.dir == 2)
			{
				dir = 1;
				if (r.getConnections() < 2)
				{
					r.dir = 1;
					r.setUC(true);
				}
			}
		}
		if (u.getID() == 2)
		{
			if (u.dir == 0)
				dir = 0;
			if (u.dir == 2)
				dir = 2;
		}
		if (l.getID() == 2)
		{
			if (l.dir == 3)
				dir = 3;
			if (l.dir == 1)
				dir = 1;
		}
		if (d.getID() == 2)
		{
			if (d.dir == 2)
				dir = 2;
			if (d.dir == 0)
				dir = 0;
		}
		if (r.getID() == 2)
		{
			if (r.dir == 1)
				dir = 1;
			if (r.dir == 3)
				dir = 3;
		}
	}

	public void updateCurveSchematic()
	{
		Building u = Simulation.map.schematic.getNextBuilding(x, y, 0, 1);
		Building l = Simulation.map.schematic.getNextBuilding(x, y, 1, 1);
		Building d = Simulation.map.schematic.getNextBuilding(x, y, 2, 1);
		Building r = Simulation.map.schematic.getNextBuilding(x, y, 3, 1);
		if (u.getID() == 2 && l.getID() == 2)
		{
			if (u.dir == 2 && l.dir == 1)
			{
				curve = 1;
				return;
			}
			if (u.dir == 0 && l.dir == 3)
			{
				curve = 2;
				return;
			}
		}
		if (u.getID() == 2 && r.getID() == 2)
		{
			if (u.dir == 0 && r.dir == 1)
			{
				curve = 1;
				return;
			}
			if (u.dir == 2 && r.dir == 3)
			{
				curve = 2;
				return;
			}
		}
		if (d.getID() == 2 && l.getID() == 2)
		{
			if (d.dir == 2 && l.dir == 3)
			{
				curve = 1;
				return;
			}
			if (d.dir == 0 && l.dir == 1)
			{
				curve = 2;
				return;
			}
		}
		if (d.getID() == 2 && r.getID() == 2)
		{
			if (d.dir == 0 && r.dir == 3)
			{
				curve = 1;
				return;
			}
			if (d.dir == 2 && r.dir == 1)
			{
				curve = 2;
				return;
			}
		}
		curve = 0;
	}

	@Override
	public void updateCurve()
	{
		Tile u = Simulation.map.getNextTile(x, y, 0, 1);
		Tile l = Simulation.map.getNextTile(x, y, 1, 1);
		Tile d = Simulation.map.getNextTile(x, y, 2, 1);
		Tile r = Simulation.map.getNextTile(x, y, 3, 1);
		if (u.b.getID() == 2 && l.b.getID() == 2)
		{
			if (u.b.dir == 2 && l.b.dir == 1)
			{
				curve = 1;
				return;
			}
			if (u.b.dir == 0 && l.b.dir == 3)
			{
				curve = 2;
				return;
			}
		}
		if (u.b.getID() == 2 && r.b.getID() == 2)
		{
			if (u.b.dir == 0 && r.b.dir == 1)
			{
				curve = 1;
				return;
			}
			if (u.b.dir == 2 && r.b.dir == 3)
			{
				curve = 2;
				return;
			}
		}
		if (d.b.getID() == 2 && l.b.getID() == 2)
		{
			if (d.b.dir == 2 && l.b.dir == 3)
			{
				curve = 1;
				return;
			}
			if (d.b.dir == 0 && l.b.dir == 1)
			{
				curve = 2;
				return;
			}
		}
		if (d.b.getID() == 2 && r.b.getID() == 2)
		{
			if (d.b.dir == 0 && r.b.dir == 3)
			{
				curve = 1;
				return;
			}
			if (d.b.dir == 2 && r.b.dir == 1)
			{
				curve = 2;
				return;
			}
		}
		curve = 0;
	}

	@Override
	public void setCurvature(int c)
	{
		curve = c;
		uc = false;
	}

	@Override
	public void changeCurvature()
	{
		curve++;
		if (curve > 2)
			curve = 0;
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.MOTOR] = 1;
		price[Cargo.RUBBER] = 1;
		price[Cargo.IRON] = 1;
		return price;
	}

	@Override
	public void renderCargo(Graphics2D g)
	{
		c.render(g);
	}

	@Override
	public void updateInSchematic()
	{
		if (uc)
		{
			updateCurveSchematic();
			uc = !uc;
		}
	}

	@Override
	public String getName()
	{
		return "Conveyor Belt";
	}

	@Override
	public Cargo getCargo()
	{
		return c;
	}

	@Override
	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(TextureLoader.getTexture(this), x - 15, y - 65, null);
	}

	@Override
	public Cargo takeCargo()
	{
		Cargo cargo = new Cargo(c.x, c.y, c.ID);
		c = new Cargo(0, 0, 0);
		return cargo;
	}
}
