package org.tiralab.particles.gui;

import org.tiralab.particles.particles.Particle;
import org.tiralab.particles.gui.Rectangle;

/**
 * This interface allows the App class to hook up code
 * into the gui events.
 */
public interface GuiListener {
	/**
	 * Get the string that is displayed at top of the GUI.
	 *
	 * @return The text shown in GUI.
	 */
	String     getHeaderText();

	/**
	 * Fetch particles in the view.
	 *
	 * @param x The x coordinate of the view
	 * @param y The y coordinate of the view
	 * @param w The width of the view
	 * @param h The height of the view
	 * @return Gives particles that are in the view.
	 */
	Particle[] fetchParticles(int x, int y, int w, int h);

	/**
	 * Get debug rectangles.
	 *
	 * @return A array of debug rectangles.
	 */
	Rectangle[] getDebugRectangles();

	/**
	 * Handle a key press.
	 *
	 * @param key The key as string.
	 */
	void keyPress(String key);
}
