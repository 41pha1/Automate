package data;

import java.io.Serializable;

import world.Cargo;

public class Res implements Serializable
{
	private static final long serialVersionUID = 1277932620839093033L;
	public float[] res;
	public Res(int ID)
	{
		res = new float[Cargo.DIFFERENTCARGOS];
		for(int i = 0; i < res.length; i++) 
		{
			res[i] = (float)Math.random()+0.5f;
		}
	}
}
