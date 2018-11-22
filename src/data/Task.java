package data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.Serializable;

import game.Simulation;
import gui.Button;
import gui.Store;
import input.Mouse;
import input.Picker;
import utility.Vector2D;

public class Task implements Serializable
{
	private static final long serialVersionUID = 5265353177327870404L;
	public int ID;
	public float priority;
	public String name;
	public boolean hasSelection = true, hasDestination = false, selectingDestination = false, hasBuild = false,
			building = false;
	public Selection s;
	public Vector2D des;
	public int OwnerID;
	public int id;
	Button delete;
	Button select;
	Button destination;
	Button build;

	public Task(int ID, float priority, String name, int OwnerID, int id)
	{
		des = null;
		this.ID = ID;
		if (ID == 1 || ID == 3)
			hasDestination = true;
		if (ID == 2 || ID == 3)
			hasSelection = false;
		if (ID == 2)
			hasBuild = true;
		delete = new Button(0, 0, 0, 0, 30, 30, 0);
		select = new Button(0, 0, 0, 0, 30, 30, 1);
		destination = new Button(0, 0, 0, 0, 30, 30, 2);
		build = new Button(0, 0, 0, 0, 30, 30, 3);
		this.OwnerID = OwnerID;
		this.id = id;
		s = new Selection(0, 0, 0, 0, OwnerID, true);
		this.priority = priority;
		this.name = name;
	}

	public void render(Graphics2D g, int x, int y, int dx, int dy)
	{
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(x, y, 350, 40);
		if (Simulation.map.entities.get(OwnerID).getTasks().selectedTask == id)
		{
			g.setColor(new Color(255, 0, 0, 150));
			g.drawRect(x, y, 350, 40);
		}
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.PLAIN, 16));
		g.drawString(name, x + 10, y + 25);
		delete.x = x + 300;
		delete.y = y + 5;
		delete.dx = dx;
		delete.dy = dy;
		delete.render(g);
		if (hasBuild)
		{
			build.x = x + 260;
			build.y = y + 5;
			build.dx = dx;
			build.dy = dy;
			build.render(g);
		}
		if (hasDestination)
		{
			destination.x = x + 260 - (hasSelection ? 40 : 0);
			destination.y = y + 5;
			destination.dx = dx;
			destination.dy = dy;
			destination.render(g);
		}
		if (hasSelection)
		{
			select.x = x + 260;
			select.y = y + 5;
			select.dx = dx;
			select.dy = dy;
			select.render(g);
		}
	}

	public void update(int id)
	{
		this.id = id;
		delete.update();
		if (delete.pressed)
		{
			// if(Simulation.map.entities.get(OwnerID).getTasks().selectingTask==id)
			Simulation.map.entities.get(OwnerID).getTasks().selectingTask = -1;
			Simulation.map.entities.get(OwnerID).getTasks().selectedTask = -1;
			Simulation.map.entities.get(OwnerID).getTasks().tasks.remove(id);
			Simulation.map.entities.get(OwnerID).setActive(false);
		}
		if (hasBuild)
		{
			build.update();
			if (build.pressed)
			{
				if (Simulation.map.entities.get(OwnerID).getTasks().selectedTask == id)
				{
					Store.cs.get(Store.selectedCategory).selected = -1;
					building = !building;
					Simulation.map.schematic.saved = false;
					Simulation.map.schematic.load();
				} else
				{
					Simulation.map.entities.get(OwnerID).getTasks().selectedTask = id;
				}
			}
		}
		if (hasDestination)
		{
			if (selectingDestination)
			{
				des = Picker.pick;
				if (Mouse.left)
					selectingDestination = false;
			}
			destination.update();
			if (destination.pressed)
			{
				if (Simulation.map.entities.get(OwnerID).getTasks().selectedTask == id)
				{
					selectingDestination = true;
					des = new Vector2D(0, 0);
					Simulation.map.entities.get(OwnerID).getTasks().selectingTask = id;
				} else
				{
					Simulation.map.entities.get(OwnerID).getTasks().selectedTask = id;
				}
			}
		}
		if (hasSelection)
		{
			select.update();
			if (select.pressed)
			{
				if (Simulation.map.entities.get(OwnerID).getTasks().selectedTask == id)
				{
					Simulation.map.entities.get(OwnerID).getTasks().selectingTask = id;
					s = new Selection(0, 0, 0, 0, OwnerID, true);
				} else
				{
					Simulation.map.entities.get(OwnerID).getTasks().selectedTask = id;
				}
			}
		}
	}
}
