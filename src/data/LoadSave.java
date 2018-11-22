package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import gui.Console;
import world.Map;

public class LoadSave
{
	public static void saveMap(Map map, String file)
	{
		OutputStream ops = null;
		ObjectOutputStream objOps = null;
		try
		{
			ops = new FileOutputStream(file + ".txt");
			objOps = new ObjectOutputStream(ops);
			objOps.writeObject(map);
			objOps.flush();
			System.out.println("Saved!");
			Console.print("Saved!");
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (objOps != null)
					objOps.close();
			} catch (Exception ex)
			{

			}
		}
	}

	public static Map loadMap(String file)
	{
		InputStream fileIs = null;
		ObjectInputStream objIs = null;
		try
		{
			fileIs = new FileInputStream(file + ".txt");
			objIs = new ObjectInputStream(fileIs);
			Map map = (Map) objIs.readObject();
			return map;
		} catch (FileNotFoundException e)
		{
			e.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} finally
		{
			try
			{
				if (objIs != null)
					objIs.close();
			} catch (Exception ex)
			{

			}
		}
		return null;
	}
}
