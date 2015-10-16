package org.tiralab.particles;

import org.tiralab.particles.models.Model;
import org.tiralab.particles.models.Stars;

public class StarsTest extends org.tiralab.particles.ModelTest {
	@Override
	public Model createInstance() {
		return new Stars();
	}
}