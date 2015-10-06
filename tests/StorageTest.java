package org.tiralab.particles;

import java.util.Random;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for Storage interface.
 * NOTE: This file is only used as abstract class for storage tests.
 */
public class StorageTest implements Model {
	Particle[] particles;
	Storage storage;

	public StorageTest() {
		this.particles = new Particle[0];
	}

	public Storage createInstance() {
		return null;
	}

	@Test
	public void checkThatStorageIsSetInModel() {
		Storage storage = createInstance();
		if(storage == null) return;

		storage.setModel(this);
		Assert.assertTrue(this.storage.equals(storage));
	}

	@Test
	public void testSingleParticle() {
		Storage storage = createInstance();
		Particle[] output;
		if(storage == null) return;

		this.particles = new Particle[1];
		this.particles[0] = new Particle(5.1f, 2.1f);

		storage.setModel(this);
		output = storage.getObjectsAtArea(0, 0, 10f, 10f);
		Assert.assertNotSame(null, output);
		Assert.assertEquals(1, output.length);
		Assert.assertEquals(this.particles[0], output[0]);
	}

	@Test
	public void testParticleAdd() {
		Storage storage = createInstance();
		Particle[] output;
		Particle p;
		boolean[] b;
		int i;
		if(storage == null) return;

		p = new Particle(2f, 3f);
		this.particles = new Particle[1];
		this.particles[0] = p;

		storage.setModel(this);

		b = new boolean[2];
		this.particles = new Particle[2];
		this.particles[0] = p;
		this.particles[1] = new Particle(6f, 1f);
		this.storage.addParticle(particles[1]);

		output = storage.getObjectsAtArea(0f, 0f, 10f, 10f);
		System.out.println(output.length);
		for(i = 0; i < output.length; i++) {
			if(output[i] == this.particles[0]) {
				b[0] = !b[0];
			} else if(output[i] == this.particles[1]) {
				b[1] = !b[1];
			}
		}
		Assert.assertTrue(b[0]);
		Assert.assertTrue(b[1]);
	}

	@Test
	public void testRandomParticle() {
		Storage storage = createInstance();
		Particle[] output;
		Random rand;
		int i;

		if(storage == null) return;

		this.particles = new Particle[1000];
		rand = new Random();
		for(i = 0; i < this.particles.length; i++) {
			this.particles[i] = new Particle(rand.nextFloat(), rand.nextFloat());
		}

		storage.setModel(this);

		for(i = 0; i < this.particles.length; i++) {
			int j;

			output = storage.getObjectsAtArea(particles[i].getX() - 5,
					particles[i].getX() - 5, 10f, 10f);
			Assert.assertNotSame(null, output);
			for(j = 0; j < output.length; j++) {
				if(output[j] == this.particles[i]) return;
			}
			Assert.assertEquals(this.particles[i], output[j]);
		}
	}

	@Override
	public Particle[] getParticles() {
		return this.particles;
	}

	@Override
	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	@Override
	public void simulate() {
	}

	@Override
	public void reset(long count) {
	}

	@Override
	public void reset() {
	}
}
