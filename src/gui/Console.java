package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

import display.Frame;

public class Console 
{
	private static ArrayList<String> msgs = new ArrayList<String>();
	private static ArrayList<Float> decay = new ArrayList<Float>();
	
	public static void render(Graphics2D g) 
	{
		for(int i = Math.max(0, msgs.size()-10); i < msgs.size(); i++) 
		{
			String msg = msgs.get(i);
			g.setColor(new Color(255,255,255,200));
			g.setFont(new Font("Serial", Font.PLAIN, 12));
			g.drawString(msg, 20, Frame.height-(msgs.size()*20-i*20));
		}
	}
	public static void update()
	{
		for(int i = 0; i < decay.size(); i++) 
		{
			decay.set(i, decay.get(i)-0.01f);
			if(decay.get(i)<=0) 
			{
				msgs.remove(i);
				decay.remove(i);
			}
		}
	}
	public static void print(String s) 
	{
		msgs.add(s);
		decay.add(1f);
	}
}
