package org.tiralab.particles;

import java.util.Random;

public class Fluid implements Model {
	private Particle p[];
	private Storage storage;

	public Fluid() {
		int i;
		Random rand = new Random();

		this.p = new Particle[256];

		for(i = 0; i < p.length; i++) {
			this.p[i] = new Particle(
				rand.nextInt() % 64,
				rand.nextInt() % 64);
		}
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public Particle[] getParticles() {
		return p;
	}

	/**
	 * Returns value of nice polynome at x. This
	 * is used in computing the force created by
	 * other particle.
	 */
	private float force_polynome(float x) {
		return (-4 * x * x) + (12 * x) - 8;
	}

	public void simulate() {
		final float radius = 50.0f;
		final float force  = .0005f;
		int i, j;

		for(i = 0; i < p.length; i++) {
			Particle particle = p[i];
			float ax, ay, vx, vy, x, y;

			vx = particle.getVelocityX();
			vy = particle.getVelocityY();

			/* destroy oscillation */
			vx *= 0.95f;
			vy *= 0.95f;

			ax = ay = 0;

			/* compute gravitation of the other planets */
			for(j = 0; j < p.length; j++) {
				Particle another = p[j];
				float r2, dx, dy;

				if(i == j) continue;

				dx = another.getX() - particle.getX();
				dy = another.getY() - particle.getY();
				r2 = dx * dx + dy * dy;

				if(r2 > 4 * radius * radius) {
					/* do nothing if it's too far */
				} else if(r2 > 0.001) {
					float r, f;

					r = (float)Math.sqrt((double)r2) / radius;
					/* even particle distribution */
					//f = force_polynome(r);
					/* with surface tension */
					f = (r - 1);
					//if(f < 0) f *= 100;

					//System.out.println("spring "+i+"-"+j + ", force "+f+", radius "+r);

					ax += dx / r * f * force;
					ay += dy / r * f * force;

				}
			}

			ay += 1.2f;

			float f, d = 300 - particle.getY();
			if(d > radius) {
			} else if(d < 0) {
				ay = 0;
				vy = 0;
			} else {
				d /= radius;
				d = 1 - d;
				f = d * d * 5;

				ay -= 1 * f;
			}

			vx = vx + ax;
			vy = vy + ay;
			particle.setVelocity(vx, vy);

			x = particle.getX() + vx * 0.01f;
			y = particle.getY() + vy * 0.01f;

			particle.set(x, y);
		}
	}
}
