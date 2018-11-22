package world;

public class Tree extends Building
{
	private static final long serialVersionUID = 4326406233939733367L;
	public static final int ID = 6;
	public int chopped = 0;

	public Tree()
	{
		chopped = 0;
	}

	@Override
	public int getChopped()
	{
		return chopped;
	}

	@Override
	public void chop()
	{
		chopped++;
	}

	public Tree(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.built = true;
	}

	@Override
	public int getID()
	{
		return ID;
	}

	@Override
	public void update()
	{

	}

	@Override
	public String getName()
	{
		return "Tree";
	}
}
