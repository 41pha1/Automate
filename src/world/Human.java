package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;

import animation.Animator;
import data.Tank;
import data.Tasks;
import game.Simulation;
import gui.Gui;
import input.Keyboard;
import input.Picker;
import texture.TextureLoader;
import utility.NameGenerator;
import utility.Vector2D;

public class Human extends Entity implements Serializable
{
	private static final long serialVersionUID = 2037179882880511320L;
	public Tasks t;
	public String taskInfo;
	public Vector2D destination;
	public boolean chopping = false;
	public boolean waitingForMaterials = false;
	public boolean busy = false;
	public boolean atCorner = true;
	public Building TB;
	public int[] inventory;
	public String name;
	public Tank O;

	public Human(float x, float y, int id, int d)
	{
		super(x, y, id, d);
		O = new Tank(1, 1000, 1000);
		name = NameGenerator.getName();
		t = new Tasks(id);
		inventory = new int[Cargo.DIFFERENTCARGOS];
		for (int j = 0; j < inventory.length; j++)
		{
			inventory[j] = 0;
		}
	}

	@Override
	public void showGui(Graphics2D g)
	{
		Gui.showGui(g, this);
	}

	public int countInventory()
	{
		int count = 0;
		for (int i : inventory)
		{
			count += i;
		}
		return count;
	}

	@Override
	public int[] getInventory()
	{
		return inventory;
	}

	@Override
	public void render(Graphics g)
	{
		int size = (int) Simulation.map.t.getSize();
		Vector2D pos = getPosToRenderAt();
		if (moving)
		{
			int img = (Animator.veryFastAnimation / 2) % 13;
			g.drawImage(TextureLoader.astronaut[0][(dir + 1) % 4][img], (int) pos.x, (int) pos.y, size * 2, size * 2,
					null);
		} else if (chopping)
		{
			int img = Animator.fastAnimation % 9;
			g.drawImage(TextureLoader.astronaut[1][(dir + 1) % 4][img], (int) pos.x, (int) pos.y, size * 2, size * 2,
					null);
		} else
		{
			g.drawImage(TextureLoader.textures[5][(dir)][0], (int) pos.x, (int) pos.y, size * 2, size * 2, null);
		}
	}

	@Override
	public void renderSelection(Graphics g)
	{
		int size = (int) Simulation.map.t.getSize();
		Vector2D pos = getPosToRenderAt();
		if (Simulation.map.selectedEntity == ID)
		{
			g.setColor(new Color(255, 255, 255, 150));
			g.fillOval((int) pos.x + size - size / 3, (int) pos.y + size + 20, (int) (size / 1.5f), size / 3);
		}
	}

	@Override
	public Vector2D getPosToRenderAt()
	{
		int size = (int) Simulation.map.t.getSize();
		float x = this.x * size + (Simulation.map.t.getX() * size);
		float y = this.y * size + (Simulation.map.t.getY() * size) + 10;
		float isoX = x - y;
		float isoY = (x + y) / 2f;
		return new Vector2D(isoX, isoY - 96);
	}

