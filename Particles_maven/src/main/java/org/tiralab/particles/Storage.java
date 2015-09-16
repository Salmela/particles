package org.tiralab.particles;

/**
 * The acceleration structure.
 */
public interface Storage {
	/**
	 * Get all particles that are intersecting the rectangle(x,y,w,h).
	 */
	Particle[] getObjectsAtArea(float x, float y, float w, float h);

	/**
	 * Get nearest particle that is to point (x, y).
	 */
	Particle getObjectAtPoint(float x, float y);

	/**
	 * Set model of storage.
	 *
	 * The model is needed for getting all particles.
	 */
	void setModel(Model model);

	/* Signal handlers for the model */

	void addParticle(Particle particle);
	void removeParticle(Particle particle);
	void moveParticle(Particle particle);
}
