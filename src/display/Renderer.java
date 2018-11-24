package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Map;

import data.Transformation;
import game.Simulation;
import gui.Console;
import gui.Gui;
import gui.Store;
import launcher.Main;
import utility.Counter;

public class Renderer
{
	static int width, height;
	static Gui gui;
	public static Transformation t;

	public Renderer(int width, int height)
	{
		gui = new Gui(400, 200);
		Renderer.width = width;
		Renderer.height = height;
		t = new Transformation(-50, -50, 64);
	}

	public static void render(Graphics2D g)
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		Object desktopHints = (tk.getDesktopProperty("awt.font.desktophints"));
		g.setRenderingHints((Map<?, ?>) desktopHints);
		Counter.count();
		reset(g);
		Simulation.map.Render(g);
		Simulation.pm.render(g);
		// g.drawImage(InfoBox.render(), 750, 50, 200, 100, null);
		renderGuis(g);
		showFPS(g);
		// showRes(g);
	}

	public static void renderGuis(Graphics2D g)
	{
		if (Simulation.map.selectedEntity != -1)
		{
			if (!Simulation.map.isEntityBuilding())
				Simulation.map.entities.get(Simulation.map.selectedEntity).showGui(g);
			if (Simulation.map.isEntityBuilding())
			{
				Store.render(g);
				Store.update();
			}
		}
		if (Simulation.map.getTile(Simulation.map.selectedBuilding).b.getID() != -1)
			Simulation.map.getTile(Simulation.map.selectedBuilding).b.renderInterface(g);
		Console.render(g);
		renderIntro(g);
	}

	public static void renderIntro(Graphics2D g)
	{
		if (Simulation.intro < 100)
		{
			int i = Simulation.intro;
			Simulation.map.t.shake(60, 55, (1 - (i / 100f)) * 2f);
			if (i > 5)
			{
				g.setColor(new Color(0, 0, 0, Math.max(255 - ((i - 5) * 8), 1)));
			} else
				g.setColor(new Color(0, 0, 0, 255));
			g.fillRect(0, 0, Frame.width, Frame.height);
			if (i > 5)
			{
				g.setColor(new Color(255, 255, 255, Math.max(255 - ((i - 5) * 16), 1)));
			} else
				g.setColor(new Color(255, 255, 255, 255));
			g.setFont(new Font("Ethnocentric", Font.PLAIN, 72));
			String text = "MARS, 2100";
			int width = g.getFontMetrics().stringWidth(text);
			int height = g.getFontMetrics().getHeight();
			g.drawString(text, Frame.width / 2 - width / 2, Frame.height / 2 - height / 2);
		}
	}

	public static void showFPS(Graphics2D g)
	{
		g.setColor(Color.GREEN);
		Font font = new Font("Serial", Font.PLAIN, 15);
		g.setFont(font);
		g.drawString("" + Main.fps, 10, 30);
	}

	public static void reset(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
	}
}
