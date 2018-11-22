package world;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import game.Simulation;
import gui.Button;
import gui.Gui;
import gui.ScrollBar;
import input.Mouse;

public class Mine extends Building
{
	private static final long serialVersionUID = -4970384144897726073L;
	public float miningProgress = 0;
	public float efficency = 1;
	public Cargo mining;
	public static final int ID = 0;
	public static ArrayList<Integer> mineable;
	public ArrayList<Integer> exports;
	public int[] inventory;
	public Cargo c;
	public int selected = -1, newSelection;
	public static BufferedImage body;
	public static ScrollBar sb, isb;
	public static Button save, disband, inv, gen;
	public boolean general = true, miningSelector = false;

	public Mine()
	{
		mineable = new ArrayList<Integer>();
		mineable.add(Cargo.COAL);
		mineable.add(Cargo.IRON_ORE);
		mineable.add(Cargo.COPPER_ORE);
		mineable.add(Cargo.STONE);
		sb = new ScrollBar(0, 0, 12, 120);
		isb = new ScrollBar(0, 0, 12, 120);
		save = new Button(0, 0, 0, 0, 40, 40, 6);
		save.showBorder = false;
		save.showOverlay = false;
		disband = new Button(0, 0, 0, 0, 40, 40, 0);
		disband.showBorder = false;
		disband.showOverlay = false;
		inv = new Button(0, 0, 0, 0, 80, 40, "Inventory");
		inv.showBorder = false;
		inv.FontSize = 16;
		gen = new Button(0, 0, 0, 0, 80, 40, "General");
		gen.showBorder = false;
		gen.FontSize = 16;
	}

	@Override
	public Cargo takeCargo()
	{
		Cargo cargo = new Cargo(0, 0, 0);
		if (exports.size() > 0)
		{
			cargo = new Cargo(0, 0, (exports.get(0)));
			inventory[exports.get(0)]--;
		}
		return cargo;
	}

