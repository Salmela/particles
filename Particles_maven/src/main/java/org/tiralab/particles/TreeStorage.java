package org.tiralab.particles;

import java.util.HashSet;

/**
 * This acceleration structure stores the particles in quadtree.
 */
public class TreeStorage implements Storage {
	private Particle[] array;
	private Model model;
	private QuadTree root;

	/**
	 * Implementation of quadtree.
	 *
	 * TODO Move this to separate file and create unit tests for it.
	 */
	private abstract class QuadTree {
		protected QuadTreeNode parent;
		protected float x, y, size;
		protected int childID;

		public static final int CHILD_TR = 0; /* top-right */
		public static final int CHILD_TL = 1; /* top-left */
		public static final int CHILD_BR = 2; /* bottom-right */
		public static final int CHILD_BL = 3; /* bottom-left */

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
				case CHILD_TR:
					this.y += r;
					this.x += r;
					break;
				case CHILD_TL:
					this.y += r;
					this.x -= r;
					break;
				case CHILD_BR:
					this.y -= r;
					this.x += r;
					break;
				case CHILD_BL:
					this.y -= r;
					this.x -= r;
					break;
				default:
					System.out.println("Should not happen.");
					System.exit(1);
			}
		}

		QuadTreeNode  getParent() {
			return this.parent;
		}

		abstract public void reset();
		abstract public void insert(Particle particle);
		abstract public void remove(Particle particle);
		abstract public void get(HashSet<Particle> set, float x, float y, float width, float height);
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
				System.out.println("The particle was attempted to be removed from incorrect leaf." + particle.getPrevX() +","+ particle.getPrevX());
				//System.exit(-1);
				return;
			}

			/* replace the removed particle with last particle */
			last = this.particles[this.particleCount - 1];
			this.particles[i] = last;
			this.particleCount--;
		}

		public void get(HashSet<Particle> set, float x, float y, float width, float height) {
			int i;

			for(i = 0; i < this.particleCount; i++) {
				set.add(this.particles[i]);
			}
		}
	}

	private class QuadTreeNode extends QuadTree {
		private QuadTree[] childs;

		public QuadTreeNode() {
			this(null, 0);
		}
		public QuadTreeNode(QuadTreeNode parent, int childID) {
			super(parent, childID);

			this.childs = new QuadTree[4];

			this.childs[0] = new QuadTreeLeaf(this, 0);
			this.childs[1] = new QuadTreeLeaf(this, 1);
			this.childs[2] = new QuadTreeLeaf(this, 2);
			this.childs[3] = new QuadTreeLeaf(this, 3);
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
					return CHILD_TR;
				} else {
					return CHILD_TL;
				}
			} else {
				if(x > this.x) {
					return CHILD_BR;
				} else {
					return CHILD_BL;
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
				particle.getX(), particle.getY());

			this.childs[childID].remove(particle);
		}

		public void get(HashSet<Particle> set, float x, float y, float width, float height) {
			boolean getTop, getBottom, getLeft, getRight;

			getTop = (y + height > this.y);
			getBottom = (y < this.y);
			getLeft = (x + width > this.x);
			getRight = (x < this.x);

			if(getTop && getRight)
				childs[CHILD_TR].get(set, x, y, width, height);
			if(getTop && getLeft)
				childs[CHILD_TL].get(set, x, y, width, height);
			if(getBottom && getRight)
				childs[CHILD_BR].get(set, x, y, width, height);
			if(getBottom && getLeft)
				childs[CHILD_BL].get(set, x, y, width, height);
		}
	}

	public TreeStorage() {
		this.root = new QuadTreeNode();
	}

	public void setModel(Model model)
	{
		this.model = model;
		this.model.setStorage(this);

		this.updateParticles();
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                                   float w, float h) {
		HashSet<Particle> set = new HashSet<Particle>();

		this.root.get(set, x, y, w, h);

		return set.toArray(new Particle[0]);
	}

	public Particle getObjectAtPoint(float x, float y) {
		return array[0];
	}

	public void addParticle(Particle particle) {
		this.root.insert(particle);
	}

	public void removeParticle(Particle particle) {
		this.root.remove(particle);
	}

	public void updateParticles() {
		Particle[] array;
		int i;

		this.root.reset();
		array = model.getParticles();

		for(i = 0; i < array.length; i++) {
			this.root.insert(array[i]);
		}
	}
}
