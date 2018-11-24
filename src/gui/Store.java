package gui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

import display.Frame;
import game.Simulation;
import input.Mouse;
import utility.Vector2D;
import world.Building;
import world.ConveyorBelt;
import world.Factory;
import world.Mine;
import world.RoboArm;
import world.Smeltery;

public class Store
{
	public static ArrayList<Category> cs;
	public static int selectedCategory = 1;
	static Button left, right, exit, save;
	public static final int slotSize = 100;

	public Store()
	{
		cs = new ArrayList<Category>();
		addCategory(new Building[] { new Mine(), new Smeltery() }, "Resources");
		addCategory(new Building[] { new RoboArm(), new ConveyorBelt() }, "Transportation");
		addCategory(new Building[] { new Factory() }, "Production");
		addButtons();
	}

	public void addButtons()
	{
		left = new Button(Frame.width / 2 - 120, Frame.height - slotSize - 40, 0, 0, 40, 40, 5);
		left.showBorder = false;
		left.showOverlay = false;
		right = new Button(Frame.width / 2 + 80, Frame.height - slotSize - 40, 0, 0, 40, 40, 4);
		right.showBorder = false;
		right.showOverlay = false;
		exit = new Button(Frame.width - 40, Frame.height - slotSize - 40, 0, 0, 40, 40, 0);
		exit.showBorder = false;
		exit.showOverlay = false;
		save = new Button(Frame.width - 80, Frame.height - slotSize - 40, 0, 0, 40, 40, 6);
		save.showBorder = false;
		save.showOverlay = false;
	}

	public void addCategory(Building[] elements, String name)
	{
		cs.add(new Category(cs.size(), 100, name));
		for (Building e : elements)
		{
			cs.get(cs.size() - 1).addItem(e);
		}
		cs.get(cs.size() - 1).calculateDims();
		cs.get(cs.size() - 1).positionSlots();
	}

	public static void render(Graphics2D g)
	{
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(0, Frame.height - 100, Frame.width, 100);
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(0, Frame.height - 140, Frame.width, 40);
		cs.get(selectedCategory).render(g);
		left.render(g);
		right.render(g);
		exit.render(g);
		save.render(g);
		Slot s = cs.get(selectedCategory).getSlotUnderMouse();
		if (s != null)
			InfoTag.render(g, s.b, Frame.width - InfoTag.width, Frame.height - InfoTag.height);
		else if (cs.get(selectedCategory).selected != -1)
			InfoTag.render(g, cs.get(selectedCategory).slots.get(cs.get(selectedCategory).selected).b,
					Frame.width - InfoTag.width, Frame.height - InfoTag.height);
	}

	public static void rotate()
	{
		cs.get(selectedCategory).rotate();
	}

	public static void place(Vector2D pos)
	{
		cs.get(selectedCategory).place(pos);
	}

	public static boolean MouseOverAnySlot()
	{
		return cs.get(selectedCategory).MouseOverAnySlot() || Mouse.y > Frame.height - slotSize - 40;
	}

	public static void update()
	{
		left.update();
		if (left.pressed)
		{
			if (selectedCategory < cs.size() - 1)
				selectedCategory++;
			else
				selectedCategory = 0;
		}
		right.update();
		if (right.pressed)
		{
			if (selectedCategory > 0)
				selectedCategory--;
			else
				selectedCategory = cs.size() - 1;
		}
		save.update();
		if (save.pressed)
		{
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().tasks.get(Simulation.map.entities
					.get(Simulation.map.selectedEntity).getTasks().selectedTask).building = false;
			Simulation.map.schematic.save();
		}
		exit.update();
		if (exit.pressed)
		{
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().tasks.get(Simulation.map.entities
					.get(Simulation.map.selectedEntity).getTasks().selectedTask).building = false;
			Simulation.map.schematic.discard();
		}
		cs.get(selectedCategory).update();
	}
}
