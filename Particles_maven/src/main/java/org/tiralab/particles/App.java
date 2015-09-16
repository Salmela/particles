package org.tiralab.particles;

/**
 * This class handles the program logic for the gui.
 *
 */
public class App implements GuiListener {
	private Model activeModel;
	private Storage activeStorage;
	private Window window;

	public App() {
		this.window = new Window(this);
		this.window.setVisible(true);

		this.activeStorage = new DirectStorage();
		this.activeModel = new Stars();
		//this.activeModel = new Fluid();

		this.activeStorage.setModel(this.activeModel);
	}

	public static void main(String[] args) {
		new App();
	}

	public void keyPress(String key) {
		char k = key.toCharArray()[0];
		switch(k) {
			/*TODO switch model*/
			case 'M':
				break;
			/*TODO reset the model */
			case 'R':
				break;
			/*TODO switch acceleration structure */
			case 'A':
				break;
		}
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

		return this.activeStorage.getObjectsAtArea(x, y, w, h);
	}
}
