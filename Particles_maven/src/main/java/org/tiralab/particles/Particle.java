package org.tiralab.particles;

public class Particle {
	private float x, y, vx, vy, radius;

	public Particle(float x, float y) {
		this.set(x, y);
	}

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public float getVelocityX() {
		return this.vx;
	}

	public float getVelocityY() {
		return this.vy;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void setVelocity(float x, float y) {
		this.vx = x;
		this.vy = y;
	}
}
