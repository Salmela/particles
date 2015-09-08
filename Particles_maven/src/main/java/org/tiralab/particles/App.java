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
		this.p = new Particle[256];

		for(i = 0; i < 256; i++) {
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

	public void simulate() {
		Random rand = new Random();
		int i;

		for(i = 0; i < p.length; i++) {
			Particle particle = p[i];
			float x, y;

			x = particle.getX() + rand.nextInt() % 2;
			y = particle.getY() + rand.nextInt() % 2;

			particle.set(x, y);
		}
	}

	public Particle[] fetchParticles(int x, int y, int w, int h) {
		simulate();
		return p;
	}
}
