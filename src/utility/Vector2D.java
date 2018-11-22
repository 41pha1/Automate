package utility;

import java.io.Serializable;

import game.Simulation;

public class Vector2D implements Serializable
{
	private static final long serialVersionUID = 6546225051852919884L;
	public float x;
	public float y;

	public Vector2D(float x, float y)
	{
		super();
		this.x = x;
		this.y = y;
	}public boolean inBounds()
	{
		if(x < 0 || x >= Simulation.map.tiles.length|| y < 0 || y >= Simulation.map.tiles.length)return false;
		return true;
	}
}
