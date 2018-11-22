package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import game.Simulation;
import input.Picker;
import utility.Vector2D;
import world.Tile;

public class InfoBox 
{
	public static BufferedImage render()
	{
		int w = 200, h = 100;
		BufferedImage box = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = box.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);
		g.setColor(Color.BLACK);
		Font f = new Font ("Arial", Font.PLAIN, 16);
		g.setFont(f);
		
		
		g.drawString(buildingInfo(), 10, 20);
		g.drawString(cargo(), 10, 50);
		g.drawString(built(), 10, 80);
		return box;
	}
	public static String buildingInfo()
	{
		Vector2D pos = Picker.pick;
		Tile tile = Simulation.map.getTile((int)pos.x, (int)pos.y);
		String info = "Materials: "+tile.b.getMaterials()+", D: "+tile.b.dir;
		if(!pos.inBounds())info = "";
		return info;
	}public static String cargo()
	{
		Vector2D pos = Picker.pick;
		Tile tile = Simulation.map.getTile((int)pos.x, (int)pos.y);
		String info = "Cargo: "+tile.b.getCargo().getName();
		if(!pos.inBounds())info = "";
		return info;
	}public static String built()
	{
		Vector2D pos = Picker.pick;
		Tile tile = Simulation.map.getTile((int)pos.x, (int)pos.y);
		String info = "Type: "+tile.b.getName()+", X: "+tile.b.x+", Y: "+tile.b.y;
		if(!pos.inBounds())info = "";
		return info;
	}
}
