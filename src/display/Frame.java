package display;

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import input.Keyboard;
import input.Mouse;

public class Frame extends JFrame
{
	private static final long serialVersionUID = 1L;
	public static int width, height;
	public static Renderer r;
	public static BufferStrategy bs;
	public static boolean undecorated;

	public Frame(int width, int height, int x, int y)
	{
		this(width, height);
		this.setLocation(x, y);
	}

	public Frame(int width, int height)
	{
		r = new Renderer(width, height);
		this.addKeyListener(new Keyboard());
		this.addMouseListener(new Mouse());
		this.addMouseMotionListener(new Mouse());
		this.addMouseWheelListener(new Mouse());
		Frame.width = width;
		Frame.height = height;
		this.setSize(width, height);
		this.setTitle("Automate");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setDecorated(true);
		this.setLocation(0, 0);
		this.setVisible(true);
		createStrat();
	}

	public void setDecorated(boolean b)
	{
		this.setUndecorated(!b);
		undecorated = !b;
	}

	public void createStrat()
	{
		createBufferStrategy(3);
		bs = getBufferStrategy();
	}

	public void render()
	{
		width = this.getWidth();
		height = this.getHeight();
		Graphics2D fin = (Graphics2D) bs.getDrawGraphics();
		Renderer.render(fin);
		fin.dispose();
		bs.show();
	}
}
