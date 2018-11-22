package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import data.LoadSave;
import game.Simulation;
import world.Map;

public class Keyboard implements KeyListener
{
	public static boolean r,x,c, left, right, up, down, space, esc, m;
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)esc = true;
		if(e.getKeyCode()==KeyEvent.VK_R)r=true;
		if(e.getKeyCode()==KeyEvent.VK_X)x=true;
		if(e.getKeyCode()==KeyEvent.VK_C)c=true;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)left=true;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)right=true;
		if(e.getKeyCode()==KeyEvent.VK_UP)up=true;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)down=true;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)space=true;
		if(e.getKeyCode()==KeyEvent.VK_M)m=true;
		if(e.getKeyCode()==KeyEvent.VK_S) 
		{
			Map map = Simulation.map;
			LoadSave.saveMap(map, Simulation.currentSave);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE)esc = false;
		if(e.getKeyCode()==KeyEvent.VK_R)r=false;
		if(e.getKeyCode()==KeyEvent.VK_X)x=false;
		if(e.getKeyCode()==KeyEvent.VK_C)c=false;
		if(e.getKeyCode()==KeyEvent.VK_LEFT)left=false;
		if(e.getKeyCode()==KeyEvent.VK_RIGHT)right=false;
		if(e.getKeyCode()==KeyEvent.VK_UP)up=false;
		if(e.getKeyCode()==KeyEvent.VK_DOWN)down=false;
		if(e.getKeyCode()==KeyEvent.VK_SPACE)space=false;
		if(e.getKeyCode()==KeyEvent.VK_M)m=false;
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
