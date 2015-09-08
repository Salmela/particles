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
		this.p = new Particle[128];

		for(i = 0; i < p.length; i++) {
			this.p[i] = new Particle(
				Math.abs(rand.nextInt()) % 128,
				Math.abs(rand.nextInt()) % 128);
			this.p[i].setVelocity(rand.nextInt() % 128,
			                      rand.nextInt() % 128);
		}
	}

	public static void main(String[] args) {
		new App();
	}

	public String getHeaderText() {
		return "Test";
	}

	/*TODO move this code to the model */
	public void simulate() {
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

	public Particle[] fetchParticles(int x, int y, int w, int h) {
		simulate();
		return p;
	}
}
