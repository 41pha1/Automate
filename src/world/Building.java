package world;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;

import game.Simulation;
import input.Mouse;
import input.Picker;
import texture.TextureLoader;

public class Building implements Serializable
{
	private static final long serialVersionUID = -9186169803763332236L;
	public int x, y;
	public int dir;
	public boolean built = false;
	public static boolean MouseOver = false;

	public Building()
	{

	}

	public boolean compare(Building b2)
	{
		Building b1 = this;
		return b1.built == b2.built && b1.x == b2.x && b1.y == b2.y && b1.getID() == b2.getID() && b1.dir == b2.dir
				&& b1.getCurvature() == b2.getCurvature();
	}

	public int getMaterials()
	{
		return 0;
	}

	public void rotate()
	{
		dir++;
		if (dir >= 4)
			dir = 0;
	}

	public Building(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public Building(int x, int y, int dir)
	{
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public boolean accepts(int ID)
	{
		return false;
	}

	public Cargo getCargo()
	{
		return new Cargo(0, 0, 0);
	}

	public Cargo takeCargo()
	{
		return new Cargo(0, 0, 0);
	}

	public int getID()
	{
		return -1;
	}

	public int getConnections()
	{
		return 0;
	}

	public void updateCurve()
	{

	}

	public void setCargo(Cargo c)
	{

	}

	public int[] getInventory()
	{
		return new int[10];
	}

	public void changeCurvature()
	{

	}

	public int getChopped()
	{
		return 1000;
	}

	public void chop()
	{

	}

	public void setUC(boolean uc)
	{

	}

	public void update()
	{

	}

	public void renderCargo(Graphics2D g)
	{

	}

	public String getName()
	{
		return "None";
	}

	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		return price;
	}

	public void renderInterface(Graphics2D g)
	{
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		int ix = (int) (x - y);
		int iy = (int) ((x + y) / 2f);
		int posx = ix + 90;
		int posy = iy - 75;
		g.setColor(new Color(0, 0, 0, 200));
		g.fillRect(posx, posy, 400, 40);
		MouseOver = Mouse.x > posx && Mouse.x < posx + 400 && Mouse.y > posy && Mouse.y < posy + 190;
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.PLAIN, 18));
		g.drawString("" + getName(), posx + 10, posy + 27);
		g.setColor(new Color(0, 0, 0, 150));
		g.fillRect(posx, posy + 40, 400, 150);
		renderBody(g, posx, posy + 40);
	}

	public void renderBody(Graphics2D g, int posx, int posy)
	{

	}

	public boolean isSelected(float d)
	{
		return (Math.abs(Picker.pick.x - x) < d) && (Math.abs(Picker.pick.y - y) < d);
	}

	public void updateDirSchematic()
	{

	}

	public void updateInSchematic()
	{

	}

	public void render(Graphics2D g)
	{
		int size = (int) Simulation.map.t.getSize();
		float xp = (Simulation.map.t.getX() + x) * size;
		float yp = (Simulation.map.t.getY() + y) * size;
		float ix = (int) (xp - yp);
		float iy = (int) ((xp + yp) / 2f);
		render(g, (int) ix, (int) iy);
	}

	public int getCurvature()
	{
		return 0;
	}

	public void setCurvature(int c)
	{

	}

	@Override
	public Building clone()
	{
		Building clone;
		switch (this.getID())
		{
		case 0:
			clone = new Mine(x, y);
			break;
		case 1:
			clone = new RoboArm(x, y);
			clone.dir = dir;
			break;
		case 2:
			clone = new ConveyorBelt(x, y);
			clone.dir = dir;
			clone.setCurvature(this.getCurvature());
			break;
		case 3:
			clone = new Smeltery(x, y);
			break;
		case 4:
			clone = new Quarry(x, y);
			break;
		case 5:
			clone = new Rocket(x, y);
			break;
		case 6:
			clone = new Tree(x, y);
			break;
		case 7:
			clone = new Rock(x, y);
			break;
		case 8:
			clone = new Factory(x, y);
			break;
		case 9:
			clone = new Generator(x, y);
			break;
		default:
			clone = new Building(x, y);
		}
		return clone;
	}

	public void setID()
	{

	}

	public void render(Graphics2D g, int x, int y, int size)
	{
		g.drawImage(TextureLoader.getTexture(this), x, y, size, size, null);
	}

	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(TextureLoader.getTexture(this), x - 25, y - 55, null);
	}

	public void renderHighlighted(Graphics2D g)
	{
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
		render(g);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
}
