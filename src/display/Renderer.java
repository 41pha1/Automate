package display;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import animation.Animator;
import game.Simulation;
import gui.Console;
import gui.Gui;
import gui.MenuBar;
import gui.Store;
import utility.Counter;

public class Renderer
{
	static int width, height;
	static Gui gui;

	public Renderer(int width, int height)
	{
		gui = new Gui(400, 200);
		new MenuBar();
		Renderer.width = width;
		Renderer.height = height;
		try
		{
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Font font = Font.createFont(Font.TRUETYPE_FONT, new File("res/ethnocentric.ttf"));
			ge.registerFont(font);
		} catch (IOException | FontFormatException e)
		{

		}
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
		renderDayNight(g);
		renderGuis(g);
		if (Simulation.intro > 100)
			MenuBar.render(g);
	}

	public static void renderDayNight(Graphics2D g)
	{
		float brightness = (float) Math.cos(Math.toRadians(Animator.dayNight)) / 3 + 0.5f;
		g.setColor(new Color(0, 0, 0, brightness));
		g.fillRect(0, 0, Frame.width, Frame.height);
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
			g.setFont(new Font("EthnocentricRg-Regular", Font.PLAIN, 72));
			String text = "MARS, 2100";
			int width = g.getFontMetrics().stringWidth(text);
			int height = g.getFontMetrics().getHeight();
			g.drawString(text, Frame.width / 2 - width / 2, Frame.height / 2 - height / 2);
		}
	}

	public static void reset(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
	}
}
