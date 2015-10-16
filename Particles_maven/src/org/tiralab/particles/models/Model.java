package org.tiralab.particles.models;

import org.tiralab.particles.storages.Storage;
import org.tiralab.particles.particles.Particle;

/**
 * The interface for model classes.
 *<p>
 * The classes, which implement Model interface, store the actual
 * particles. These classes will only have simple array like structure
 * for storing the particle. The acceleration structures have more
 * specialised getters for fetching particles at some specific region.
 *<p>
 * The interface allows Models to emit event about particle
 * changes to its acceleration structure.
 */
public interface Model {
	/**
	 * Reset the model.
	 * @param particleCount Number of the particles created.
	 */
	void reset(long particleCount);
	/**
	 * Reset the model with old parameters.
	 */
	void reset();

	/**
	 * Get all particles from model in array.
	 *
	 * @return Gives every particle in the model.
	 */
	Particle[] getParticles();

	/**
	 * Set the storage.
	 *
	 * The model sends particle change events to storage.
	 *
	 * @param storage The new storage object for the model
	 */
	void setStorage(Storage storage);

	/**
	 * Simulate single iteration of the simulation.
	 */
	void simulate();
}
