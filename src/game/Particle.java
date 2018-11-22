package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import utility.Vector2D;

public class Particle
{
	Color c;
	Polygon p;
	float x, y, z, r;
	float velx, vely, velz, velr;
	float accx, accy, accz;
	float lifetime;

	public Particle(float x, float y, float speed, float ax, float ay, float lf, Color c)
	{
		lifetime = (float) (Math.random() * lf);
		this.c = c;
		accx = (float) (ax * Math.random());
		accy = (float) (ay * Math.random());
		velx = (float) Math.random() * speed - (speed / 2);
		vely = (float) Math.random() * speed;
		velr = (float) Math.random() * 50 - 25;
		this.x = x + 0.725f;
		this.y = y - 0.075f;
		int[] xcoords = { 0 };
		int[] ycoords = { 0 };
		p = new Polygon(xcoords, ycoords, 1);
		p = new Polygon(xcoords, ycoords, 1);
	}

	public Particle(float x, float y, float z, float vx, float vy, float vz, float ax, float ay, float az, float lf,
			Color c)
	{
		lifetime = (float) (Math.random() * lf);
		this.c = c;
		accx = (float) (ax * Math.random());
		accy = (float) (ay * Math.random());
		accz = (float) (az * Math.random());
		velx = (float) Math.random() * vx - (vx / 2);
		vely = (float) Math.random() * vy - (vy / 2);
		velz = (float) (Math.random() / 2 + 0.5f) * vz;
		velr = (float) Math.random() * 50 - 25;
		this.x = x + 0.725f;
		this.y = y - 0.075f;
		this.z = z;
		int[] xcoords = { 0 };
		int[] ycoords = { 0 };
		p = new Polygon(xcoords, ycoords, 1);
		p = new Polygon(xcoords, ycoords, 1);
	}

	public void render(Graphics g)
	{
		g.setColor(c);
		g.fillPolygon(p);
	}

	public Vector2D rotatePoint(Vector2D pt, Vector2D center, float angle)
	{
		float angleRad = (float) ((angle / 180) * Math.PI);
		float cosAngle = (float) Math.cos(angleRad);
		float sinAngle = (float) Math.sin(angleRad);
		float dx = (pt.x - center.x);
		float dy = (pt.y - center.y);

		pt.x = center.x + (int) (dx * cosAngle - dy * sinAngle);
		pt.y = center.y + (int) (dx * sinAngle + dy * cosAngle);
		return pt;
	}

	public Polygon rotate(Polygon toRotate, float angle)
	{
		float x = toRotate.xpoints[0];
		float y = toRotate.ypoints[0];
		Vector2D center = new Vector2D(x + 2.5f, y + 5);
		for (int i = 0; i < toRotate.npoints; i++)
		{
			Vector2D pt = new Vector2D(toRotate.xpoints[i], toRotate.ypoints[i]);
			Vector2D rotated = rotatePoint(pt, center, angle);
			toRotate.xpoints[i] = (int) rotated.x;
			toRotate.ypoints[i] = (int) rotated.y;
		}
		return toRotate;
	}

	public void update()
	{
		float friction = 0.99f;
		velx += accx;
		vely += accy;
		velz += accz;
		velx *= friction;
		vely *= friction;
		velz *= friction;
		x += velx;
		y += vely;
		z += velz;
		r += velr;
		int size = (int) Simulation.map.t.getSize();
		float x = (Simulation.map.t.getX() + this.x) * size;
		float y = (Simulation.map.t.getY() + this.y) * size;
		float ix = (int) (x - y);
		float iy = (int) ((x + y) / 2f) - z * size;
		int[] xcoords = { (int) ix + 0, (int) ix + 5, (int) ix + 10 };
		int[] ycoords = { (int) iy + 0, (int) iy + 5, (int) iy + 0 };
		p = new Polygon(xcoords, ycoords, 3);
		p = rotate(p, r);
		lifetime--;
	}
}
