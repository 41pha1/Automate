package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import texture.TextureLoader;
import texture.getRandomColor;
import world.Building;

public class ParticleManager
{
	ArrayList<Particle> particles;

	public ParticleManager()
	{
		particles = new ArrayList<Particle>();
	}

	public void addDestructionParticles(Building b)
	{
		addParticles(b.x, b.y, -0.5f, 0.03f, 0.03f, 0.07f, 0, 0, -0.002f, 50, 1000, TextureLoader.getTexture(b));
	}

	public void addDestructionParticles(float x, float y)
	{
		addParticles(x, y, -0.5f, 0.03f, 0.03f, 0.07f, 0, 0, -0.002f, 50, 1000, TextureLoader.pipes[0]);
	}

	public void addParticles(float x, float y, float speed, float accx, float accy, float lifetime, int n,
			BufferedImage b)
	{
		for (int i = 0; i < n; i++)
		{
			particles.add(new Particle(x, y, speed, accx, accy, lifetime, getRandomColor.randomColor(b)));
		}
	}

	public void addParticles(float x, float y, float z, float vx, float vy, float vz, float accx, float accy,
			float accz, float lifetime, int n, BufferedImage b)
	{
		for (int i = 0; i < n; i++)
		{
			particles.add(new Particle(x, y, z, vx, vy, vz, accx, accy, accz, lifetime, getRandomColor.randomColor(b)));
		}
	}

	public void update()
	{
		for (Particle p : particles)
		{
			p.update();
		}
		for (int i = particles.size() - 1; i >= 0; i--)
		{
			if (particles.get(i).lifetime < 0)
			{
				particles.remove(i);
			}
		}
	}

	public void render(Graphics g)
	{
		for (Particle p : particles)
		{
			p.render(g);
		}
	}
}