	public Mine(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.built = true;
		exports = new ArrayList<Integer>();
		c = new Cargo(0, 0, 0);
		mining = new Cargo(0, 0, 0);
		inventory = new int[Cargo.DIFFERENTCARGOS];
		for (int i = 0; i < inventory.length; i++)
		{
			inventory[i] = 0;
		}
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public Cargo getCargo()
	{
		if (exports.size() > 0)
			return new Cargo(0, 0, exports.get(0));
		return new Cargo(0, 0, 0);
	}

	@Override
	public void update()
	{
		createExports();
		if (selected >= 0 && selected < mineable.size())
			mining = new Cargo(0, 0, mineable.get(selected));
		else
			mining = new Cargo(0, 0, 0);
		c.update();
		if (inventory[mining.ID] < 10 && mining.ID != 0)
			miningProgress += Simulation.map.getTile(x, y).r.res[mining.ID] * efficency * 0.001f;
		if (miningProgress > 1)
		{
			miningProgress = 0;
			inventory[mining.ID]++;
		}
	}

	public void renderGeneral(Graphics2D g, Graphics2D g2, int posx, int posy)
	{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.BOLD, 15));
		g.drawString("Currently mining: " + mining.getName(), 10, 20);
		int cx = 250;
		int cy = 10;
		mining.renderAt(g, cx, cy, 128, 128);
		g.setColor(Color.white);
		g.drawRect(cx, cy, 128, 128);
		if (mining.ID > 0)
		{
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(10, 30, 200, 20);
			g.setColor(Color.GREEN);
			g.fillRect(10, 30, (int) (200 * miningProgress), 20);
			g.setColor(Color.WHITE);
			g.drawString("Ground multiplier: " + (int) (Simulation.map.getTile(x, y).r.res[mining.ID] * 50), 10, 80);
			if (inventory[mining.ID] >= 10)
			{
				g.setColor(Color.RED);
				g.drawString("Inventory full", 10, 120);
			}
		} else
		{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(cx + 1, cy + 1, 127, 127);
			g.setColor(Color.WHITE);
			g.drawString("Select", cx + 43, cy + 60);
		}
		inv.update(posx + 320, posy - 40);
		inv.render(g2);
		if (inv.pressed)
		{
			general = false;
		}
		if (Mouse.x > posx + cx && Mouse.x < posx + cx + 128 && Mouse.y > posy + cy && Mouse.y < posy + cy + 128)
		{
			g.setColor(new Color(255, 255, 255, 30));
			g.fillRect(cx, cy, 128, 128);
			if (Mouse.left)
			{
				Mouse.left = false;
				miningSelector = true;
				newSelection = selected;
			}
		}
	}

	public void renderMineables(Graphics2D g, int yoff, int tx, int ty)
	{
		for (int i = 0; i < mineable.size(); i++)
		{
			Cargo c = new Cargo(0, 0, mineable.get(i));
			int x = (i % 4) * 50;
			int y = i / 4 * 50 + yoff;
			c.renderAt(g, x, y, 50, 50);
			if (Mouse.x > x + tx && Mouse.x < x + tx + 50 && Mouse.y > y + ty && Mouse.y < y + ty + 50)
			{
				g.setColor(new Color(255, 255, 255, 30));
				g.fillRect(x, y, 50, 50);
				if (Mouse.left)
				{
					Mouse.left = false;
					if (newSelection != i)
						newSelection = i;
					else
						newSelection = -1;
				}
			}
			if (i != newSelection)
				g.setColor(new Color(0, 0, 0, 100));
			else
				g.setColor(Color.WHITE);
			g.drawRect(x, y, 50, 50);
		}
	}

	public void renderMiningSelector(Graphics2D b, Graphics2D g, int posx, int posy)
	{
		int h = -100 + mineable.size() / 4 * 50;
		if (h < 0)
			h = 0;
		sb.update(h, 380, 15);
		sb.render(b);
		int yoff = -sb.ca;
		renderMineables(b, yoff, posx, posy);
		b.setColor(Color.WHITE);
		if ((newSelection >= 0 && newSelection < mineable.size()))
		{
			b.setFont(new Font("Serial", Font.PLAIN, 18));
			b.drawString(new Cargo(0, 0, mineable.get(newSelection)).getName(), 220, 20);
			b.drawLine(210, 30, 350, 30);
			b.setFont(new Font("Serial", Font.PLAIN, 14));
			b.drawString("Ground multiplier: " + (int) (Simulation.map.getTile(x, y).r.res[newSelection] * 50), 220,
					50);

		}
		save.update(320 + posx, posy - 40);
		save.render(g);
		if (save.pressed)
		{
			miningSelector = false;
			if (selected != newSelection)
				miningProgress = 0;
			selected = newSelection;
		}
		disband.update(360 + posx, posy - 40);
		disband.render(g);
		if (disband.pressed)
		{
			miningSelector = false;
		}
	}

	public void createExports()
	{
		exports.clear();
		for (int i = 0; i < inventory.length; i++)
		{
			if (inventory[i] > 0)
			{
				exports.add(i);
			}
		}
	}

	public void renderInventory(Graphics2D g, Graphics2D g2, int posx, int posy)
	{
		int h = -50 + exports.size() / 7 * 50;
		sb.update(h, 380, 15);
		sb.render(g);
		int yoff = -sb.ca;

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Serial", Font.PLAIN, 18));
		g2.drawString("'s Inventory", posx + 52, posy - 13);

		int i = 0;
		for (int j : exports)
		{
			Cargo c = new Cargo(0, 0, j);
			int x = 10 + (i % 7) * 50;
			int y = yoff - 40 + i / 7 * 50;
			c.renderAt(g, x, y, 50, 50);
			g.setColor(new Color(0, 0, 0, 100));
			g.drawRect(x, y, 50, 50);
			g.setColor(Color.white);
			g.setFont(new Font("Serial", Font.PLAIN, 12));
			g.drawString(inventory[j] + "x", x, y + 50);
			g.setColor(new Color(0, 0, 0, 100));
			i++;
		}

		gen.update(posx + 320, posy - 40);
		gen.render(g2);
		if (gen.pressed)
		{
			general = true;
		}
	}

	@Override
	public void renderBody(Graphics2D g, int posx, int posy)
	{
		body = new BufferedImage(Gui.width, Gui.height - 50, BufferedImage.TYPE_INT_ARGB);
		Graphics2D b = body.createGraphics();
		b.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
		b.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		if (!general)
		{
			renderInventory(b, g, posx, posy);
		} else
		{
			if (miningSelector)
				renderMiningSelector(b, g, posx, posy);
			else
				renderGeneral(b, g, posx, posy);
		}
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
		price[4] = 2;
		price[3] = 10;
		return price;
	}

	@Override
	public String getName()
	{
		return "Mine";
	}
}
