package org.tiralab.particles;

public interface Model {
	Particle[] getParticles();
	void setStorage(Storage storage);
	void simulate();
}
