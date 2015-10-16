package org.tiralab.particles.main;

import org.tiralab.particles.storages.ArrayStorage;
import org.tiralab.particles.storages.DirectStorage;
import org.tiralab.particles.models.Fluid;
import org.tiralab.particles.models.Model;
import org.tiralab.particles.models.Stars;
import org.tiralab.particles.storages.Storage;
import org.tiralab.particles.storages.TreeStorage;
import org.tiralab.particles.gui.GuiListener;
import org.tiralab.particles.particles.Particle;
import org.tiralab.particles.gui.Rectangle;
import org.tiralab.particles.gui.Window;

/**
 * This class handles the program logic for the gui.
 *
 */
public class App implements GuiListener {
	private Model models[], activeModel;
	private Storage storages[], activeStorage;
	private Window window;

	public App() {
		this.models = new Model[2];
		this.storages = new Storage[3];

		this.models[0] = new Stars();
		this.models[1] = new Fluid();
		this.storages[0] = new DirectStorage();
		this.storages[1] = new ArrayStorage();
		this.storages[2] = new TreeStorage();

		this.activeStorage = this.storages[0];
		this.activeModel = this.models[0];

		this.activeStorage.setModel(this.activeModel);

		this.window = new Window(this);
		this.window.setVisible(true);
	}

	public static void main(String[] args) {
		new App();
	}

	public void keyPress(String key) {
		char k = key.toCharArray()[0];
		switch(k) {
			/* switch model*/
			case 'M':
				this.switchModel();
				break;
			/* reset the model */
			case 'R':
				this.activeModel.reset();
				break;
			/* switch acceleration structure */
			case 'A':
				this.switchStorage();
				break;
			case 'V':
				this.window.setDebugMode(
					!this.window.getDebugMode());
				break;
		}
	}

	private void switchModel() {
		Model model = this.models[0];
		int i;

		/* get next model */
		for(i = 0; i < this.models.length - 1; i++) {
			if(this.models[i] == this.activeModel) {
				model = this.models[i + 1];
			}
		}
		this.activeModel = model;
		this.activeStorage.setModel(this.activeModel);
	}

	private void switchStorage() {
		Storage storage = this.storages[0];
		int i;

		/* get next acceleration structure */
		for(i = 0; i < this.storages.length - 1; i++) {
			if(this.storages[i] == this.activeStorage) {
				storage = this.storages[i + 1];
			}
		}
		this.activeStorage = storage;
		this.activeStorage.setModel(this.activeModel);
	}

	/**
	 * Returns the string visible in the
	 * top-right corner of the window.
	 */
	public String getHeaderText() {
		int size = this.activeStorage.getMemoryConsumption();

		return String.format("FPS: %d, [%s/%s] (%d, %d) %dB",
			(int)this.window.getFPS(),
			this.activeModel.getClass().getSimpleName(),
			this.activeStorage.getClass().getSimpleName(),
			(int)this.window.getScrollX(),
			(int)this.window.getScrollY(),
			size);
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

	/**
	 * Give the debug rectangle.
	 */
	public Rectangle[] getDebugRectangles() {
		return this.activeStorage.getDebugRectangle();
	}
}
