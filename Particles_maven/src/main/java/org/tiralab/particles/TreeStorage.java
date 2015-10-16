package org.tiralab.particles;

/**
 * This acceleration structure stores the particles in quadtree.
 */
public class TreeStorage implements Storage {
	private Particle[] array;
	private Model model;
	private QuadTree root;
	private boolean isEmpty;
	private Rectangle[] debugRectangles;

	/**
	 * Implementation of quadtree.
	 *
	 * TODO Move this to separate file and create unit tests for it.
	 */
	private abstract class QuadTree {
		protected QuadTreeNode parent;
		protected float x, y, size;
		protected int childID;

		public static final int CHILD_TL = 0; /* top-left */
		public static final int CHILD_TR = 1; /* top-right */
		public static final int CHILD_BL = 2; /* bottom-left */
		public static final int CHILD_BR = 3; /* bottom-right */

		public QuadTree() {
		}

		public QuadTree(QuadTreeNode parent, int childID) {
			float r;

			this.parent = parent;
			this.childID = childID;
			if(parent == null) {
				this.size = 512.0f;
				this.x = 0f;
				this.y = 0f;
				return;
			} else {
				this.size = parent.size / 2.0f;
				this.x = parent.x;
				this.y = parent.y;
			}

			r = this.size / 2.0f;

			switch(childID) {
				case CHILD_TL:
					this.y -= r;
					this.x -= r;
					break;
				case CHILD_TR:
					this.y -= r;
					this.x += r;
					break;
				case CHILD_BL:
					this.y += r;
					this.x -= r;
					break;
				case CHILD_BR:
					this.y += r;
					this.x += r;
					break;
				default:
					System.out.println("Should not happen.");
					System.exit(1);
			}
		}

		/**
		 * Give the parent QuadTreeNode of this node.
		 * @return The parent
		 */
		public QuadTreeNode getParent() {
			return this.parent;
		}

		/**
		 * Add a rectangle, which represents this QuadTree node,
		 * to the array.
		 *
		 * @param array Pre-allocated array for the debug rectangles.
		 * @param index The index of the next unused rectangle
		 *              in the array.
		 * @return Gives the index of the next unused rectangle.
		 */
		public int getDebugRectangle(Rectangle[] array, int index) {
			float half;
			if(index >= array.length) return index;

			half = this.size / 2;
			array[index] = new Rectangle(this.x - half, this.y - half,
				this.size, this.size);

			return index + 1;
		}

		/**
		 * Check if a particle is inside the QuadTree.
		 *
		 * @param particle The checked particle
		 * @return True if it's inside.
		 */
		public boolean isInside(Particle particle) {
			float offsetX, offsetY;

			offsetX = particle.getX() - this.x + this.size / 2.0f;
			offsetY = particle.getY() - this.y + this.size / 2.0f;

			return (offsetX > 0.0 && offsetY > 0.0 &&
				offsetX <= this.size &&
				offsetY <= this.size);
		}

		abstract public void reset();
		abstract public void insert(Particle particle);
		abstract public void remove(Particle particle);
		abstract public void get(ParticleSet set, float x, float y, float width, float height);
	}

	private class QuadTreeLeaf extends QuadTree {
		private Particle[] particles;
		private int particleCount;

		public QuadTreeLeaf(QuadTreeNode parent, int childID) {
			super(parent, childID);

			this.particleCount = 0;
			this.particles = new Particle[16];
		}

		/**
		 * Remove all particles from this leaf.
		 */
		public void reset() {
			this.particleCount = 0;
		}

		/**
		 * Add particle to particle list.
		 */
		public void insert(Particle particle) {
			QuadTreeNode parent = this.getParent();

			/* replace this leaf with a node if this is full */
			if(this.particleCount >= 16) {
				QuadTreeNode node;
				int i;

				if(this.size < .0001) {
					System.out.println("QuadTree is too deep.");
					System.exit(1);
				}

				node = new QuadTreeNode(parent, this.childID);
				parent.replaceChild(this, node);
				for(i = 0; i < this.particleCount; i++) {
					node.insert(this.particles[i]);
				}
				node.insert(particle);
				return;
			}

			this.particles[this.particleCount] = particle;
			this.particleCount++;
		}

		/**
		 * Remove particle from particle list.
		 */
		public void remove(Particle particle) {
			Particle last;
			int i;

			for(i = 0; i < this.particleCount; i++) {
				if(this.particles[i] == particle) break;
			}
			if(i == this.particleCount) {
				System.out.println("The particle was attempted to be removed from incorrect leaf.");
				return;
			}

			/* replace the removed particle with last particle */
			last = this.particles[this.particleCount - 1];
			this.particles[i] = last;
			this.particleCount--;
		}

		public void get(ParticleSet set, float x, float y, float width, float height) {
			int i;

			for(i = 0; i < this.particleCount; i++) {
				set.add(this.particles[i]);
			}
		}
	}

	private class QuadTreeNode extends QuadTree {
		private QuadTree[] childs;

		public QuadTreeNode(float x, float y, float size) {
			super();
			this.childID = 0;
			this.x = x;
			this.y = y;
			this.size = size;
			this.parent = null;

			initializeChilds();
		}

		public QuadTreeNode(QuadTreeNode parent, int childID) {
			super(parent, childID);

			initializeChilds();
		}

		private void initializeChilds() {
			this.childs = new QuadTree[4];

			this.childs[0] = new QuadTreeLeaf(this, 0);
			this.childs[1] = new QuadTreeLeaf(this, 1);
			this.childs[2] = new QuadTreeLeaf(this, 2);
			this.childs[3] = new QuadTreeLeaf(this, 3);
		}

