package world;

import game.Simulation;

public class Quarry extends Building
{
	private static final long serialVersionUID = -7978930742290535095L;
	public float material = 0;
	public int materialPerCargo = 200;
	public static final int materialID = 3;
	public static final int ID = 4;
	public Cargo c;

	public Quarry()
	{

	}

	public Quarry(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.built = true;
		c = new Cargo(0, 0, 0);
	}

	@Override
	public int getMaterials()
	{
		return (int) material;
	}

	@Override
	public void setCargo(Cargo c)
	{
		this.c = c;
	}

	@Override
	public Cargo getCargo()
	{
		return c;
	}

	@Override
	public void update()
	{
		c.update();
		material += Simulation.map.getTile(x, y).r.res[Cargo.STONE];
		if (material > materialPerCargo && c.ID == 0)
		{
			material -= materialPerCargo;
			c = new Cargo(x, y, materialID);
		}
	}

	@Override
	public String getName()
	{
		return "Quarry";
	}
}
