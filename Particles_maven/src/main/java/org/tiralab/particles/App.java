package org.tiralab.particles;

/**
 * This class handles the program logic for the gui.
 *
 */
public class App implements GuiListener {
	Model active_model;

	public App() {
		Window window;

		window = new Window(this);
		window.setVisible(true);

		//this.active_model = new Stars();
		this.active_model = new Fluid();
	}

	public static void main(String[] args) {
		new App();
	}

	public String getHeaderText() {
		return "Test";
	}

	public Particle[] fetchParticles(int x, int y, int w, int h) {
		active_model.simulate();
		return active_model.getParticles();
	}
}
