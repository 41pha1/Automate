package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import input.Mouse;

public class ScrollBar implements Serializable
{
	private static final long serialVersionUID = 1L;
	int ch;
	int x, y, w, h;
	public int ca;

	public ScrollBar(int x, int y, int w, int h)
	{
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void render(Graphics g)
	{
		if (ch > 0)
		{
			g.setColor(new Color(0, 0, 0, 150));
			g.fillRect(x + w / 4, y, w / 2, h);
			g.setColor(Color.WHITE);
			g.fillRect(x, y + (int) (((float) (h - h / 5) / (float) ch) * ca), w, h / 5);
		}
	}

	public void update(int ch, int x, int y)
	{
		this.ch = ch;
		this.x = x;
		this.y = y;
		ca += Mouse.mw * 50;
		Mouse.mw = 0;
		if (ca < 0)
			ca = 0;
		if (ca > ch)
			ca = ch;
	}
}
