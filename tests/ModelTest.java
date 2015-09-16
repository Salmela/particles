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

	//TODO implement tests for these methods
	//Particle[] getParticles();
	//void simulate();

	@Test
	public void test() {
		Model model = createInstance();
		if(model == null) return;
	}
}