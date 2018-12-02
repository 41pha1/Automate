package world;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;

import data.Tank;
import game.Simulation;
import gui.Gui;
import texture.TextureLoader;

public class Generator extends Building
{
	private static final long serialVersionUID = 934756892734658723l;
	public static final int ID = 9;
	public static BufferedImage body;
	public Tank H, O, H2O;
	public float Energy;
	public static final float EnergyPerReaktion = 10;
	public static final float TimePerReaktion = 10;
	public float TimeRemaining;

	public Generator(int x, int y)
	{
		TimeRemaining = TimePerReaktion;
		H = new Tank(0, 0, 1000);
		O = new Tank(1, 0, 1000);
		H2O = new Tank(2, 0, 1000);
		this.x = x;
		this.y = y;
		this.built = true;
	}

	public Generator()
	{

	}

	@Override
	public void place()
	{
		Simulation.map.getTile(x, y).updatePipes();
	}

	@Override
	public boolean connectedToPipes()
	{
		return true;
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public void update()
	{
		if (O.amount < O.capacity || H.amount < H.capacity)
			checkForConnectedPipes();
		TimeRemaining--;
		if (TimeRemaining <= 0)
		{
			TimeRemaining = TimePerReaktion;
			if (O.amount >= 2 && H.amount >= 1 && H2O.capacity - H2O.amount >= 1)
			{
				O.amount -= 2;
				H.amount -= 1;
				H2O.amount++;
				Energy += EnergyPerReaktion;
			}
		}
	}

	public void checkForConnectedPipes()
	{
		if (piped(x, y))
		{
			HashSet<Tile> usedTiles = new HashSet<Tile>();
			ArrayList<Tile> connectedTiles = new ArrayList<Tile>();
			connectedTiles.add(Simulation.map.getTile(x, y));
			usedTiles.add(Simulation.map.getTile(x, y));

			for (int i = 0; i < connectedTiles.size(); i++)
			{
				int x = connectedTiles.get(i).x;
				int y = connectedTiles.get(i).y;

				if (checkTile(Simulation.map.getNextTile(x, y, 0, 1), usedTiles, connectedTiles))
					return;
				if (checkTile(Simulation.map.getNextTile(x, y, 1, 1), usedTiles, connectedTiles))
					return;
				if (checkTile(Simulation.map.getNextTile(x, y, 2, 1), usedTiles, connectedTiles))
					return;
				if (checkTile(Simulation.map.getNextTile(x, y, 3, 1), usedTiles, connectedTiles))
					return;
			}
		}
	}

	public boolean piped(Tile t)
	{
		return piped(t.x, t.y);
	}

	public boolean piped(int x, int y)
	{
		return Simulation.map.getTile(x, y).isPiped() && !Simulation.map.schematic.pipesToBuild[x][y];
	}

	public boolean checkTile(Tile t, HashSet<Tile> usedTiles, ArrayList<Tile> connectedTiles)
	{
		if (!usedTiles.contains(t) && piped(t))
		{
			if (checkForAvailableGas(t))
				return true;
			connectedTiles.add(t);
			usedTiles.add(t);
		}
		return false;
	}

	public boolean checkForAvailableGas(Tile connected)
	{
		float pipeSpeed = 1;
		boolean gasFound = false;
		if (H.amount < H.capacity - pipeSpeed)
		{
			float received = connected.b.getGas(0, pipeSpeed);
			if (received > 0)
			{
				H.amount += received;
				H.update();
				gasFound = true;
			}
		}

		if (O.amount < O.capacity - pipeSpeed)
		{
			float received = connected.b.getGas(1, pipeSpeed);
			if (received > 0)
			{
				O.amount += received;
				O.update();
				gasFound = true;
			}
		}
		return gasFound;
	}

	public void renderMainGui(Graphics2D g)
	{
		H.render(g, 10, 10);
		O.render(g, 70, 10);
		g.setColor(Color.WHITE);
		g.drawImage(TextureLoader.icons[7], 130, 60, 32, 32, null);
		H2O.render(g, 170, 10);
	}

	@Override
	public void renderBody(Graphics2D g, int posx, int posy)
	{
		body = new BufferedImage(Gui.width, Gui.height - 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = body.createGraphics();
		b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		b.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		renderMainGui(b);
		g.drawImage(body, posx, posy, null);
	}

	@Override
	public int[] getPrice()
	{
		int[] price = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < price.length; j++)
		{
			price[j] = 0;
		}
		price[Cargo.IRON] = 6;
		price[Cargo.MOTOR] = 1;
		price[Cargo.CABLE] = 2;
		price[Cargo.PIPE] = 2;
		return price;
	}

	@Override
	public void render(Graphics2D g, int x, int y)
	{
		g.drawImage(TextureLoader.getTexture(this), x - 73, y - 65, null);
	}

	@Override
	public void render(Graphics2D g, int x, int y, int size)
	{
		g.drawImage(TextureLoader.getTexture(this), (int) (x - size / 2f), y, size * 2, size, null);
	}

	@Override
	public String getName()
	{
		return "Generator";
	}
}
