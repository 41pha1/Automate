package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener
{
	public static int x;
	public static int y;
	public static int mw;
	public static boolean left = false;
	public static boolean right = false;
	public static boolean middle = false;
	
	@Override
	public void mouseDragged(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) 
	{

	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{

	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == 1)left=true;
		if(e.getButton() == 2)middle=true;
		if(e.getButton() == 3)right=true;
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(e.getButton() == 1)left = false;
		if(e.getButton() == 2)middle = false;
		if(e.getButton() == 3)right = false;
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		mw = e.getWheelRotation();
	}
}
