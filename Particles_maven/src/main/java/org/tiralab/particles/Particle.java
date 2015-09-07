package org.tiralab.particles;

public class Particle {
	private float x, y, radius;

	public Particle(float x, float y) {
		this.set(x, y);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}
}
