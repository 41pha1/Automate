package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import display.Frame;
import game.Simulation;
import utility.Vector2D;
import world.Building;

public class Category
{
	public int id;
	public int x, y;
	public int size;
	public int selected;
	public String name;
	public int slotUnderMouse = -1;
	public int nextSlotToUse = 0;
	public ArrayList<Slot> slots;

	public Category(int id, int size, String name)
	{
		this.size = size;
		this.id = id;
		this.name = name;
		selected = -1;
		slots = new ArrayList<Slot>();
	}

	public void update()
	{
		slotUnderMouse = -1;
		int i = 0;
		for (Slot s : slots)
		{
			s.update();
			if (s.MouseOver)
				slotUnderMouse = i;
			i++;
		}
	}

	public Slot getSlotUnderMouse()
	{
		if (slotUnderMouse != -1)
			return slots.get(slotUnderMouse);
		return null;
	}

	public void render(Graphics2D g)
	{
		for (Slot s : slots)
		{
			float xp = x + s.b.x * size;
			float yp = y + s.b.y * size;
			s.b.render(g, (int) xp, (int) yp, size);
		}
		g.setColor(Color.WHITE);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(new Font("Serial", Font.PLAIN, 20));
		int width = g.getFontMetrics().stringWidth(name);
		int height = g.getFontMetrics().getHeight();
		g.drawString(name, Frame.width / 2 - width / 2, Frame.height - size - height / 2);
		if (selected != -1)
			g.drawRect(x + selected * size, y, size - 1, size - 4);
	}

	@SuppressWarnings("rawtypes")
	public void addItem(Building b)
	{
		Class[] c = new Class[2];
		c[0] = int.class;
		c[1] = int.class;
		int x = nextSlotToUse;
		int y = 0;

		try
		{
			slots.add(new Slot(b.getClass().getDeclaredConstructor(c).newInstance(x, y), nextSlotToUse, x, y, 100));
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		nextSlotToUse++;
	}

	public boolean MouseOverAnySlot()
	{
		for (Slot slot : slots)
		{
			if (slot.MouseOver())
				return true;
		}
		return false;
	}

	public void positionSlots()
	{
		for (Slot slot : slots)
		{
			float xp = x + slot.b.x * size;
			float yp = y + slot.b.y * size;
			slot.x1 = (int) xp;
			slot.y1 = (int) yp;
			slot.x2 = (int) xp + 100;
			slot.y2 = (int) yp + 100;
		}
	}

	public void calculateDims()
	{
		int width = size * slots.size();
		x = Frame.width / 2 - width / 2;
		y = Frame.height - size;
	}

	public void rotate()
	{
		slots.get(selected).b.rotate();
	}

	@SuppressWarnings("rawtypes")
	public void place(Vector2D pos)
	{
		if (selected != -1)
		{
			Class[] c = new Class[2];
			c[0] = int.class;
			c[1] = int.class;
			int x = (int) pos.x;
			int y = (int) pos.y;
			try
			{
				Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y] = slots.get(selected).b.getClass()
						.getDeclaredConstructor(c).newInstance(x, y);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | InstantiationException e)
			{
				e.printStackTrace();
			}
			Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y].updateDirSchematic();
		}
	}
}
