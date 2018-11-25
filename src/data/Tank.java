package data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;

public class Tank implements Serializable
{
	private static final long serialVersionUID = 1L;
	public int gasType;
	public float amount;
	public float capacity;

	public Tank(int gasType, float amount, float capacity)
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
		g.setColor(new Color(255, 255, 255, 100));
		g.rotate(-Math.PI / 2);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Serial", Font.BOLD, 15));
		int StringWidth = g.getFontMetrics().stringWidth(toString());
		g.drawString(toString(), -(y + 65 + StringWidth / 2), x + 29);
		g.rotate(Math.PI / 2);
	}

	public Color getColor()
	{
		switch (gasType)
		{
		case 0:
			return new Color(100, 100, 200);
		case 1:
			return new Color(200, 50, 50);
		case 2:
			return new Color(50, 50, 200);
		default:
			return new Color(200, 50, 200);
		}
	}

	public void update()
	{
		if (amount > capacity)
			amount = capacity;
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
		case 2:
			return "Water";
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
