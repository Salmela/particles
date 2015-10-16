package org.tiralab.particles;

import org.tiralab.particles.models.Model;
import org.tiralab.particles.models.Fluid;

public class FluidTest extends ModelTest {
	@Override
	public Model createInstance() {
		return new Fluid();
	}
}