	@Override
	public Tasks getTasks()
	{
		return t;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public void moveTo(Vector2D des)
	{
		boolean active = !goToNearestEdge();
		if (!active)
		{
			active = !goTo(des);
		}
		if (!active)
		{
			int x = Math.round(this.x);
			int y = Math.round(this.y);
			float dx = this.x - x;
			float dy = this.y - y;
			if (Math.abs(dx) < 0.1f)
				this.x = x;
			if (Math.abs(dy) < 0.1f)
				this.y = y;
		}
		moving = active;
		busy = active;
	}

	public boolean goTo(Vector2D des)
	{
		float x = des.x;
		float y = des.y;
		float dx = this.x - x - 0.5f;
		float dy = this.y - y - 0.5f;
		float d = 0.5f;
		if (-dy - 0.05f > d)
		{
			dir = 0;
			atCorner = true;
			return false;
		} else if (dy > d)
		{
			dir = 2;
			atCorner = true;
			return false;
		} else if (dx > d)
		{
			dir = 1;
			atCorner = true;
			return false;
		} else if (-dx - 0.05f > d)
		{
			dir = 3;
			atCorner = true;
			return false;
		} else
			return true;
	}

	public boolean isAtCorner()
	{
		int x = Math.round(this.x);
		int y = Math.round(this.y);
		float dx = this.x - x;
		float dy = this.y - y;
		float d = 0.1f;
		return (dy < d && dx < d);
	}

	public boolean isAtEdge()
	{
		int x = Math.round(this.x);
		int y = Math.round(this.y);
		float dx = Math.abs(this.x - x);
		float dy = Math.abs(this.y - y);
		float d = 0.02f;
		return (dy < d || dx < d);
	}

	public boolean goToEdge(Vector2D des)
	{
		float y = des.y;
		float dy = Math.abs(this.y - y - 0.5f);
		if (dy <= speed)
		{
			if (this.y - 1 > (y - 0.5f))
				this.y -= speed;
			return true;
		}
		if (this.y - 1 > (y - 0.5f))
			dir = 2;
		if (this.y - 1 < (y - 0.5f))
			dir = 0;
		return false;
	}

	public void turnTowards(Vector2D des)
	{
		float x = des.x;
		float y = des.y;
		float dx = Math.abs(this.x - x - 0.5f);
		float dy = Math.abs(this.y - y - 0.5f);
		if (dx > dy)
		{
			if (this.x - 1 > x - 0.5f)
				dir = 1;
			if (this.x - 1 < x - 0.5f)
				dir = 3;
		} else
		{
			if (this.y - 1 < y - 0.5f)
				dir = 0;
			if (this.y - 1 > y - 0.5f)
				dir = 2;
		}
	}

	public Vector2D searchForBuildingWithCargo(int x1, int y1, int x2, int y2)
	{
		int shortestX = -1;
		int shortestY = -1;
		float shortestD = Simulation.map.width + Simulation.map.height + 1;
		for (int x = x1; x < x2; x++)
		{
			for (int y = y1; y < y2; y++)
			{
				if (Simulation.map.tiles[x][y].b.getCargo().ID != 0)
				{
					float D = Math.abs(this.x - x) + Math.abs(this.y - y);
					if (D < shortestD)
					{
						shortestD = D;
						shortestX = x;
						shortestY = y;
					}
				}
			}
		}
		if (shortestX + shortestY != -2)
			return new Vector2D(shortestX, shortestY);
		return null;
	}

	public Cargo searchForItem(int x1, int y1, int x2, int y2)
	{
		x1 -= 0.5f;
		y1 -= 0.5f;
		x2 += 0.5f;
		y2 += 0.5f;
		Cargo closestCargo = null;
		float shortestD = Simulation.map.width + Simulation.map.height + 1;
		for (Cargo c : Simulation.map.cargo)
		{
			if (c.x > x1 && c.x < x2 && c.y > y1 && c.y < y2)
			{
				float D = Math.abs(this.x - c.x) + Math.abs(this.y - c.y);
				if (D < shortestD)
				{
					shortestD = D;
					closestCargo = c;
				}
			}
		}
		return closestCargo;
	}

	public Building searchForBuildingToBuild()
	{
		if (!Simulation.map.schematic.saved)
			return null;
		int shortestX = -1;
		int shortestY = -1;
		float shortestD = Float.MAX_VALUE;
		for (int x = 0; x < Simulation.map.width; x++)
		{
			for (int y = 0; y < Simulation.map.height; y++)
			{
				if (whatToDoWithBuilding(x, y) != 0)
				{
					float D = Math.abs(this.x - x) + Math.abs(this.y - y);
					if (D < shortestD)
					{
						shortestD = D;
						shortestX = x;
						shortestY = y;
					}
				}
			}
		}
		if (shortestX + shortestY != -2)
			return Simulation.map.schematic.save[shortestX][shortestY];
		return null;
	}

	public Vector2D searchFor(int ID, int x1, int y1, int x2, int y2)
	{
		int shortestX = -1;
		int shortestY = -1;
		float shortestD = Simulation.map.width + Simulation.map.height + 1;
		for (int x = x1; x < x2; x++)
		{
			for (int y = y1; y < y2; y++)
			{
				if (Simulation.map.tiles[x][y].b.getID() == ID)
				{
					float D = Math.abs(this.x - x) + Math.abs(this.y - y);
					if (D < shortestD)
					{
						shortestD = D;
						shortestX = x;
						shortestY = y;
					}
				}
			}
		}
		if (shortestX + shortestY != -2)
			return new Vector2D(shortestX, shortestY);
		return null;
	}

	public boolean goToCorner()
	{
		float y = Math.round(this.y);
		float dy = Math.abs(this.y - y);
		if (atCorner)
			return true;
		if (dy <= speed)
		{
			atCorner = true;
			return true;
		}
		if (this.y - 1 < (y - 0.5f))
			dir = 2;
		if (this.y - 1 > (y - 0.5f))
			dir = 0;
		return false;
	}

	public boolean goToNearestEdge()
	{
		int x = Math.round(this.x);
		int y = Math.round(this.y);
		float dx = this.x - x;
		float dy = this.y - y;
		if (isAtEdge())
		{
			if (Math.abs(dx) < Math.abs(dy))
				this.x = x;
			else
				this.y = y;
			return true;
		}
		if (Math.abs(dx) < Math.abs(dy))
		{
			dir = 0;
		} else
		{
			dir = 1;
		}
		return false;
	}

	public boolean gatherCargo(Building b)
	{
		moving = false;
		busy = moving;
		int[] price = b.getPrice();
		int j = 0;
		boolean AllMaterialsGathered = true;
		for (int i = 0; i < Cargo.DIFFERENTCARGOS; i++)
		{
			j += price[i];
			if (!(inventory[i] >= price[i]))
			{
				AllMaterialsGathered = false;
			}
		}
		if (AllMaterialsGathered)
		{
			taskInfo = "Every material gathered";
			return true;
		}
		if (j == 0)
			return true; // free Building
		destination = searchFor(5, 0, 0, Simulation.map.width, Simulation.map.height);
		if (destination == null)
		{
			taskInfo = "No storage found!";
			return false;
		} else
		{
			moveTo(destination);
			if (!busy)
			{
				waitingForMaterials = true;
				taskInfo = "Waiting for materials";
				for (int i = 0; i < Cargo.DIFFERENTCARGOS; i++)
				{
					Building storage = Simulation.map.getTile((int) destination.x, (int) destination.y).b;
					if (price[i] > 0 && storage.getInventory()[i] > 0 && price[i] > inventory[i])
					{
						storage.getInventory()[i]--;
						inventory[i]++;
						waitingForMaterials = false;
						return false;
					}
				}
			} else
			{
				return false;
			}
		}
		return false;
	}

	public static int whatToDoWithBuilding(int x, int y)
	{
		if (!Simulation.map.schematic.saved)
			return 0;
		Building MB = Simulation.map.tiles[x][y].b;
		Building SB = Simulation.map.schematic.save[x][y];
		if (MB.compare(SB) || ((!MB.built) && (!SB.built)))
			return 0; // DoNothing
		if (!MB.built && SB.built)
			return 1; // Build
		if (MB.built && !SB.built)
			return 2; // Destroy
		return 3; // Change
	}

	public void build(int i)
	{
		Building toBuild = null;
		if (TB != null)
		{
			destination = new Vector2D(TB.x, TB.y);
			if (whatToDoWithBuilding(TB.x, TB.y) == 0)
			{
				TB = null;
			}
		}
		if (TB != null)
			toBuild = TB;
		else
		{
			toBuild = searchForBuildingToBuild();
			TB = toBuild;
		}
		if (toBuild != null)
		{
			int whatToDoWithBuilding = whatToDoWithBuilding(toBuild.x, toBuild.y);
			if (!busy)
			{
				if (whatToDoWithBuilding == 1)
					gatherCargo(toBuild);
				if (!busy)
				{
					destination = new Vector2D(toBuild.x, toBuild.y);
					moveTo(destination);
					if (!busy)
					{
						turnTowards(destination);
						if (whatToDoWithBuilding == 1) // Build
						{
							int[] price = toBuild.getPrice();
							for (int j = 0; j < Cargo.DIFFERENTCARGOS; j++)
							{
								inventory[j] -= price[j];
							}
							Simulation.map.tiles[(int) destination.x][(int) destination.y].b = Simulation.map.schematic.save[(int) destination.x][(int) destination.y];
						} else if (whatToDoWithBuilding == 2) // Destroy
						{
							int[] refund = Simulation.map.tiles[(int) destination.x][(int) destination.y].b.getPrice();
							for (int j = 0; j < Cargo.DIFFERENTCARGOS; j++)
							{
								inventory[j] += refund[j];
							}
							Simulation.pm.addDestructionParticles(
									Simulation.map.tiles[(int) destination.x][(int) destination.y].b);
							Simulation.map.tiles[(int) destination.x][(int) destination.y].b = new Building(
									(int) destination.x, (int) destination.y);
						} else if (whatToDoWithBuilding == 3) // Change
						{
							Simulation.map.tiles[(int) destination.x][(int) destination.y].b
									.setCurvature(toBuild.getCurvature());
							Simulation.map.tiles[(int) destination.x][(int) destination.y].b.dir = toBuild.dir;
						}
					}
				}
			}
		}
		busy = moving || waitingForMaterials;
	}

	public void chopWood(int i)
	{
		if (inventory[4] < 1)
		{
			if (!moving)
				destination = searchFor(6, t.tasks.get(i).s.x1, t.tasks.get(i).s.y1, t.tasks.get(i).s.x2,
						t.tasks.get(i).s.y2);
			if (destination != null)
			{
				if (!moving)
					moveTo(destination);
				if (!busy)
				{
					turnTowards(destination);
					Building b = Simulation.map.tiles[(int) destination.x][(int) destination.y].b;
					if (Animator.fastAnimation % 9 == 0)
					{
						Simulation.pm.addParticles((int) destination.x, (int) destination.y, 0.1f, 0, 0, 20, 20,
								TextureLoader.cargo[Cargo.LOG]);
					}
					if (b.getChopped() > 200 && Animator.fastAnimation % 9 == 0)
					{
						Simulation.pm.addParticles((int) destination.x, (int) destination.y, 0.05f, -0.0001f, -0.0001f,
								100, 50, TextureLoader.textures[6][0][0]);
						Simulation.map.tiles[(int) destination.x][(int) destination.y].b = new Building(0, 0);
						atCorner = false;
						chopping = false;
						Simulation.map.cargo.add(new Cargo((int) destination.x, (int) destination.y, Cargo.LOG,
								Simulation.map.cargo.size(), true));
					} else
					{
						b.chop();
						chopping = true;
					}
				}
			}
		}
		busy = moving || chopping;
	}

	public void destroy(int i)
	{
		if (inventory[4] < 1)
		{
			if (!moving)
				destination = searchFor(7, t.tasks.get(i).s.x1, t.tasks.get(i).s.y1, t.tasks.get(i).s.x2,
						t.tasks.get(i).s.y2);
			if (destination != null)
			{
				if (!moving)
					moveTo(destination);
				if (!busy)
				{
					turnTowards(destination);
					Building b = Simulation.map.tiles[(int) destination.x][(int) destination.y].b;
					if (Animator.fastAnimation % 9 == 0)
					{
						Simulation.pm.addParticles((int) destination.x, (int) destination.y, 0.1f, 0, 0, 20, 20,
								TextureLoader.textures[8][0][0]);
					}
					if (b.getChopped() > 200 && Animator.fastAnimation % 9 == 0)
					{
						Simulation.pm.addParticles((int) destination.x, (int) destination.y, 0.05f, -0.0001f, -0.0001f,
								100, 50, TextureLoader.textures[Cargo.STONE][0][0]);
						Simulation.map.tiles[(int) destination.x][(int) destination.y].b = new Building(0, 0);
						atCorner = false;
						chopping = false;
						Simulation.map.cargo.add(new Cargo((int) destination.x, (int) destination.y, Cargo.STONE,
								Simulation.map.cargo.size(), true));
					} else
					{
						b.chop();
						chopping = true;
					}
				}
			}
		}
		busy = moving || chopping;
	}

	public void pickUp(int j)
	{
		if (countInventory() > 0)
		{
			if (!moving)
			{
				if (t.tasks.get(j).des == null || t.tasks.get(j).selectingDestination)
				{
					destination = searchFor(5, 0, 0, Simulation.map.width, Simulation.map.height);
				} else
				{
					destination = t.tasks.get(j).des;
					destination.x = (int) destination.x;
					destination.y = (int) destination.y;
				}
			}
			if (destination != null)
			{
				if (!moving)
					moveTo(destination);
				if (!busy)
				{
					turnTowards(destination);
					for (int i = 0; i < inventory.length; i++)
					{
						if (inventory[i] > 0)
						{
							Building b = Simulation.map.tiles[(int) destination.x][(int) destination.y].b;
							if (b != null)
							{
								if (b.getID() == 5)
								{
									b.getInventory()[i] += 1;
								} else if (b.accepts(i))
								{
									b.setCargo(new Cargo(0, 0, i));
								} else
								{
									Simulation.map.cargo.add(new Cargo((int) destination.x, (int) destination.y, i,
											Simulation.map.cargo.size(), true));
								}
							}
							inventory[i]--;
						}
					}
				}
			}
		} else
		{
			Cargo c = searchForItem(t.tasks.get(j).s.x1, t.tasks.get(j).s.y1, t.tasks.get(j).s.x2, t.tasks.get(j).s.y2);
			if (c != null)
			{
				if (!moving)
					destination = new Vector2D(Math.round(c.x), Math.round(c.y));
				if (!moving)
					moveTo(destination);
				if (!busy)
				{
					turnTowards(destination);
					inventory[c.ID]++;
					Simulation.map.cargo.remove(c.id);
				}
			}
			if (!moving)
			{
				destination = searchForBuildingWithCargo(t.tasks.get(j).s.x1, t.tasks.get(j).s.y1, t.tasks.get(j).s.x2,
						t.tasks.get(j).s.y2);
				if (destination != null)
				{
					if (!moving)
						moveTo(destination);
					if (!busy)
					{
						turnTowards(destination);
						c = Simulation.map.tiles[(int) destination.x][(int) destination.y].b.getCargo();
						inventory[c.ID]++;
						Simulation.map.tiles[(int) destination.x][(int) destination.y].b.setCargo(new Cargo(0, 0, 0));
					}
				}
			}
		}
		busy = moving;
	}

	public void doTask()
	{
		moving = false;
		for (int i = 0; i < t.tasks.size(); i++)
		{
			if (t.tasks.get(i).ID == 0 && (t.tasks.get(i).s.selected && (!busy || i == 0)))
			{
				chopWood(i);
			} else if (t.tasks.get(i).ID == 1 && (t.tasks.get(i).s.selected && (!busy || i == 0)))
			{
				pickUp(i);
			} else if (t.tasks.get(i).ID == 2 && (!busy || i == 0))
			{
				build(i);
			} else if (t.tasks.get(i).ID == 3
					&& (t.tasks.get(i).des != null && !t.tasks.get(i).selectingDestination && (!busy || i == 0)))
			{
				moveTo(new Vector2D((int) t.tasks.get(i).des.x, (int) t.tasks.get(i).des.y));
				busy = true;
			} else if (t.tasks.get(i).ID == 4 && (t.tasks.get(i).s.selected && (!busy || i == 0)))
			{
				destroy(i);
			}
		}
	}

	@Override
	public void setActive(boolean b)
	{
		busy = b;
	}

	public void checkForCB()
	{
		int x = Math.round(this.x - 0.55f);
		int y = Math.round(this.y - 0.4f);
		if (Simulation.map.tiles[x][y].b.getID() == 2)
		{
			int dir = Simulation.map.tiles[x][y].b.dir;
			if (dir == 0)
			{
				if (this.x - x > 0.2f && this.x - x < 0.65f)
				{
					this.y -= ConveyorBelt.speed;
				}
			}
			if (dir == 1)
			{
				if (this.y - y > 0f && this.y - y < 0.5f)
				{
					this.x -= ConveyorBelt.speed;
				}
			}
			if (dir == 2)
			{
				if (this.x - x > 0.2f && this.x - x < 0.65f)
				{
					this.y += ConveyorBelt.speed;
				}
			}
			if (dir == 3)
			{
				if (this.y - y > 0f && this.y - y < 0.5f)
				{
					this.x += ConveyorBelt.speed;
				}
			}
		}
	}

	@Override
	public void update()
	{
		if (Keyboard.m)
		{
			x = Picker.pick.x;
			y = Picker.pick.y;
		}

		if (O.amount > 0)
			O.amount -= 0.01f;

		moving = false;
		busy = false;
		waitingForMaterials = false;
		if (t.workToDo())
			doTask();
		if (!busy)
			moving = !goToCorner();
		if (dir == 0 && moving)
			y += speed;
		if (dir == 1 && moving)
			x -= speed;
		if (dir == 2 && moving)
			y -= speed;
		if (dir == 3 && moving)
			x += speed;
		checkForCB();
	}
}