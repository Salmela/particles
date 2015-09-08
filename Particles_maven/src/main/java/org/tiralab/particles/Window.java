package org.tiralab.particles;

import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.AWTEvent;

public class Window extends Frame {
	static final long serialVersionUID = 0x1424234725l;
	private GuiListener listener;

	public Window(GuiListener listener) {
		this.setSize(128, 128);
		this.setResizable(true);
		this.setTitle("particles-demo");
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.listener = listener;
	}

	protected void processWindowEvent(WindowEvent event) {
		switch(event.getID()) {
			case WindowEvent.WINDOW_CLOSING:
				this.dispose();
				break;
			default:
				System.out.println(event);
				break;
		}
	}

	public void paint(Graphics g) {
		Particle[] particles;
		int i, w, h;
		Insets windowFrame;

		windowFrame = getInsets();
		w = this.getWidth() - windowFrame.left - windowFrame.right;
		h = this.getHeight() - windowFrame.top - windowFrame.left;
		particles = listener.fetchParticles(0, 0, w, h);

		for(i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			g.fillOval(windowFrame.left + (int)p.getX() - 1,
			           windowFrame.top + (int)p.getY() - 1,
			           2, 2);
		}
	}
}
