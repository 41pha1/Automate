package gui;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class DropDownMenu 
{
	public ArrayList<Button> items;
	public Button delete;
	int width, height;
	
	public DropDownMenu(int width, int height)
	{
		items = new ArrayList<Button>();
		this.width  = width;
		this.height = height;
		delete = new Button(0,0,0,0,30,30,0);
	}
	public void add(String s)
	{
		items.add(new Button(0,0,0,0,width,height,s));
	}
	public void render(Graphics2D g)
	{
		delete.render(g);
		for(Button b: items)
		{
			b.render(g);
		}
	}
	public void update(int x, int y, int dx, int dy)
	{
		delete.x = x+300;
		delete.y = y;
		delete.dx = dx;
		delete.dy = dy;
		delete.update();
		for(int i = 0; i < items.size(); i++)
		{
			items.get(i).x = x;
			items.get(i).y = y+i*(height-1);
			items.get(i).dx = dx;
			items.get(i).dy = dy;
			items.get(i).update();
		}
	}
}
