package game;

import javax.swing.JOptionPane;

import animation.Animator;
import data.LoadSave;
import gui.Console;
import gui.Gui;
import gui.Store;
import input.Keyboard;
import input.Mouse;
import input.Picker;
import utility.Vector2D;
import world.Building;
import world.Entity;
import world.Map;

public class Simulation
{
	public static Map map;
	public static ParticleManager pm;
	public static int intro;
	public static Vector2D draggedFrom;
	public static String currentSave = "save001";

	public Simulation()
	{
		int i = JOptionPane.showConfirmDialog(null, "Do you want to load a save?");
		switch (i)
		{
		case 0:
			map = LoadSave.loadMap(currentSave);
			break;
		case 1:
			map = new Map(1000, 1000);
			break;
		case 2:
			System.exit(0);
		}
		pm = new ParticleManager();
		intro = 0;
	}

	public void update(float delta)
	{
		Console.update();
		intro = Math.max((int) (map.framesSinceGeneration / 5) - 50, 0);
		move();
		Simulation.map.t.update();
		Picker.update();
		Gui.MouseOver = false;
		map.update();
		pm.update();
		map.updateTasks();
		Animator.update();
		if (map.isEntityBuilding())
		{
			build();
			Simulation.map.schematic.update();
		} else
		{
			select();
			if (!map.entityIsSelecting())
			{
				if (Mouse.left && Picker.isOverMap)
				{
					if ((int) map.selectedBuilding.x == (int) Picker.pick.x
							&& (int) map.selectedBuilding.y == (int) Picker.pick.y)
					{
						Mouse.left = false;
						map.selectedBuilding = new Vector2D(-1, -1);
					} else
					{
						if (map.getTile(Picker.pick).b.built)
						{
							if (map.selectedEntity != -1)
								map.selectedEntity = -1;
							map.selectedBuilding = Picker.pick;
							Mouse.left = false;
						}
					}
				}
			}
		}
		if (Keyboard.esc)
		{
			Keyboard.esc = false;
			if (!map.isEntityBuilding())
			{
				map.selectedEntity = -1;
				map.selectedBuilding = new Vector2D(-1, -1);
			} else
			{
				map.entities.get(map.selectedEntity).getTasks().tasks
						.get(map.entities.get(map.selectedEntity).getTasks().selectedTask).building = false;
				Store.cs.get(Store.selectedCategory).selected = -1;
			}
		}
	}

	public void select()
	{
		if (Mouse.left && Picker.isOverEntity && !map.entityIsSelecting())
		{
			int closest = 1;
			float closestDis = 1f;
			int i = 0;
			for (Entity entity : map.entities)
			{
				float dis = entity.getDisToMouse();
				if (dis < closestDis)
				{
					closest = i;
					closestDis = dis;
				}
				i++;
			}
			if (map.selectedEntity != -1)
			{
				map.selectedEntity = -1;
			} else
			{
				map.selectedEntity = closest;
				map.selectedBuilding = new Vector2D(-1, -1);
			}
			Mouse.left = false;
		}
	}

	public void move()
	{
		if (Simulation.map.selectedEntity != -1 && Keyboard.space)
		{
			Mouse.middle = false;
			Simulation.map.t.wx = -(Simulation.map.entities.get(Simulation.map.selectedEntity).x - 9);
			Simulation.map.t.wy = -(Simulation.map.entities.get(Simulation.map.selectedEntity).y - 4);
		}
		if (Simulation.map.selectedBuilding.x != -1 && Keyboard.space)
		{
			Mouse.middle = false;
			Simulation.map.t.wx = -(Simulation.map.selectedBuilding.x - 10);
			Simulation.map.t.wy = -(Simulation.map.selectedBuilding.y - 4);
		}
		float moveSpeed = 0.1f;
		if (Mouse.middle)
		{
			if (draggedFrom == null)
			{
				draggedFrom = new Vector2D(Picker.pick.x, Picker.pick.y);
			}
			Simulation.map.t.wx -= draggedFrom.x - Picker.pick.x;
			Simulation.map.t.wy -= draggedFrom.y - Picker.pick.y;
		} else
		{
			draggedFrom = null;
		}
		if (Keyboard.left)
		{
			Simulation.map.t.wx += moveSpeed;
			Simulation.map.t.wy -= moveSpeed;
		}
		if (Keyboard.right)
		{
			Simulation.map.t.wx -= moveSpeed;
			Simulation.map.t.wy += moveSpeed;
		}
		if (Keyboard.up)
		{
			Simulation.map.t.wx += moveSpeed;
			Simulation.map.t.wy += moveSpeed;
		}
		if (Keyboard.down)
		{
			Simulation.map.t.wx -= moveSpeed;
			Simulation.map.t.wy -= moveSpeed;
		}
	}

	public void build()
	{
		if (Mouse.left)
		{
			Vector2D pos = Picker.pick;
			if (Store.selectedCategory != -1 && Store.cs.get(Store.selectedCategory).selected != -1
					&& Store.cs.get(Store.selectedCategory).slots.get(Store.cs.get(Store.selectedCategory).selected).b
							.getID() == 10) // PIPE SELECTED
			{
				if (Picker.isOverMap())
					Simulation.map.getTile(pos).piped = true;
			} else
			{
				if (Picker.isOverMap() && !map.getTile((int) pos.x, (int) pos.y).b.built
						&& !Simulation.map.schematic.getBuilding((int) pos.x, (int) pos.y).built)
					Store.place(pos);
			}
		}
		if (Keyboard.r)
		{
			Keyboard.r = false;
			Vector2D pos = Picker.pick;
			if (Picker.isOverMap() && Simulation.map.schematic.getBuilding((int) pos.x, (int) pos.y).built)
				Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y].rotate();
		}
		if (Keyboard.x)
		{
			Keyboard.x = false;
			Vector2D pos = Picker.pick;
			if (Picker.isOverMap())
			{
				if (Simulation.map.schematic.getBuilding((int) pos.x, (int) pos.y).built)
					Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y] = new Building((int) pos.x,
							(int) pos.y);
				else if (Simulation.map.tiles[(int) pos.x][(int) pos.y].b.built)
					Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y] = Simulation.map.tiles[(int) pos.x][(int) pos.y].b
							.clone();
			}

		}
		if (Keyboard.c)
		{
			Keyboard.c = false;
			Vector2D pos = Picker.pick;
			if (Picker.isOverMap() && Simulation.map.schematic.getBuilding((int) pos.x, (int) pos.y).built)
				Simulation.map.schematic.buildings[(int) pos.x][(int) pos.y].changeCurvature();
		}
	}
}