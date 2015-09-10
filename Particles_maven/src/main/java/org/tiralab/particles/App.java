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

	/**
	 * Returns the string visible in the
	 * top-right corner of the window.
	 */
	public String getHeaderText() {
		return String.format("[%s] %d, %d",
			this.activeModel.getClass().getSimpleName(),
			(int)this.window.getScrollX(),
			(int)this.window.getScrollY());
	}

	/**
	 * Gets list of particles that are currently visible in the
	 * screen.
	 */
	public Particle[] fetchParticles(int x, int y, int w, int h) {
		/*TODO: move this to separate thread */
		activeModel.simulate();

		/*TODO: use acceleration structure here */
		return activeModel.getParticles();
	}
}
