package data;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import utility.Vector2D;

public class Tasks implements Serializable
{
	private static final long serialVersionUID = 8431287374606766684L;
	public ArrayList<Task> tasks;
	public int selectingTask = -1, selectedTask = -1;
	public int OwnerID;
	public Tasks(int OwnerID)
	{
		this.OwnerID = OwnerID;
		tasks = new ArrayList<Task>();
	}
	public void addTask(int ID, String name)
	{
		selectedTask = tasks.size();
		tasks.add(new Task(ID ,tasks.size(), name, OwnerID, tasks.size()));
	}
	public boolean workToDo()
	{
		return tasks.size()>0;
	}
	public void update()
	{
		if(selectingTask!=-1) 
		{
			if(tasks.get(selectingTask).hasSelection==true)tasks.get(selectingTask).s.update();
		}
		for(int i = 0; i < tasks.size(); i++)
		{
			tasks.get(i).update(i);
		}
	}
	public void renderSelection(Graphics2D g)
	{
		if(selectedTask!=-1)
		{
			tasks.get(selectedTask).s.render(g);
			Vector2D des = tasks.get(selectedTask).des;
			if(des !=null)
			{
				Selection d = new Selection((int)des.x, (int)des.y, (int)des.x+1, (int)des.y+1, selectedTask);
				d.render(g, new Color(0,0,255));
			}
		}
	}
	public void render(Graphics2D g, int x, int y, int dx, int dy)
	{
		for(Task task : tasks)
		{
			task.render(g, x, y, dx, dy);
			y+=50;
		}
	}
}
