package org.tiralab.particles;

/**
 * This class handles the program logic for the gui.
 *
 */
public class App implements GuiListener {
	private Model activeModel;
	private Window window;

	public App() {
		this.window = new Window(this);
		this.window.setVisible(true);

		this.activeModel = new Stars();
		//this.activeModel = new Fluid();
	}

	public static void main(String[] args) {
		new App();
	}

	public String getHeaderText() {
		return String.format("[%s] %d, %d",
			this.activeModel.getClass().getSimpleName(),
			(int)this.window.getScrollX(),
			(int)this.window.getScrollY());
	}

	public Particle[] fetchParticles(int x, int y, int w, int h) {
		activeModel.simulate();
		return activeModel.getParticles();
	}
}
