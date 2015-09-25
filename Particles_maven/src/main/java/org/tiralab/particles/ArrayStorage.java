package org.tiralab.particles;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Array based particle storage.
 * TODO: Add dynamic resizing.
 */
public class ArrayStorage implements Storage {
	private ParticleArray[][] array;
	private Model model;
	private int size = 200, cellSize = 20;
	private int x, y;

	public ArrayStorage() {
	}

	private void reset() {
		this.array = new ParticleArray[size][size];
		this.x = this.y = 0;
	}

	public void setModel(Model model)
	{
		Particle[] particles;
		int i;

		reset();

		this.model = model;
		this.model.setStorage(this);

		particles = model.getParticles();
		for(i = 0; i < particles.length; i++) {
			this.addParticle(particles[i]);
		}
	}

	private int getCellPosition(float d, boolean horizontal) {
		int wd, cell;

		wd = horizontal ? this.x : this.y;
		cell = (int)Math.floor(d / cellSize) + wd;

		if(cell < 0) cell = 0;
		if(cell >= this.size) cell = this.size - 1;

		return cell;
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                                   float w, float h) {
		int cellX, cellY, endX, endY;
		int i, j;
		HashSet<Particle> set = new HashSet<Particle>();


		cellX = getCellPosition(x, true);
		cellY = getCellPosition(y, false);

		endX = getCellPosition(x + w, true);
		endY = getCellPosition(y + h, false);

		for(i = cellX; i <= endX; i++) {
			for(j = cellY; j <= endY; j++) {
				ParticleArray cellArray;

				cellArray = array[i][j];
				if(cellArray == null) continue;

				for(Particle p : cellArray) {
					set.add(p);
				}
			}
		}

		return set.toArray(new Particle[0]);
	}

	public Particle getObjectAtPoint(float x, float y) {
		return null;
	}

	public void addParticle(Particle particle) {
		ParticleArray array;
		int cellX, cellY;

		cellX = getCellPosition(particle.getX(), true);
		cellY = getCellPosition(particle.getY(), false);

		array = this.array[cellX][cellY];
		if(array == null) {
			array = new ParticleArray();
			this.array[cellX][cellY] = array;
		}
		array.add(particle);
	}

	public void removeParticle(Particle particle) {
		ParticleArray array;
		int cellX, cellY;

		cellX = getCellPosition(particle.getX(), true);
		cellY = getCellPosition(particle.getY(), false);

		array = this.array[cellX][cellY];
		if(array == null) {
			/* This should never happen except at start. */
			return;
		}
		array.remove(particle);
	}

	public void updateParticles() {
		Particle[] array = model.getParticles();
		int i;

		for(i = 0; i < array.length; i++) {
			removeParticle(array[i]);
			addParticle(array[i]);
		}
	}
}
