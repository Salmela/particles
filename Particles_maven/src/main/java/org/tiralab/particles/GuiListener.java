package org.tiralab.particles;

/**
 * This interface allows the App class to hook up code
 * into the gui events.
 */
public interface GuiListener {
	/**
	 * Get the string that is displayed at top of the gui.
	 */
	String     getHeaderText();

	/**
	 * Fetch particles in the view.
	 */
	Particle[] fetchParticles(int x, int y, int w, int h);

	/**
	 * Handle a key press.
	 */
	void keyPress(String key);
}
