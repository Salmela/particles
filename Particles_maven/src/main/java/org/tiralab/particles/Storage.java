package org.tiralab.particles;

public interface Storage {
	Particle[] getObjectsAtArea(float x, float y, float w, float h);
	Particle getObjectAtPoint(float x, float y);
	void setModel(Model model);
	void addParticle(Particle particle);
	void removeParticle(Particle particle);
	void moveParticle(Particle particle);
}
