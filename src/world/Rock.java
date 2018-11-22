package world;

public class Rock extends Building
{
	private static final long serialVersionUID = 3745516425678946096L;
	public static final int ID = 7;
	public int chopped = 0;

	public Rock()
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

	public Rock(int x, int y)
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
		return "Rock";
	}
}
