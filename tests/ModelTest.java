package org.tiralab.particles;

import junit.framework.Assert;

/**
 * Unit test for model interface.
 */
public abstract class ModelTest {
	public abstract MyInterface createInstance();

	//Particle[] getParticles();
	//void simulate();

	@Test
	public void test() {
	}
}

public class StarsTest extends ModelTest {
	public Model createInstance() {
		return new Stars();
	}
}

public class FluidTest extends ModelTest {
	public Model createInstance() {
		return new Fluid();
	}
}
