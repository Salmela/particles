package org.tiralab.particles;

public class ParticleSet {
	private ParticleArray[] array;
	private int size;

	public ParticleSet() {
		this.size = 0;
		this.array = new ParticleArray[8];
	}

	private void resize() {
		ParticleArray[] oldArray;
		int i;

		oldArray = this.array;
		this.array = new ParticleArray[oldArray.length * 2];

		for(i = 0; i < oldArray.length; i++) {
			if(oldArray[i] == null) continue;
			for(Particle particle : oldArray[i]) {
				this.add(particle);
			}
		}
	}

	public void add(Particle particle) {
		ParticleArray bucket;
		int hash;

		if(this.size >= this.array.length * 3 / 4) {
			resize();
		}

		hash = particle.hashCode();
		hash %= this.array.length;

		bucket = this.array[hash];
		if(bucket == null) {
			bucket = new ParticleArray();
			this.array[hash] = bucket;
		}

		if(bucket.contains(particle)) return;
		bucket.add(particle);
	}

	public Particle[] toArray() {
		ParticleArray array = new ParticleArray();
		int i;

		for(i = 0; i < this.array.length; i++) {
			if(this.array[i] == null) continue;

			for(Particle particle : this.array[i]) {
				array.add(particle);
			}
		}

		return array.toArray();
	}
}