		QuadTreeNode createNewRoot(float particleX, float particleY) {
			QuadTreeNode root;
			float positionX, positionY, half;
			int childID;

			if(this.parent != null) {
				System.out.println("This method can be called only to root nodes.");
				System.exit(1);
			}

			positionX = this.x;
			positionY = this.y;
			half = this.size / 2.0f;
			childID = 0;

			if(particleX < this.x) {
				positionX -= half;
				childID += 1;
			} else {
				positionX += half;
			}

			if(particleY < this.y) {
				positionY -= half;
			} else {
				positionY += half;
				childID += 2;
			}

			root = new QuadTreeNode(positionX, positionY,
				this.size * 2.0f);
			root.childs[childID] = this;
			this.parent = root;

			return root;
		}

		/**
		 * Replace a child with another.
		 *
		 * This function is used for replacing QuadTreeLeaf with
		 * QuadTreeNode when the leaf is full.
		 *
		 * @param oldChild The old child.
		 * @param newChild The new child.
		 */
		public void replaceChild(QuadTree oldChild, QuadTree newChild) {
			int childID;

			if(oldChild.childID != newChild.childID) {
				System.out.println("New child must have same childID as the old one.");
				System.exit(1);
			}

			childID = oldChild.childID;
			if(this.childs[childID] != oldChild) {
				System.out.println("The tree doesn't have the old child.");
				System.exit(1);
			}

			this.childs[childID] = newChild;
		}

		/**
		 * Figure out which child contains the coorinate.
		 *
		 * @param x Point's x coordinate.
		 * @param y Point's y coordinate.
		 */
		private int getChildIdAt(float x, float y) {
			if(y > this.y) {
				if(x > this.x) {
					return CHILD_BR;
				} else {
					return CHILD_BL;
				}
			} else {
				if(x > this.x) {
					return CHILD_TR;
				} else {
					return CHILD_TL;
				}
			}
		}

		/**
		 * Remove all particles from the children.
		 */
		public void reset() {
			childs[0].reset();
			childs[1].reset();
			childs[2].reset();
			childs[3].reset();
		}

		public void insert(Particle particle) {
			int childID = getChildIdAt(
				particle.getX(), particle.getY());

			this.childs[childID].insert(particle);
		}

		public void remove(Particle particle) {
			int childID = getChildIdAt(
				particle.getPrevX(), particle.getPrevY());

			this.childs[childID].remove(particle);
		}

		public void get(ParticleSet set, float x, float y, float width, float height) {
			boolean getTop, getBottom, getLeft, getRight;

			getTop = (y < this.y);
			getBottom = (y + height > this.y);
			getLeft = (x < this.x);
			getRight = (x + width > this.x);

			if(getTop && getRight)
				childs[CHILD_TR].get(set, x, y, width, height);
			if(getTop && getLeft)
				childs[CHILD_TL].get(set, x, y, width, height);
			if(getBottom && getRight)
				childs[CHILD_BR].get(set, x, y, width, height);
			if(getBottom && getLeft)
				childs[CHILD_BL].get(set, x, y, width, height);
		}

		@Override
		public int getDebugRectangle(Rectangle[] array, int index) {
			index = super.getDebugRectangle(array, index);

			index = this.childs[0].getDebugRectangle(array, index);
			index = this.childs[1].getDebugRectangle(array, index);
			index = this.childs[2].getDebugRectangle(array, index);
			index = this.childs[3].getDebugRectangle(array, index);

			return index;
		}
	}

	public TreeStorage() {
		this.root = null;
		this.isEmpty = true;
		this.debugRectangles = new Rectangle[4096 * 32];
	}

	public void setModel(Model model)
	{
		this.model = model;
		this.model.setStorage(this);

		this.root = new QuadTreeNode(0.0f, 0.0f, 512.0f);
		this.isEmpty = true;

		this.updateParticles();
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                                   float w, float h) {
		ParticleSet set = new ParticleSet();

		if(this.root == null) return new Particle[0];

		this.root.get(set, x, y, w, h);

		return set.toArray();
	}

	public Particle getObjectAtPoint(float x, float y) {
		if(this.root == null) return null;
		return array[0];
	}

	public void expandRoot(Particle particle) {
		QuadTreeNode oldRoot = (QuadTreeNode)this.root;
		this.root = oldRoot.createNewRoot(particle.getX(),
			particle.getY());
	}

	public void addParticle(Particle particle) {
		if(this.root == null) return;

		while(!this.root.isInside(particle)) {
			this.expandRoot(particle);
		}

		this.root.insert(particle);
	}

	public void removeParticle(Particle particle) {
		if(this.root == null) return;
		this.root.remove(particle);
	}

	public void updateParticles() {
		Particle[] array;
		int i;

		if(this.root == null) return;
		array = model.getParticles();

		for(i = 0; i < array.length; i++) {
			if(!this.isEmpty) {
				this.removeParticle(array[i]);
			}
			this.addParticle(array[i]);
			array[i].setOld();
		}
		this.isEmpty = false;
	}

	public int getMemoryConsumption() {
		return -1;
	}

	public Rectangle[] getDebugRectangle() {
		Rectangle[] rects;
		int size;

		size = this.root.getDebugRectangle(this.debugRectangles, 0);
		rects = new Rectangle[size];

		System.arraycopy(this.debugRectangles, 0, rects, 0, size);
		return rects;
	}
}
