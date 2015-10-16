package org.tiralab.particles;

import java.util.Iterator;
import org.junit.Assert;
import org.junit.Test;

public class ParticleArrayTest {
	public ParticleArrayTest() {
	}

    @Test
	public void addOne() {
		ParticleArray ar = new ParticleArray();
		Particle p = new Particle(0.0f, 0.0f);
		ar.add(p);
		Assert.assertTrue(ar.contains(p));
	}

    @Test
	public void addTwo() {
		ParticleArray ar = new ParticleArray();
		Particle p1 = new Particle(1.0f, 3.0f);
		Particle p2 = new Particle(5.0f, 2.0f);
		ar.add(p1);
		ar.add(p2);
		Assert.assertTrue(ar.contains(p1));
		Assert.assertTrue(ar.contains(p2));
	}

    @Test
	public void addAndRemove() {
		ParticleArray ar = new ParticleArray();
		Particle p = new Particle(1.0f, 3.0f);
		ar.add(p);
		ar.remove(p);
		Assert.assertFalse(ar.contains(p));
	}

    @Test
	public void addTwoAndRemoveFirst() {
		ParticleArray ar = new ParticleArray();
		Particle p1 = new Particle(1.0f, 3.0f);
		Particle p2 = new Particle(5.0f, 2.0f);
		ar.add(p1);
		ar.add(p2);
		ar.remove(p1);
		Assert.assertFalse(ar.contains(p1));
		Assert.assertTrue(ar.contains(p2));
	}

    @Test
	public void addTwoAndRemoveSecond() {
		ParticleArray ar = new ParticleArray();
		Particle p1 = new Particle(1.0f, 3.0f);
		Particle p2 = new Particle(5.0f, 2.0f);
		ar.add(p1);
		ar.add(p2);
		ar.remove(p2);
		Assert.assertTrue(ar.contains(p1));
		Assert.assertFalse(ar.contains(p2));
	}

    @Test
	public void addTwoAndIterator() {
		ParticleArray ar = new ParticleArray();
		Particle p1 = new Particle(1.0f, 3.0f);
		Particle p2 = new Particle(5.0f, 2.0f);
		ar.add(p1);
		ar.add(p2);
		Iterator<Particle> iterator = ar.iterator();
		iterator.next();
	}
}
