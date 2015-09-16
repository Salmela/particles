package org.tiralab.particles;

/**
 * Store information about single particle.
 */
public class Particle {
	private float x, y, vx, vy, radius;

	/**
	 * This constructor allows you to set
	 * initial position of the particle.
	 *
	 * @param x The initial x coordinate.
	 * @param y The initial y coordinate.
	 */
	public Particle(float x, float y) {
		this.set(x, y);
	}

	/**
	 * Getter for the x coordinate of
	 * the particle.
	 *
	 * @return Gives the x coordinate.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Getter for the y coordinate of
	 * the particle.
	 *
	 * @return Gives the y coordinate.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Getter for the x component of
	 * the particle velocity.
	 *
	 * @return Gives the x component of the velocity.
	 */
	public float getVelocityX() {
		return this.vx;
	}

	/**
	 * Getter for the y component of
	 * the particle velocity.
	 *
	 * @return Gives the y component of the velocity.
	 */
	public float getVelocityY() {
		return this.vy;
	}

	/**
	 * Setter for the particle position.
	 *
	 * @param x The new x coordinate.
	 * @param y The new y coordinate.
	 */
	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Setter for the particle velocity.
	 *
	 * @param x The new x component of the velocity.
	 * @param y The new y component of the velocity.
	 */
	public void setVelocity(float x, float y) {
		this.vx = x;
		this.vy = y;
	}
}
