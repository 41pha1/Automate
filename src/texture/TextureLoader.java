package texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import animation.Animator;
import world.Building;
import world.Cargo;

public class TextureLoader
{
	public static BufferedImage[][][] textures;
	public static BufferedImage[] icons;
	public static BufferedImage[][] CBCurves;
	public static BufferedImage[][][] astronaut;
	public static BufferedImage[] cargo;
	public static BufferedImage[] rocketAnimation;
	public static BufferedImage[][][] grounds;
	public static BufferedImage[] pipes;

	public TextureLoader()
	{
		textures = new BufferedImage[12][16][4];
		icons = new BufferedImage[10];
		grounds = new BufferedImage[1][1][4];
		CBCurves = new BufferedImage[16][4];
		astronaut = new BufferedImage[3][4][8];
		cargo = new BufferedImage[Cargo.DIFFERENTCARGOS];
		pipes = new BufferedImage[11];
		rocketAnimation = new BufferedImage[101];
		loadAnimations();
		loadCargo();
		loadTextures();
		loadGrounds();
		loadCBcurves();
		loadIcons();
		loadPipes();
	}

	public static void loadPipes()
	{
		pipes = loadTextures("Pipes", 11, 1, 11);
	}

	public static void loadGrounds()
	{
		grounds[0] = loadTexture("martianGround", 2, 2, 4);
	}

	public static void loadCargo()
	{
		cargo[Cargo.LOG] = loadImage("log");
		cargo[Cargo.STONE] = loadImage("Stone");
		cargo[Cargo.CABLE] = loadImage("cable");
		cargo[Cargo.MOTOR] = loadImage("Motor");
		cargo[Cargo.RUBBER] = loadImage("Rubber");
		cargo[Cargo.WIRE] = loadImage("Wire");
		cargo[Cargo.COIL] = loadImage("Coil");
		cargo[Cargo.IRON] = loadImage("Iron");
		cargo[Cargo.COPPER] = loadImage("Copper");
		cargo[Cargo.PROCESSOR] = loadImage("Processor");
		cargo[Cargo.COAL] = loadImage("Coal");
		cargo[Cargo.IRON_ORE] = loadImage("IronOre");
		cargo[Cargo.COPPER_ORE] = loadImage("CopperOre");
		cargo[Cargo.PIPE] = loadImage("pipe");

	}

	public static void loadAnimations()
	{
		BufferedImage[][] walkAnimation = loadDirectionalTexture("AstronautWalk", 13);
		astronaut[0][0] = walkAnimation[2];
		astronaut[0][1] = walkAnimation[1];
		astronaut[0][2] = walkAnimation[0];
		astronaut[0][3] = walkAnimation[3];
		astronaut[1] = loadTexture("Chop", 3, 3, 9);
		astronaut[2] = loadTexture("Carry", 2, 2, 4);
		rocketAnimation = loadTextures("hRocket", 10, 10, 100);
	}

	public static void loadIcons()
	{
		icons[0] = loadImage("delete");
		icons[1] = loadImage("select");
		icons[2] = loadImage("destination");
		icons[3] = loadImage("build");
		icons[4] = loadImage("right");
		icons[5] = loadImage("left");
		icons[6] = loadImage("save");
		icons[7] = loadImage("arrow");
	}

	public static void loadCBcurves()
	{
		BufferedImage[][] CBCurvesRight = loadDirectionalTexture("ConveyorBeltCurvesRight", 4);
		BufferedImage[][] CBCurvesLeft = loadDirectionalTexture("ConveyorBeltCurvesLeft", 4);

		CBCurves[0] = CBCurvesRight[3];
		CBCurves[1] = CBCurvesRight[0];
		CBCurves[2] = CBCurvesRight[1];
		CBCurves[3] = CBCurvesRight[2];
		CBCurves[4] = CBCurvesLeft[1];
		CBCurves[5] = CBCurvesLeft[2];
		CBCurves[6] = CBCurvesLeft[3];
		CBCurves[7] = CBCurvesLeft[0];
	}

	public static void loadTextures()
	{
		textures[0] = loadDirectionalTexture("ConveyorBelt", 4);
		textures[1] = loadTexture("Mine", 1, 1, 1);
		textures[3] = loadDirectionalTexture("RoboArm", 4, 4, 16);
		textures[5] = loadDefaultPose();
		textures[6] = loadTexture("tree", 1, 1, 1);
		textures[7] = loadTexture("rocket", 1, 1, 1);
		textures[8] = loadTexture("Rock", 1, 1, 1);
		textures[9] = loadTexture("factory", 1, 1, 1);
		textures[10] = loadTexture("smeltery", 1, 1, 1);
		textures[11] = loadTexture("generator", 1, 9, 9);
		// Building.clone();
	}

