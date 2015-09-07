package org.tiralab.particles;

import java.util.Random;

import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.AWTEvent;

public class Window extends Frame {
	static final long serialVersionUID = 0x1424234725l;
	private Particle p[];

	public Window() {
		this.setSize(128, 128);
		this.setResizable(true);
		this.setTitle("particles-demo");
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);

		int i;
		Random rand = new Random();
		this.p = new Particle[256];

		for(i = 0; i < 256; i++) {
			this.p[i] = new Particle(
				Math.abs(rand.nextInt()) % 128,
				Math.abs(rand.nextInt()) % 128);
		}
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
		int i;

		for(i = 0; i < 255; i++) {
			Particle p = this.p[i];
			g.fillOval((int)p.getX() - 1,
			           (int)p.getY() - 1,
			           2, 2);
		}
	}
}
