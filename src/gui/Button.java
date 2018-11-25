package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

import input.Mouse;
import texture.TextureLoader;

public class Button implements Serializable
{
	private static final long serialVersionUID = -8326781563959803688L;
	public int x;
	public int y;
	public int dx = 0;
	public int dy = 0;
	int width;
	int height;
	public String s = "";
	public int FontSize = 14;
	public Font font;
	public boolean pressed = false;
	public boolean stayPressed = false;
	public boolean showBorderWhenPressed = true;
	public boolean showBorder = true;
	public boolean showOverlay = true;
	public boolean mouseOver;
	int texture = -1;

	public Button(int x, int y, int dx, int dy, int width, int height, String s)
	{
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
		this.s = s;
	}

	public Button(int x, int y, int dx, int dy, int width, int height)
	{
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
	}

	public Button(int x, int y, int dx, int dy, int width, int height, int textureID)
	{
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.width = width;
		this.height = height;
		texture = textureID;
	}

	public boolean MouseOver()
	{
		return Mouse.x > x + dx && Mouse.x < x + dx + width && Mouse.y > y + dy && Mouse.y < y + dy + height;
	}

	public void render(Graphics2D g)
	{
		g.setColor(new Color(0, 0, 0, 150));
		if (mouseOver)
			g.setColor(new Color(50, 50, 50, 150));
		if (!showOverlay)
			g.setColor(new Color(100, 100, 100, 150));
		if (showOverlay || mouseOver)
			g.fillRect(x, y, width, height);
		g.setColor(Color.WHITE);
		if (showBorder || (pressed && showBorderWhenPressed))
			g.drawRect(x, y, width - 1, height - 1);
		g.setFont(font);
		g.drawString(s, x + 5, y + height / 1.75f);
		if (texture != -1)
			g.drawImage(TextureLoader.icons[texture], x, y, width, height, null);
	}

	public void update(int x, int y, int dx, int dy)
	{
		this.dx = dx;
		this.dy = dy;
		update(x, y);
	}

	public boolean isPressed()
	{
		update();
		if (pressed)
		{
			pressed = false;
			return true;
		}
		return false;
	}

	public void update(int x, int y)
	{
		this.x = x;
		this.y = y;
		update();
	}

	public void update()
	{
		font = new Font("Serial", Font.PLAIN, FontSize);
		mouseOver = MouseOver();
		if (mouseOver && Mouse.left)
		{
			mouseOver = false;
			pressed = true;
			Mouse.left = false;
		} else
		{
			if (!stayPressed)
				pressed = false;
		}
	}
}
