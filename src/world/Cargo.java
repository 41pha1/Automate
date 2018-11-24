package world;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

import game.Simulation;
import texture.TextureLoader;
import utility.Vector2D;

public class Cargo implements Serializable
{
	private static final long serialVersionUID = 1302781432064090240L;
	public int x;
	public static final int COAL = 1, IRON_ORE = 2, STONE = 3, LOG = 4, IRON = 5, MOTOR = 6, PROCESSOR = 7, RUBBER = 8,
			COPPER = 9, WIRE = 10, CABLE = 11, COIL = 12, COPPER_ORE = 13;
	public static int DIFFERENTCARGOS = 14;
	public int y;
	float vx, vy;
	public int ID, id;
	boolean arrived = true;
	boolean inMap = false;

	public Cargo(int x, int y, int ID, int id, boolean b)
	{
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.ID = ID;
		this.id = id;
		inMap = b;
	}

	public Cargo(int x, int y, int ID)
	{
		this.x = x;
		this.y = y;
		this.vx = 0;
		this.vy = 0;
		this.ID = ID;
	}

	public Cargo(int x, int y, int ID, int dir)
	{
		this.x = x;
		this.y = y;
		calculateVPos(dir);
		this.ID = ID;
		arrived = false;
	}

	public void calculateVPos(int dir)
	{
		if (dir == 0)
		{
			this.vx = 0;
			this.vy = 1;
		}
		if (dir == 1)
		{
			this.vx = 1;
			this.vy = 0;
		}
		if (dir == 2)
		{
			this.vx = 0;
			this.vy = -1;
		}
		if (dir == 3)
		{
			this.vx = -1;
			this.vy = 0;
		}
	}

	public boolean arrived()
	{
		return arrived;
	}

	public void update()
	{
		float speed = ConveyorBelt.speed;
		if (vx > 0)
		{
			vx -= speed;
			if (vx <= 0)
				arrived = true;
		}
		if (vx < 0)
		{
			vx += speed;
			if (vx >= 0)
				arrived = true;
		}
		if (vy > 0)
		{
			vy -= speed;
			if (vy <= 0)
				arrived = true;
		}
		if (vy < 0)
		{
			vy += speed;
			if (vy >= 0)
				arrived = true;
		}
	}

	public void renderAt(Graphics g, int x, int y, int width, int height)
	{
		g.drawImage(TextureLoader.cargo[ID], x, y, width, height, null);
	}

	public void render(Graphics g)
	{
		int isoX, isoY;
		float xp = ((x + vx + Simulation.map.t.getX()) * Simulation.map.t.getSize());
		float yp = ((y + vy + Simulation.map.t.getY()) * Simulation.map.t.getSize());
		isoX = (int) (xp - yp);
		isoY = (int) ((xp + yp) / 2f);
		isoY -= 32;
		isoX += 32;
		renderAt(g, isoX, isoY, 64, 64);
	}

	public ArrayList<Vector2D> getProductionCost()
	{
		return getProductionCost(ID);
	}

	public static ArrayList<Vector2D> getProductionCost(int ID)
	{
		ArrayList<Vector2D> cost = new ArrayList<Vector2D>();
		if (ID == MOTOR)
		{
			cost.add(new Vector2D(IRON, 2));
			cost.add(new Vector2D(COIL, 1));
			cost.add(new Vector2D(CABLE, 1));
			return cost;
		}
		if (ID == WIRE)
		{
			cost.add(new Vector2D(COPPER, 1));
			return cost;

		}
		if (ID == CABLE)
		{
			cost.add(new Vector2D(WIRE, 1));
			cost.add(new Vector2D(RUBBER, 1));
			return cost;
		}
		if (ID == COIL)
		{
			cost.add(new Vector2D(WIRE, 2));
			return cost;
		}
		return cost;
	}

	@Override
	public String toString()
	{
		return getName(ID);
	}

	public static Cargo getOre(Cargo c)
	{
		if (c.ID == Cargo.IRON)
			return new Cargo(0, 0, Cargo.IRON_ORE);
		if (c.ID == Cargo.COPPER)
			return new Cargo(0, 0, Cargo.COPPER_ORE);
		return new Cargo(0, 0, 0);
	}

	public static String getName(int ID)
	{
		if (ID == COAL)
			return "Coal";
		if (ID == IRON_ORE)
			return "Iron Ore";
		if (ID == STONE)
			return "Stone";
		if (ID == LOG)
			return "Log";
		if (ID == IRON)
			return "Iron";
		if (ID == MOTOR)
			return "Motor";
		if (ID == PROCESSOR)
			return "Processor";
		if (ID == RUBBER)
			return "Rubber";
		if (ID == COPPER)
			return "Copper";
		if (ID == WIRE)
			return "Wire";
		if (ID == CABLE)
			return "Cable";
		if (ID == COIL)
			return "Coil";
		if (ID == COPPER_ORE)
			return "Copper Ore";
		return "None";
	}
}
