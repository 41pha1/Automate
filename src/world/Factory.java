package world;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import gui.Button;
import gui.Gui;
import gui.ScrollBar;
import input.Mouse;
import utility.Vector2D;

public class Factory extends Building
{
	private static final long serialVersionUID = 4622359395473603431L;
	public static final int ID = 8;
	public static ArrayList<Integer> produceable;
	public static boolean[] locked;
	public int[] inventory;
	public Cargo in, out;
	public ArrayList<Integer> exports;
	public int selected, newSelection;
	public boolean assemblingSelector = false, general = true;
	public static BufferedImage body;
	public static ScrollBar sb, isb;
	public static Button save, disband, inv, gen;
	public float efficency = 1f;
	public float productionProgress;
	public Cargo producing;

	public Factory()
	{
		sb = new ScrollBar(0, 0, 12, 120);
		isb = new ScrollBar(0, 0, 12, 120);
		produceable = new ArrayList<Integer>();
		produceable.add(Cargo.MOTOR);
		produceable.add(Cargo.WIRE);
		produceable.add(Cargo.CABLE);
		produceable.add(Cargo.COIL);
		produceable.add(Cargo.PIPE);
		locked = new boolean[produceable.size()];
		for (int i = 0; i < produceable.size(); i++)
		{
			locked[i] = false;
		}
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

	public void renderProduceables(Graphics2D g, int yoff, int tx, int ty)
	{
		for (int i = 0; i < produceable.size(); i++)
		{
			Cargo c = new Cargo(0, 0, produceable.get(i));
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

	public Factory(int x, int y)
	{
		out = new Cargo(0, 0, 0);
		in = new Cargo(0, 0, 0);
		selected = -1;
		newSelection = -1;
		this.x = x;
		this.y = y;
		exports = new ArrayList<Integer>();
		inventory = new int[Cargo.DIFFERENTCARGOS];
		for (int i = 0; i < inventory.length; i++)
		{
			inventory[i] = 0;
		}
		this.built = true;
		producing = new Cargo(0, 0, 0);
	}

	@Override
	public Cargo getCargo()
	{
		if (exports.size() > 0)
		{
			Cargo c = new Cargo(0, 0, (exports.get(0)));
			return c;
		}
		return new Cargo(0, 0, 0);
	}

	@Override
	public void setCargo(Cargo c)
	{
		in = c;
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

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public void update()
	{
		inventory[0] = 0;
		createExports(producing.getProductionCost());
		if (in.ID != 0)
		{
			inventory[in.ID]++;
			in = new Cargo(0, 0, 0);
		}
		if (selected >= 0 && selected < produceable.size())
			producing = new Cargo(0, 0, produceable.get(selected));
		else
			producing = new Cargo(0, 0, 0);
		if (producing.ID > 0)
		{
			boolean enoughCargo = true;
			for (Vector2D cost : producing.getProductionCost())
			{
				if (!(inventory[(int) (cost.x)] >= cost.y))
					enoughCargo = false;
			}
			if (enoughCargo && inventory[producing.ID] < 10)
			{
				productionProgress += efficency * 0.003f;
				if (productionProgress > 1)
				{
					productionProgress = 0;
					for (Vector2D cost : producing.getProductionCost())
					{
						inventory[(int) (cost.x)] -= cost.y;
					}
					inventory[producing.ID]++;
				}
			} else
			{
				productionProgress = 0;
			}
		}
	}

	@Override
	public boolean accepts(int ID)
	{
		for (Vector2D cost : producing.getProductionCost())
		{
			if (ID == (int) cost.x && inventory[ID] < 10)
				return true;
		}
		return false;
	}

	public void renderAssemblingSelector(Graphics2D b, Graphics2D g, int posx, int posy)
	{
		int h = -100 + produceable.size() / 4 * 50;
		if (h < 0)
			h = 0;
		sb.update(h, 380, 15);
		sb.render(b);
		int yoff = -sb.ca;
		renderProduceables(b, yoff, posx, posy);
		b.setColor(Color.WHITE);
		if ((newSelection >= 0 && newSelection < produceable.size()))
		{
			b.setFont(new Font("Serial", Font.PLAIN, 18));
			b.drawString(new Cargo(0, 0, produceable.get(newSelection)).toString(), 220, 20);
			b.drawLine(210, 30, 350, 30);
			int i = 0;
			for (Vector2D cost : Cargo.getProductionCost(produceable.get(newSelection)))
			{
				Cargo c = new Cargo(0, 0, (int) (cost.x));
				int x = 210 + (i % 3) * 50;
				int y = 40 + i / 3 * 50;
				c.renderAt(b, x, y, 50, 50);
				b.setColor(new Color(0, 0, 0, 100));
				b.drawRect(x, y, 50, 50);
				b.setColor(Color.white);
				b.setFont(new Font("Serial", Font.PLAIN, 12));
				b.drawString((int) cost.y + "x", x, y + 50);
				i++;
			}
		}
		save.update(320 + posx, posy - 40);
		save.render(g);
		if (save.pressed)
		{
			assemblingSelector = false;
			if (selected != newSelection)
				productionProgress = 0;
			selected = newSelection;
		}
		disband.update(360 + posx, posy - 40);
		disband.render(g);
		if (disband.pressed)
		{
			assemblingSelector = false;
		}
	}

	public void renderGeneral(Graphics2D g, Graphics2D g2, int posx, int posy)
	{
		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.BOLD, 15));
		g.drawString("Currently assembling: " + producing, 10, 20);
		int cx = 250;
		int cy = 10;
		producing.renderAt(g, cx, cy, 128, 128);
		g.setColor(Color.white);
		g.drawRect(cx, cy, 128, 128);
		if (producing.ID > 0)
		{
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(10, 30, 200, 20);
			g.setColor(Color.GREEN);
			g.fillRect(10, 30, (int) (200 * productionProgress), 20);
			g.setColor(Color.WHITE);
			g.drawString("Assembling cost: ", 10, 80);
		} else
		{
			g.setColor(new Color(0, 0, 0, 200));
			g.fillRect(cx + 1, cy + 1, 127, 127);
			g.setColor(Color.WHITE);
			g.drawString("Select", cx + 43, cy + 60);
		}
		int i = 0;
		for (Vector2D cost : producing.getProductionCost())
		{
			Cargo c = new Cargo(0, 0, (int) (cost.x));
			int x = 10 + (i % 3) * 50;
			int y = 150 - 60 + i / 3 * 50;
			if (inventory[(int) cost.x] >= cost.y)
			{
				g.setColor(new Color(0, 255, 0, 50));
			} else
			{
				g.setColor(new Color(255, 0, 0, 50));
			}
			g.fillRect(x + 1, y + 1, 49, 49);
			c.renderAt(g, x, y, 50, 50);
			g.setColor(new Color(0, 0, 0, 100));
			g.drawRect(x, y, 50, 50);
			g.setColor(Color.white);
			g.setFont(new Font("Serial", Font.PLAIN, 12));
			g.drawString((int) cost.y + "x", x, y + 50);
			i++;
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
				assemblingSelector = true;
				newSelection = selected;
			}
		}
	}

	public void createExports(ArrayList<Vector2D> cost)
	{
		exports.clear();
		for (int i = 0; i < inventory.length; i++)
		{
			add: if (inventory[i] > 0)
			{
				for (Vector2D co : cost)
				{
					if (co.x == i)
						break add;
				}
				exports.add(i);
			}
		}
	}

	public void renderInventory(Graphics2D g, Graphics2D g2, int posx, int posy)
	{
		ArrayList<Vector2D> cost = producing.getProductionCost();
		int h1 = -50 + cost.size() / 3 * 50;
		if (h1 < 0)
			h1 = 0;
		int h2 = -50 + exports.size() / 3 * 50;
		if (h2 < 0)
			h2 = 0;
		int h = h1 > h2 ? h1 : h2;
		sb.update(h, 380, 15);
		sb.render(g);
		int yoff = -sb.ca;

		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Serial", Font.PLAIN, 18));
		g2.drawString("'s Inventory", posx + 72, posy - 13);

		g.setColor(Color.WHITE);
		g.setFont(new Font("Serial", Font.PLAIN, 16));
		g.drawString("IMPORTING", 20, 20 + yoff);
		g.drawString("EXPORTING", 220, 20 + yoff);
		g.drawLine(200, 10, 200, 140);

		int i = 0;
		for (Vector2D co : cost)
		{
			Cargo c = new Cargo(0, 0, (int) (co.x));
			int x = 10 + (i % 3) * 50;
			int y = yoff + 40 + i / 3 * 50;
			c.renderAt(g, x, y, 50, 50);
			g.setColor(new Color(0, 0, 0, 100));
			g.drawRect(x, y, 50, 50);
			g.setColor(Color.white);
			g.setFont(new Font("Serial", Font.PLAIN, 12));
			g.drawString("(" + inventory[(int) co.x] + "x)", x, y + 50);
			g.setColor(new Color(0, 0, 0, 100));
			i++;
		}
		i = 0;
		for (int j : exports)
		{
			Cargo c = new Cargo(0, 0, j);
			int x = 210 + (i % 3) * 50;
			int y = yoff + 40 + i / 3 * 50;
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
			if (assemblingSelector)
				renderAssemblingSelector(b, g, posx, posy);
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
		price[Cargo.IRON] = 3;
		price[Cargo.STONE] = 5;
		return price;
	}

	@Override
	public String getName()
	{
		return "Factory";
	}
}
