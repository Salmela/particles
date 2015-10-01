package org.tiralab.particles;

/**
 * This is dummy acceleration structure that just gets the particles
 * from the model and puts them into a array.
 */
public class DirectStorage implements Storage {
	private Particle[] array;
	private Model model;

	public DirectStorage() {
	}

	public void setModel(Model model)
	{
		this.model = model;
		this.model.setStorage(this);

		this.updateParticles();
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                               float w, float h) {
		return array;
	}

	public Particle getObjectAtPoint(float x, float y) {
		int i, min = -1;
		float min_dist = Float.MAX_VALUE;

		for(i = 0; i < array.length; i++) {
			Particle p = array[i];
			float dist;

			dist  = (p.getX() - x) * (p.getX() - x);
			dist += (p.getY() - y) * (p.getY() - y);

			if(dist < min_dist) {
				min_dist = dist;
				min = i;
			}
		}

		if(min == -1) return null;
		return array[min];
	}

	public void addParticle(Particle particle) {
		this.updateParticles();
	}

	public void removeParticle(Particle particle) {
		this.updateParticles();
	}

	public void updateParticles() {
		this.array = model.getParticles();
	}

	public int getMemoryConsumption() {
		return 0;
	}

	public Rectangle[] getDebugRectangle() {
		return new Rectangle[0];
	}
}
