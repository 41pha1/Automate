package launcher;

import display.Frame;
import game.Simulation;
import gui.FontLoader;
import gui.Store;
import texture.TextureLoader;
import utility.Counter;

public class Main
{
	public static int fpsCap = 60, fps;
	public static Frame f;
	public static Simulation s;
	public static Store store;
	public static TextureLoader t;
	public static FontLoader FL;

	public static void main(String[] args)
	{
		long t = System.currentTimeMillis();
		init();
		System.out.println("Done! (" + (System.currentTimeMillis() - t) + "ms)");
		long lr = System.nanoTime();
		long tpr = 1000000000 / (fpsCap);
		while (true)
		{
			Counter.update();
			long time = System.nanoTime();
			long tslr = time - lr;
			if (tslr >= tpr)
			{
				fps = Counter.lastCount;
				lr = System.nanoTime();
				s.update((float) tslr / (float) tpr);
				f.render();
			}
		}
	}

	public static void init()
	{
		s = new Simulation();
		t = new TextureLoader();
		FL = new FontLoader();
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// int width = (int)screenSize.getWidth();
		// int height = (int)screenSize.getHeight();
		int width = 1920 / 2;
		int height = 1080 / 2;
		f = new Frame(width, height, 0, 0);
		store = new Store();
	}
}
