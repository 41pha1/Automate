package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Gastank implements Serializable
{
	private static final long serialVersionUID = 1L;
	public int gasType;
	public float amount;
	public float capacity;

	public Gastank(int gasType, float amount, float capacity)
	{
		super();
		this.gasType = gasType;
		this.amount = amount;
		this.capacity = capacity;
	}

	public void render(Graphics2D g, int x, int y)
	{
		g.setColor(new Color(0, 0, 0, 100));
		g.fillRect(x, y, 50, 130);
		g.fillRect(x + 15, y + 15, 20, 100);
		g.setColor(getColor());
		int stored = (int) ((amount / capacity) * 100);
		g.fillRect(x + 15, y + 115 - stored, 20, stored);
	}

	public Color getColor()
	{
		switch (gasType)
		{
		case 0:
			return new Color(100, 100, 200);
		case 1:
			return new Color(200, 50, 50);
		default:
			return new Color(200, 50, 200);
		}
	}

	@Override
	public String toString()
	{
		switch (gasType)
		{
		case 0:
			return "Hydrogen";
		case 1:
			return "Oxygen";
		default:
			return "Unknown";
		}
	}

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}
}
