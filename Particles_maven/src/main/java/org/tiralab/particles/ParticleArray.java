package org.tiralab.particles;

import java.util.Iterator;

/**
 * The dynamic array.
 *
 * The data structure is dynamically growing array. It doesn't retain
 * insertion order, because of the implementation remove.
 */
public class ParticleArray implements Iterable<Particle> {
	private Particle[] array;
	private int size;

	/**
	 * Iterator used for iterating the array.
	 */
	private class ParticleIterator implements Iterator<Particle> {
		private int index;

		public ParticleIterator() {
			this.index = 0;
		}
		public boolean hasNext() {
			int size = ParticleArray.this.size;
			return (this.index + 1 < size);
		}
		public Particle next() {
			Particle[] array = ParticleArray.this.array;
			return array[this.index++];
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Constructor that just creates empty array.
	 */
	public ParticleArray() {
		this.size = 0;
		this.array = new Particle[8];
	}

	/**
	 * Get a iterator for the array.
	 *
	 * @return The iterator for traversing Particle.
	 */
	public Iterator<Particle> iterator() {
		return new ParticleIterator();
	}

	/**
	 * Get the number of the particle.
	 *
	 * @return The size of the array.
	 */
	public int size() {
		return this.size;
	}

	/**
	 * Add a particle to the array.
	 *
	 * @param particle The particle
	 */
	public void add(Particle particle) {
		if(particle == null) return;

		/* increase the array */
		if(this.array.length == this.size) {
			Particle[] newArray;
			newArray = new Particle[this.size * 2];
			System.arraycopy(this.array, 0, newArray, 0,
				this.size);
			this.array = newArray;
		}
		this.array[size++] = particle;
	}

	/**
	 * Remove the particle from the array.
	 *
	 * This function changes the order of array, because
	 * the last particle is moved to the place of
	 * removed particle.
	 *
	 * @param particle The particle
	 */
	public void remove(Particle particle) {
		int index;

		index = this.getIndexOf(particle);
		if(index == -1) return;

		/* replace the removed particle with the last one */
		this.array[index] = this.array[--this.size];

		/* shrink the array */
		if(this.array.length == this.size * 2) {
			Particle[] newArray;
			newArray = new Particle[this.size];
			System.arraycopy(this.array, 0, newArray, 0,
				this.size);
			this.array = newArray;
		}
	}

	/**
	 * The array contains particle.
	 *
	 * @param particle The particle
	 * @return True if the array contains the particle.
	 */
	public boolean contains(Particle particle) {
		return (this.getIndexOf(particle) >= 0);
	}

	/**
	 * Get the index of the particle in the array.
	 *
	 * @param particle The particle
	 * @return
	 * Gives the index of the particle in the array.
	 * The function returns -1 if the array doesn't
	 * contain the particle.
	 */
	public int getIndexOf(Particle particle) {
		int i;

		if(particle == null) return -1;

		for(i = 0; i < this.size; i++) {
			if(this.array[i] == particle) return i;
		}
		return -1;
	}

	/**
	 * Get a plain array.
	 *
	 * @return The array.
	 */
	public Particle[] toArray() {
		Particle[] returnArray;

		returnArray = new Particle[this.size];
		System.arraycopy(this.array, 0, returnArray, 0,
			this.size);

		return returnArray;
	}
}
