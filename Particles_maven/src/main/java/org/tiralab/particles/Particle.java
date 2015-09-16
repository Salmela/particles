package org.tiralab.particles;

/**
 * Store information about single particle.
 */
public class Particle {
	private float x, y, vx, vy, radius;

	/**
	 * This constructor allows you to set
	 * initial position of the particle.
	 */
	public Particle(float x, float y) {
		this.set(x, y);
	}

	/**
	 * Getter for the x coordinate of
	 * the particle position.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Getter for the y coordinate of
	 * the particle position.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Getter for the x coordinate of
	 * the particle velocity.
	 */
	public float getVelocityX() {
		return this.vx;
	}

	/**
	 * Getter for the y coordinate of
	 * the particle velocity.
	 */
	public float getVelocityY() {
		return this.vy;
	}

	/**
	 * Setter for the particle position.
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Setter for the particle velocity.
	 */
	public void setVelocity(float x, float y) {
		this.vx = x;
		this.vy = y;
	}
}
