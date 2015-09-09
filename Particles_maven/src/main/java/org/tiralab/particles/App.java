package org.tiralab.particles;

import java.util.Random;

/**
 * This class handles the program logic for the gui.
 *
 */
public class App implements GuiListener {
	private Particle p[];

	public App() {
		Window window;

		System.out.println( "Starting...");
		window = new Window(this);
		window.setVisible(true);

		int i;
		Random rand = new Random();
		//this.p = new Particle[128];
		this.p = new Particle[256];

		for(i = 0; i < p.length; i++) {
			//this.p[i] = new Particle(
			//	Math.abs(rand.nextInt()) % 128,
			//	Math.abs(rand.nextInt()) % 128);
			//this.p[i].setVelocity(rand.nextInt() % 128,
			//                      rand.nextInt() % 128);

			this.p[i] = new Particle(
				Math.abs(rand.nextInt()) % 128,
				Math.abs(rand.nextInt()) % 128);
		}
	}

	public static void main(String[] args) {
		new App();
	}

	public String getHeaderText() {
		return "Test";
	}

	/* nbody simulation */
	/*TODO move this code to the model */
	public void simulate2() {
		int i, j;

		for(i = 0; i < p.length; i++) {
			Particle particle = p[i];
			float ax, ay, vx, vy, x, y;

			vx = particle.getVelocityX();
			vy = particle.getVelocityY();

			ax = ay = 0;

			/* compute gravitation of the other planets */
			for(j = 0; j < p.length; j++) {
				Particle another = p[j];
				float r2, dx, dy;

				if(i == j) continue;

				dx = another.getX() - particle.getX();
				dy = another.getY() - particle.getY();
				r2 = dx * dx + dy * dy;

				if(r2 > 0.01) {
					ax += dx / r2;
					ay += dy / r2;

				} else if(r2 != 0.0) {
					/* merge the particles */
					vx = vx + another.getVelocityX() / 2.0f;
					vy = vy + another.getVelocityY() / 2.0f;

					x = particle.getX() + another.getX() / 2.0f;
					y = particle.getY() + another.getY() / 2.0f;
					another.setVelocity(vx, vy);
					another.set(x, y);
					particle.set(x, y);
				}
			}

			vx = vx + ax * .5f;
			vy = vy + ay * .5f;
			particle.setVelocity(vx, vy);

			x = particle.getX() + vx * 0.01f;
			y = particle.getY() + vy * 0.01f;

			particle.set(x, y);
		}
	}

	private float force_polynome(float x) {
		return (-4 * x * x) + (12 * x) - 8;
	}

	/* water model simulation */
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

	public Particle[] fetchParticles(int x, int y, int w, int h) {
		simulate();
		return p;
	}
}