	public static BufferedImage[][] loadDefaultPose()
	{
		BufferedImage[][] output = new BufferedImage[4][1];

		output[0][0] = astronaut[0][1][0];
		output[1][0] = astronaut[0][2][0];
		output[2][0] = astronaut[0][3][0];
		output[3][0] = astronaut[0][0][0];
		return output;
	}

	public static BufferedImage loadImage(String name)
	{
		File file = new File("res/" + name + ".png");
		BufferedImage texture = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		try
		{
			texture = ImageIO.read(file);
		} catch (IOException e)
		{
			System.err.println("Error occured loading image " + name + ".png!");
		}
		return texture;
	}

	public static BufferedImage[][] loadDirectionalTexture(String name, int x, int y, int n)
	{
		BufferedImage[][] tex = new BufferedImage[4][n];
		tex[0] = loadTextures(name + 2, x, y, n);
		tex[1] = loadTextures(name + 1, x, y, n);
		tex[2] = loadTextures(name + 0, x, y, n);
		tex[3] = loadTextures(name + 3, x, y, n);
		return tex;
	}

	public static BufferedImage[][] loadDirectionalTexture(String name, int n)
	{
		BufferedImage[][] tex = new BufferedImage[4][n];
		BufferedImage[] dirs = loadTextures(name, 1, 4, 4);
		tex[0] = loadTextures(dirs[2], n, 1, n);
		tex[1] = loadTextures(dirs[1], n, 1, n);
		tex[2] = loadTextures(dirs[0], n, 1, n);
		tex[3] = loadTextures(dirs[3], n, 1, n);
		return tex;
	}

	public static BufferedImage[] loadTextures(String name, int x, int y, int n)
	{
		BufferedImage texture = loadImage(name);
		BufferedImage[] tex = new BufferedImage[n];
		int k = 0;
		outerLoop: for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				float w = texture.getWidth() / (float) x;
				float h = texture.getHeight() / (float) y;
				BufferedImage part = new BufferedImage((int) w + 1, (int) h + 1, BufferedImage.TYPE_INT_ARGB);
				Graphics g = part.getGraphics();
				g.drawImage(texture, 0, 0, (int) w, (int) h, (int) (j * w), (int) (i * h), (int) ((j + 1) * w),
						(int) ((i + 1) * h), null);
				tex[k] = part;
				k++;
				if (k >= n)
					break outerLoop;
			}
		}
		return tex;
	}

	public static BufferedImage[] loadTextures(BufferedImage texture, int x, int y, int n)
	{
		BufferedImage[] tex = new BufferedImage[n];
		int k = 0;
		outerLoop: for (int i = 0; i < y; i++)
		{
			for (int j = 0; j < x; j++)
			{
				float w = texture.getWidth() / (float) x;
				float h = texture.getHeight() / (float) y;
				BufferedImage part = new BufferedImage((int) w + 1, (int) h + 1, BufferedImage.TYPE_INT_ARGB);
				Graphics g = part.getGraphics();
				g.drawImage(texture, 0, 0, (int) w, (int) h, (int) (j * w), (int) (i * h), (int) ((j + 1) * w),
						(int) ((i + 1) * h), null);
				tex[k] = part;
				k++;
				if (k >= n)
					break outerLoop;
			}
		}
		return tex;
	}

	public static BufferedImage getTexture(Building b)
	{
		switch (b.getID())
		{
		case 0:
			return textures[1][0][0];
		case 1:
			int img = Animator.fastAnimation % 32;
			if (img > 15)
				img = 31 - img;
			return textures[3][b.dir][img];
		case 2:
			int curve = b.getCurvature();
			if (curve != 0)
				return CBCurves[((curve - 1) * 4) + b.dir][Animator.updates % 4];
			else
				return textures[0][b.dir][Animator.updates % 4];
		case 3:
			return textures[10][0][0];
		case 4:
			return new BufferedImage(0, 0, 0);
		case 5:
			return textures[7][0][0];
		case 6:
			return textures[6][0][0];
		case 7:
			return textures[8][0][0];
		case 8:
			return textures[9][0][0];
		case 9:
			return textures[11][0][Animator.veryFastAnimation / 2 % 8];
		default:
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
	}

	public static BufferedImage[][] loadTexture(String name, int x, int y, int n)
	{
		BufferedImage[][] tex = new BufferedImage[4][n];
		BufferedImage[] textures = loadTextures(name, x, y, n);
		BufferedImage[] texturesR = loadTextures(name + "R", x, y, n);
		tex[0] = textures;
		tex[1] = Rotater.rotate(textures, 1);
		tex[2] = texturesR;
		tex[3] = Rotater.rotate(texturesR, 1);
		return tex;
	}
}
