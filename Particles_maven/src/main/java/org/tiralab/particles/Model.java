package org.tiralab.particles;

/**
 * The classes, which implement Model interface, store the actual
 * particles. These classes will only have simple array like structure
 * for storing the particle. The acceleration structures have more
 * specialiced getters for fetching particles at some specific region.
 *
 * The interface allows Models to emit event about particle
 * changes to its acceleration structure.
 */
public interface Model {
	/**
	 * Get all particles from model in array.
	 */
	Particle[] getParticles();
	/**
	 * Set the storage.
	 *
	 * The model sends particle change events to storage.
	 */
	void setStorage(Storage storage);

	/**
	 * Simulate single iteration of the simulation.
	 */
	void simulate();
}
