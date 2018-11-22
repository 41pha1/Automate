package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.io.Serializable;

import game.Simulation;
import input.Mouse;
import input.Picker;

public class Selection implements Serializable
{
	private static final long serialVersionUID = 475295437684504673L;
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	boolean selecting = false;
	boolean firstCornerSelected;
	public boolean selected = false;
	int OwnerID;
	
	public Selection(int x1, int y1, int x2, int y2, int OwnerID) 
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.OwnerID = OwnerID;
	}public Selection(int x1, int y1, int x2, int y2, int OwnerID, boolean b) 
	{
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		selecting = b;
		this.OwnerID = OwnerID;
	}
	public void update()
	{
		if(selecting)
		{
			if(!Mouse.left&&!firstCornerSelected)
			{
				this.x1 = (int)Picker.pick.x;
				this.y1 = (int)Picker.pick.y;
				this.x2 = (int)Picker.pick.x+1;
				this.y2 = (int)Picker.pick.y+1;
			}
			if(Mouse.left&&!firstCornerSelected)
			{
				this.x1 = (int)Picker.pick.x;
				this.y1 = (int)Picker.pick.y;
				firstCornerSelected = true;
			}
			if(firstCornerSelected)
			{
				if(!Mouse.left)
				{
					selecting = false;
					selected = true;
					Simulation.map.entities.get(OwnerID).getTasks().selectingTask=-1;
				}
				this.x2 = (int)Picker.pick.x+1;
				this.y2 = (int)Picker.pick.y+1;
//				if(this.x2<this.x1)
//				{
//					int X1 = this.x1;
//					this.x1 = this.x2;
//					this.x2 = X1;
//				}if(this.y2<this.y1)
//				{
//					int Y1 = this.y1;
//					this.y1 = this.y2;
//					this.y2 = Y1;
//				}
			}
		}
	}
	public void render(Graphics2D g, Color c)
	{
		float size = Simulation.map.t.size;
		float X1 = ((Simulation.map.t.getX()+x1)*size);
		float Y1= ((Simulation.map.t.getY()+y1)*size);
		int isoX1 = (int)(X1 - Y1);
		int isoY1 = (int)((X1 + Y1) / 2f);
		float X2 = ((Simulation.map.t.getX()+x1)*size);
		float Y2 = ((Simulation.map.t.getY()+y2)*size);
		int isoX2 = (int)(X2 - Y2);
		int isoY2 = (int)((X2 + Y2) / 2f);
		float X3 = ((Simulation.map.t.getX()+x2)*size);
		float Y3= ((Simulation.map.t.getY()+y2)*size);
		int isoX3 = (int)(X3 - Y3);
		int isoY3 = (int)((X3 + Y3) / 2f);
		float X4 = ((Simulation.map.t.getX()+x2)*size);
		float Y4 = ((Simulation.map.t.getY()+y1)*size);
		int isoX4 = (int)(X4 - Y4);
		int isoY4 = (int)((X4 + Y4) / 2f);
		g.setColor(new Color(255,255,255,100));
		Polygon p = new Polygon();
		p.addPoint(isoX1+51, isoY1);
		p.addPoint(isoX2+51, isoY2);
		p.addPoint(isoX3+51, isoY3);
		p.addPoint(isoX4+51, isoY4);
		g.fillPolygon(p);
		g.setColor(c);
		g.drawPolygon(p);	
	}
	public void render(Graphics2D g)
	{
		float size = Simulation.map.t.size;
		float X1 = ((Simulation.map.t.getX()+x1)*size);
		float Y1= ((Simulation.map.t.getY()+y1)*size);
		int isoX1 = (int)(X1 - Y1);
		int isoY1 = (int)((X1 + Y1) / 2f);
		float X2 = ((Simulation.map.t.getX()+x1)*size);
		float Y2 = ((Simulation.map.t.getY()+y2)*size);
		int isoX2 = (int)(X2 - Y2);
		int isoY2 = (int)((X2 + Y2) / 2f);
		float X3 = ((Simulation.map.t.getX()+x2)*size);
		float Y3= ((Simulation.map.t.getY()+y2)*size);
		int isoX3 = (int)(X3 - Y3);
		int isoY3 = (int)((X3 + Y3) / 2f);
		float X4 = ((Simulation.map.t.getX()+x2)*size);
		float Y4 = ((Simulation.map.t.getY()+y1)*size);
		int isoX4 = (int)(X4 - Y4);
		int isoY4 = (int)((X4 + Y4) / 2f);
		g.setColor(new Color(255,255,255,100));
		Polygon p = new Polygon();
		p.addPoint(isoX1+51, isoY1);
		p.addPoint(isoX2+51, isoY2);
		p.addPoint(isoX3+51, isoY3);
		p.addPoint(isoX4+51, isoY4);
		g.fillPolygon(p);
		g.setColor(Color.RED);
		g.drawPolygon(p);	
	}
}
