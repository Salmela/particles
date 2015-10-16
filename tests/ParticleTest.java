package org.tiralab.particles;

import org.tiralab.particles.particles.Particle;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for particle class.
 */
public class ParticleTest {
	@Test
	public void constructorPositionX() {
		Particle p = new Particle(3.1f, 5f);
		Assert.assertEquals(3.1f, p.getX(), .01f);
	}

	@Test
	public void constructorPositionY() {
		Particle p = new Particle(5f, 9.2f);
		Assert.assertEquals(9.2f, p.getY(), .01f);
	}

	@Test
	public void setPositionX() {
		Particle p = new Particle(3.1f, 7.8f);
		p.set(6.9f, 1.2f);
		Assert.assertEquals(6.9f, p.getX(), .01f);
	}

	@Test
	public void setPositionY() {
		Particle p = new Particle(3.1f, 7.8f);
		p.set(6.9f, 1.2f);
		Assert.assertEquals(1.2f, p.getY(), .01f);
	}

	@Test
	public void setVelocityX() {
		Particle p = new Particle(3.1f, 7.8f);
		p.setVelocity(8.4f, 2.3f);
		Assert.assertEquals(8.4f, p.getVelocityX(), .01f);
	}

	@Test
	public void setVelocityY() {
		Particle p = new Particle(3.1f, 7.8f);
		p.setVelocity(8.4f, 2.3f);
		Assert.assertEquals(2.3f, p.getVelocityY(), .01f);
	}
}
