package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import display.Frame;
import launcher.Main;

public class MenuBar
{
	public static Button layers;
	public static boolean showPipes;

	public MenuBar()
	{
		layers = new Button(130, Frame.undecorated ? 3 : 33, 0, 0, 32, 32, 8);
		layers.showBorder = false;
		layers.showOverlay = false;
		showPipes = false;
	}

	public static void showFPS(Graphics2D g, int x, int y)
	{
		g.setColor(Color.WHITE);
		Font font = new Font("Serial", Font.PLAIN, 30);
		g.setFont(font);
		g.drawString("" + Main.fps, x, y);
	}

	public static void render(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int y = 0;
		if (!Frame.undecorated)
			y += 30;
		int width = Frame.width;
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRoundRect(100, y - 40, width - 200, 80, 80, 80);
		layers.render(g);
		if (layers.isPressed())
		{
			showPipes = !showPipes;
		}
		showFPS(g, width - 160, y + 30);
	}
}
