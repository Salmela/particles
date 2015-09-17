package org.tiralab.particles;

/**
 * The acceleration structure.
 */
public interface Storage {
	/**
	 * Get at least all the particles that are intersecting the rectangle(x,y,w,h).
	 *
	 * @param x The x coordinate of the rectangle
	 * @param y The y coordinate of the rectangle
	 * @param w The width of the rectangle
	 * @param h The height of the rectangle
	 * @return Gives particles that are inside the rectangle.
	 */
	Particle[] getObjectsAtArea(float x, float y, float w, float h);

	/**
	 * Get the nearest particle that is to point (x, y).
	 *
	 * @param x The x coordinate of the point
	 * @param y The y coordinate of the point
	 * @return Gives the nearest particle.
	 */
	Particle getObjectAtPoint(float x, float y);

	/**
	 * Set model of storage.
	 *
	 * The model is needed for getting all particles.
	 * @param model The new model for the storage.
	 */
	void setModel(Model model);

	/* Signal handlers for the model */

	void addParticle(Particle particle);
	void removeParticle(Particle particle);
	void updateParticles();
}
