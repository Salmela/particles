package org.tiralab.particles;

public class TreeStorage implements Storage {
	private Particle[] array;
	private Model model;

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
			this.size = parent.size / 2.0f;
			this.x = parent.x;
			this.y = parent.y;

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

		abstract public void insert(Particle particle);
		abstract public void remove(Particle particle);
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
			if(this.particles[i] != particle) return;

			/* replace the removed particle with last particle */
			last = this.particles[this.particleCount - 1];
			this.particles[i] = last;
			this.particleCount--;
		}
	}

	private class QuadTreeNode extends QuadTree {
		private QuadTree[] childs;

		public QuadTreeNode(QuadTreeNode parent, int childID) {
			super(parent, childID);

			this.childs = new QuadTree[4];

			this.childs[0] = new QuadTreeLeaf(this, 0);
			this.childs[1] = new QuadTreeLeaf(this, 1);
			this.childs[2] = new QuadTreeLeaf(this, 2);
			this.childs[3] = new QuadTreeLeaf(this, 3);
		}

		public void replaceChild(QuadTree oldChild, QuadTree newChild) {
			int i;

			for(i = 0; i < 4; i++) {
				if(this.childs[i] == oldChild) {
					this.childs[i] = newChild;
				}
			}
		}

		public void insert(Particle particle) {
			/*TODO implement */
		}

		public void remove(Particle particle) {
			/*TODO implement */
		}
	}

	public TreeStorage() {
	}

	public void setModel(Model model)
	{
		this.model = model;
		this.model.setStorage(this);

		this.updateParticles();
	}

	public Particle[] getObjectsAtArea(float x, float y,
	                               float w, float h) {
		return array;
	}

	public Particle getObjectAtPoint(float x, float y) {
		return array[0];
	}

	public void addParticle(Particle particle) {
		this.updateParticles();
	}

	public void removeParticle(Particle particle) {
		this.updateParticles();
	}

	public void updateParticles() {
		this.array = model.getParticles();
	}
}
