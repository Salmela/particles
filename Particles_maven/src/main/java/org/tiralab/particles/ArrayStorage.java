package org.tiralab.particles;

/**
 * Array based particle storage.
 * TODO: Add dynamic resizing.
 */
public class ArrayStorage implements Storage {
	private ParticleArray[][] array;
	private Model model;
	private int size = 200, cellSize = 20;
	private int x, y;
	private boolean isEmpty;

	public ArrayStorage() {
	}

	private void reset() {
		this.array = new ParticleArray[size][size];
		this.x = this.y = 0;
		this.isEmpty = true;
	}

	public void setModel(Model model)
	{
		Particle[] particles;
		int i;

		reset();

		this.model = model;
		this.model.setStorage(this);

		this.updateParticles();
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
		ParticleSet set = new ParticleSet();


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

		return set.toArray();
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

		cellX = getCellPosition(particle.getPrevX(), true);
		cellY = getCellPosition(particle.getPrevY(), false);

		array = this.array[cellX][cellY];
		if(array == null || !array.remove(particle)) {
			System.out.println("The particle was attempted to be removed from incorrect cell.");
		}
	}

	public void updateParticles() {
		Particle[] array = model.getParticles();
		int i;

		for(i = 0; i < array.length; i++) {
			if(!this.isEmpty) {
				removeParticle(array[i]);
			}
			addParticle(array[i]);
			array[i].setOld();
		}
		this.isEmpty = false;
	}

	public int getMemoryConsumption() {
		return 0;
	}

	public Rectangle[] getDebugRectangle() {
		Rectangle[] rectangles;
		int i;

		rectangles = new Rectangle[this.size * 2];

		for(i = 0; i < this.size; i++) {
			rectangles[i * 2] = new Rectangle(0,
				i * this.cellSize,
				this.cellSize * this.size,
				this.cellSize);
			rectangles[i * 2 + 1] = new Rectangle(i * this.cellSize,
				0, this.cellSize,
				this.cellSize * this.size);
		}

		return rectangles;
	}
}
