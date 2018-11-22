package world;

import java.awt.Color;
import java.awt.Graphics;

import game.Simulation;

public class DefaultBuilding extends Building
{
	private static final long serialVersionUID = -4891415651166063349L;
	public static final int ID = -1;
	public Cargo c;
	
	public DefaultBuilding()
	{
		
	}
	public DefaultBuilding(int x, int y) 
	{
		this.x = x;
		this.y = y;
		this.built = true;
		c = new Cargo(0,0,0);
	}
	public boolean accepts(int ID)
	{
		return false;
	}
	public Cargo getCargo()
	{
		return c;
	}
	public int getID()
	{
		return ID;
	}
	public void setCargo(Cargo c)
	{
		this.c = c;
	}
	public void update()
	{
		
	}
	public void renderCargo(Graphics g)
	{
		c.render(g);
	}
	public String getName()
	{
		return "Default";
	}
	public void render(Graphics g, int x, int y)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, (int)Simulation.map.t.getSize(), (int)Simulation.map.t.getSize());
	}	
}
