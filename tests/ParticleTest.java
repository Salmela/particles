package org.tiralab.particles;

import junit.framework.Assert;

/**
 * Unit test for particle class.
 */
public class ParticleTest {
	@Test
	public void constructorPositionX() {
		Particle p = new Particle(3.1, 5);
		Assert.assertEqual(p.getX(), 3.1);
	}

	@Test
	public void constructorPositionY() {
		Particle p = new Particle(5, 9.2);
		Assert.assertEqual(p.getY(), 9.2);
	}

	@Test
	public void setPositionX() {
		Particle p = new Particle(3.1, 7.8);
		p.set(6.9, 1.2);
		Assert.assertEqual(p.getX(), 6.9);
	}

	@Test
	public void setPositionY() {
		Particle p = new Particle(3.1, 7.8);
		p.set(6.9, 1.2);
		Assert.assertEqual(p.getY(), 1.2);
	}

	@Test
	public void setVelocityX() {
		Particle p = new Particle(3.1, 7.8);
		p.setVelocity(8.4, 2.3);
		Assert.assertEqual(p.getVelocityX(), 8.4);
	}

	@Test
	public void setVelocityY() {
		Particle p = new Particle(3.1, 7.8);
		p.setVelocity(8.4, 2.3);
		Assert.assertEqual(p.getVelocityY(), 2.3);
	}
}
