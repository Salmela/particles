package org.tiralab.particles;

/**
 * A immutable rectangle class.
 */
public class Rectangle {
	private float x, y, w, h;

	/**
	 * Constructor for setting all the class variables.
	 */
	public Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	/**
	 * Getter for the x coordinate.
	 *
	 * @return x coordinate.
	 */
	public float getX() {
		return this.x;
	}

	/**
	 * Getter for the y coordinate.
	 *
	 * @return y coordinate.
	 */
	public float getY() {
		return this.y;
	}

	/**
	 * Getter for the width of the rectangle.
	 *
	 * @return width.
	 */
	public float getWidth() {
		return this.w;
	}

	/**
	 * Getter for the height of the rectangle.
	 *
	 * @return height.
	 */
	public float getHeight() {
		return this.h;
	}
}
