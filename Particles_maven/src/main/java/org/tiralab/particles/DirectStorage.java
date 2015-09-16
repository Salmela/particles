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

		this.update();
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                               float w, float h) {
		return array;
	}

	public Particle getObjectAtPoint(float x, float y) {
		/*FIXME this is incorrect implementation */
		return array[0];
	}

	public void addParticle(Particle particle) {
		this.update();
	}

	public void removeParticle(Particle particle) {
		this.update();
	}

	public void moveParticle(Particle particle) {
		this.update();
	}

	private void update() {
		this.array = model.getParticles();
	}
}
