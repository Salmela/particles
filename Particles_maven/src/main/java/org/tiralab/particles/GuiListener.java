package org.tiralab.particles;

public interface GuiListener {
	String     getHeaderText();
	Particle[] fetchParticles(int x, int y, int w, int h);
}
