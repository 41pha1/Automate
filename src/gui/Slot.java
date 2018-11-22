package gui;

import input.Mouse;
import world.Building;

public class Slot 
{
	public Building b;
	public boolean MouseOver;
	int x, xpos, ypos, size;
	int x1, x2, y1, y2;
	public Slot(Building b, int x, int xpos, int ypos, int size)
	{
		this.x = x;
		this.xpos = xpos;
		this.ypos = ypos; 
		this.size = size;
		this.b = b;
		x1 = xpos + x * size;
		y1 = ypos;
		x2 = x1 + size;
		y2 = y1 + size;
	}
	public boolean MouseOver()
	{
		return Mouse.x > x1 && Mouse.x < x2 && Mouse.y > y1 && Mouse.y < y2;
	}
	public void update()
	{
		if(MouseOver())
		{
			MouseOver = true;
			if(Mouse.left)Store.cs.get(Store.selectedCategory).selected = x;
		}else
		{
			MouseOver = false;
		}
	}
}
