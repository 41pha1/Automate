package gui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader 
{
	public static Font font;
	public FontLoader() 
	{
		try 
		{
			font = Font.createFont(Font.TRUETYPE_FONT, new File("res/FONT.ttf")).deriveFont(9f);
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}
	}
}
