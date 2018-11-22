package world;

import java.awt.Graphics;
import java.io.Serializable;

import data.Tasks;
import input.Picker;
import utility.Vector2D;

public class Entity implements Serializable
{
	private static final long serialVersionUID = -3227569261581045169L;
	public float x;
	public float y;
	int ID;
	int dir;
	float speed = 0.02f;
	boolean moving;
	public Entity(float x, float y, int id)
	{
		dir = 0;
		this.x = x;
		this.y = y;
		this.ID = id;
		moving = false;
	}
	public void renderSelection(Graphics g) 
	{
		
	}
	public Entity(float x, float y, int id, int d)
	{
		dir = d;
		this.x = x;
		this.y = y;
		this.ID = id;
		moving = false;
	}
	public void setActive(boolean b)
	{
		
	}
	public void render(Graphics g)
	{
		
	}
	public void update()
	{
		
	}
	public Tasks getTasks()
	{
		return new Tasks(ID);
	}
	public boolean isSelected(float d)
	{
		return (Math.abs(Picker.pick.x-x)<d)&&(Math.abs(Picker.pick.y-y)<d);
	}
	public Vector2D getPosToRenderAt()
	{
		return new Vector2D(0,0);
	}
	public String getName()
	{
		return "NoName";
	}
	public float getDisToMouse()
	{
		float xd = Math.abs(Picker.pick.x-x);
		float yd = Math.abs(Picker.pick.y-y);
		return (float)Math.sqrt((xd*xd)+(yd*yd));
	}
	public int[] getInventory() 
	{
		return new int[10];
	}
}