package input;

import game.Simulation;
import gui.Gui;
import gui.Store;
import utility.Vector2D;
import world.Building;

public class Picker 
{
	public static Vector2D pick = new Vector2D(0,0);
	public static boolean isOverMap, isOverEntity;
	public static void update()
	{
		float mx = Mouse.x;
		float my = Mouse.y;
		float x = (int) ((2 * my + mx) / 2)-Simulation.map.t.getSize()/2;
		float y = (int) ((2 * my - mx) / 2)+Simulation.map.t.getSize()/2;
		x /= 1000;
		y /= 1000;
		x *= (1000/Simulation.map.t.getSize());
		y *= (1000/Simulation.map.t.getSize());
		x -= Simulation.map.t.getX();
		y -= Simulation.map.t.getY();
		pick = new Vector2D(x,y);
		isOverEntity = Simulation.map.EntitySelected() && !Building.MouseOver;
		isOverMap = !Store.MouseOverAnySlot() && !isOverEntity && !Gui.MouseOver && !Building.MouseOver;
		Building.MouseOver = false;
	}
	public static boolean isOverMap()
	{
		return isOverMap;
	}
}
