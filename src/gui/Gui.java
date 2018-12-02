package gui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Simulation;
import input.Mouse;
import utility.Vector2D;
import world.Cargo;
import world.Human;

public class Gui
{
	public static Button addTask, inventory, tasks;
	public static boolean dropDown = false;
	public static int width, height;
	public static DropDownMenu menu;
	public static ScrollBar sb;
	public static BufferedImage body;

	public static boolean MouseOver = false;

	public Gui(int width, int height)
	{
		Gui.width = width;
		Gui.height = height;
		body = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		addTask = new Button(0, 0, 0, 0, 100, 30, "Add Task");
		inventory = new Button(0, 0, 0, 0, 100, 40, "Inventory");
		inventory.stayPressed = true;
		inventory.showBorder = false;
		tasks = new Button(0, 0, 0, 0, 100, 40, "Tasks");
		tasks.stayPressed = true;
		tasks.pressed = true;
		tasks.showBorder = false;
		sb = new ScrollBar(0, 0, 12, 120);
		menu = new DropDownMenu(150, 40);
		menu.add("Chop Wood");
		menu.add("Pick up");
		menu.add("Build");
		menu.add("Move");
		menu.add("Destroy");
		menu.add("Work");
	}

	public static void showDropDown(Graphics2D g, int x, int y, int dx, int dy)
	{
		menu.update(x, y, dx, dy);
		menu.render(g);
		if (menu.delete.pressed)
			dropDown = false;
		if (menu.items.get(0).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(0, "Chop Wood");
		} else if (menu.items.get(1).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(1, "Pick up");
		} else if (menu.items.get(2).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(2, "Build");
		} else if (menu.items.get(3).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(3, "Move");
		} else if (menu.items.get(4).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(4, "Destroy");
		} else if (menu.items.get(5).pressed)
		{
			dropDown = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().addTask(5, "Work");
		}
	}

	public static void showInventory(int[] inventory, Graphics2D g, int dx, int dy)
	{
		ArrayList<Vector2D> inv = new ArrayList<Vector2D>();
		for (int i = 0; i < Cargo.DIFFERENTCARGOS; i++)
		{
			if (inventory[i] != 0)
				inv.add(new Vector2D(i, inventory[i]));
		}

		int i = 0;
		for (Vector2D cargo : inv)
		{
			Cargo c = new Cargo(0, 0, (int) (cargo.x));
			int x = dx + 10 + (i % 4) * 50;
			int y = dy + i / 4 * 50;
			c.renderAt(g, x, y, 50, 50);
			g.setColor(new Color(0, 0, 0, 100));
			g.drawRect(x, y, 50, 50);
			g.setColor(Color.white);
			g.setFont(new Font("Serial", Font.PLAIN, 12));
			String amount = "" + (int) cargo.y;
			int width = g.getFontMetrics().stringWidth(amount);
			g.drawString(amount, x + 50 - width, y + 50);
			g.setColor(new Color(0, 0, 0, 100));
			i++;
		}
	}

	public static int getLengthOfInventory(int[] inv)
	{
		int j = 0;
		for (int i = 0; i < inv.length; i++)
		{
			if (inv[i] > 0)
				j++;
		}
		return j;
	}

	public static void showGui(Graphics2D g, Human h)
	{
		Vector2D pos = h.getPosToRenderAt();
		g.setColor(new Color(0, 0, 0, 200));
		pos.x += 100;
		Gui.MouseOver = Mouse.x > pos.x && Mouse.x < pos.x + Gui.width && Mouse.y > pos.y
				&& Mouse.y < pos.y + Gui.height;
		g.fillRect((int) pos.x, (int) pos.y, Gui.width, 40);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.BOLD, 16));
		body = new BufferedImage(width, height - 40, BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = body.createGraphics();
		b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		b.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		int yoff = -40;
		if (tasks.pressed)
		{
			yoff -= sb.ca;
			g.drawString("" + h.getName() + "'s Tasks", (int) pos.x + 10, (int) pos.y + 27);
			tasks.x = (int) pos.x + 200;
			tasks.y = (int) pos.y;
			tasks.render(g);
			inventory.x = (int) pos.x + 300;
			inventory.y = (int) pos.y;
			inventory.render(g);
			inventory.update();
			b.setColor(new Color(0, 0, 0, 150));
			b.fillRect(0, 0, width, height - 40);
			if (Simulation.map.selectedEntity != -1)
				sb.update((Gui.dropDown ? (menu.items.size() * 50) : 0)
						+ Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().tasks.size() * 50, 380,
						15);
			sb.render(b);
			if (inventory.pressed)
				tasks.pressed = false;
			Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().render(b, 20, 50 + yoff, (int) pos.x,
					(int) pos.y + 40);
			if (Gui.dropDown)
			{
				Gui.showDropDown(b, 20, 50 + yoff
						+ Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().tasks.size() * 50,
						(int) pos.x, (int) pos.y + 40);
			} else
			{
				addTask.x = 20;
				addTask.y = 50 + yoff
						+ Simulation.map.entities.get(Simulation.map.selectedEntity).getTasks().tasks.size() * 50;
				addTask.dx = (int) pos.x;
				addTask.dy = (int) pos.y + 40;
				addTask.render(b);
				addTask.update();
				if (addTask.pressed)
					Gui.dropDown = true;
			}
		} else
		{
			yoff -= sb.ca;
			g.drawString("" + h.getName() + "'s Inventory", (int) pos.x + 10, (int) pos.y + 27);
			inventory.x = (int) pos.x + 200;
			inventory.y = (int) pos.y;
			inventory.render(g);
			tasks.x = (int) pos.x + 300;
			tasks.y = (int) pos.y;
			tasks.render(g);
			tasks.update();
			b.setColor(new Color(0, 0, 0, 150));
			b.fillRect(0, 0, width, height - 40);
			sb.update((Gui.getLengthOfInventory(h.getInventory()) / 4) * 50, 380, 15);
			sb.render(b);
			if (tasks.pressed)
				inventory.pressed = false;
			Gui.showInventory(h.getInventory(), b, 0, 50 + yoff);
			b.setColor(Color.WHITE);
			b.drawLine(235, 10, 235, 140);
			h.O.render(b, 260, 10);
		}
		g.drawImage(body, (int) pos.x, (int) pos.y + 40, null);
	}
}
