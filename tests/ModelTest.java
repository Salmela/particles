package org.tiralab.particles;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for model interface.
 * NOTE: This file is only used as abstract class for models tests.
 */
public class ModelTest {
	public Model createInstance() {
		return null;
	}

	@Test
	public void particleArrayNotNull() {
		Model model = createInstance();
		Particle[] particles;
		if(model == null) return;

		particles = model.getParticles();
		Assert.assertNotSame(null, particles);
	}

	@Test
	public void simulateDoesntCrash() {
		Model model = createInstance();
		if(model == null) return;

		model.simulate();
	}

	@Test
	public void simulationDoesntCrash() {
		Model model = createInstance();
		int i;

		if(model == null) return;

		for(i = 0; i < 100; i++) {
			model.simulate();
		}
	}
}
