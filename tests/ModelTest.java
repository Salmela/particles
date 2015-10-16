package org.tiralab.particles;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for model interface.
 * NOTE: This file is only used as abstract class for models tests.
 */
public class ModelTest implements Storage {
	public Model createInstance() {
		return null;
	}

	@Test
	public void particleArrayNotNull() {
		Model model = createInstance();
		Particle[] particles;
		if(model == null) return;

		model.setStorage(this);
		particles = model.getParticles();
		Assert.assertNotSame(null, particles);
	}

	@Test
	public void simulateDoesntCrash() {
		Model model = createInstance();
		if(model == null) return;

		model.setStorage(this);
		model.simulate();
	}

	@Test
	public void simulationDoesntCrash() {
		Model model = createInstance();
		int i;
		if(model == null) return;

		model.setStorage(this);
		for(i = 0; i < 100; i++) {
			model.simulate();
		}
	}

	@Override
	public Particle[] getObjectsAtArea(float x, float y, float w, float h) {
		return null;
	}

	@Override
	public Particle getObjectAtPoint(float x, float y) {
		return null;
	}

	@Override
	public void setModel(Model model) {
		model.setStorage(this);
	}

	@Override
	public int getMemoryConsumption() {
		return 0;
	}

	@Override
	public Rectangle[] getDebugRectangle() {
		return null;
	}

	@Override
	public void addParticle(Particle particle) {
	}

	@Override
	public void removeParticle(Particle particle) {
	}

	@Override
	public void updateParticles() {
	}
}
