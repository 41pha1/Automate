package data;

import java.io.Serializable;

import display.Frame;
import utility.Vector2D;

public class Transformation implements Serializable
{
	private static final long serialVersionUID = 4568517136669064677L;
	float x, y;
	public float wx, wy;
	public float size;

	public float getSize()
	{
		return size;
	}

	public void setSize(float size)
	{
		this.size = size;
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public Transformation(float x, float y, float size)
	{
		this.x = x;
		this.y = y;
		wx = x;
		wy = y;
		this.size = size;
	}

	public void update()
	{
		float dx = 0.1f * (wx - x);
		float dy = 0.1f * (wy - y);
		if (wx != x)
			x += dx;
		if (wy != y)
			y += dy;

	}

	public void setX(float x)
	{
		this.x = x;
	}

	public void setY(float y)
	{
		this.y = y;
	}

	public Vector2D getMapPosition(float x, float y)
	{
		Vector2D pos = new Vector2D();
		pos.x = (x / (size) + y / (size)) / 2;
		pos.y = (y / (size) - (x / (size))) / 2;
		return pos;
	}

	public int getDx(float x, float y)
	{
		x *= size;
		y *= size;
		return (int) (x - y);
	}

	public int getDy(float x, float y)
	{
		x *= size;
		y *= size;
		return (int) ((x + y) / 2f);
	}

	public void lockOn(float x, float y)
	{
		wx = -x + (Frame.width / 4) / size + (Frame.height / 2) / size - 0.5f;
		wy = -y - (Frame.width / 4) / size + (Frame.height / 2) / size + 0.5f;
	}

	public void shake(float x, float y, float s)
	{
		float d = Math.abs(this.wx - this.getX()) + Math.abs(this.wy - this.getY());
		if (d < 1)
		{
			lockOn(x, y);
			wx = (float) (wx + Math.random() * s - (s * 0.5f));
			wy = (float) (wy + Math.random() * s - (s * 0.5f));
		}
	}
}
