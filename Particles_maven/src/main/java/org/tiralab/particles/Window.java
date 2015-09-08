package org.tiralab.particles;

import java.awt.event.WindowEvent;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.FontMetrics;
import java.awt.AWTEvent;

public class Window extends Frame {
	static final long serialVersionUID = 0x1424234725l;
	private GuiListener listener;
	private Image offscreenBuffer;
	private Graphics offscreenGraphics;

	/* These are for detecting size changes */
	private int oldWidth, oldHeight;

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

	private void resized() {
		this.offscreenBuffer = createImage(getWidth(), getHeight());
		this.offscreenGraphics = offscreenBuffer.getGraphics();

		this.oldWidth = getWidth();
		this.oldHeight = getHeight();
	}

	public void update(Graphics g){
		paint(g);
	}

	public void paint(Graphics graphics) {
		Graphics g;
		Particle[] particles;
		int contentWidth, contentHeight, i;
		String text;
		Insets windowFrame;

		if(this.offscreenGraphics == null ||
		   this.oldWidth != getWidth() ||
		   this.oldHeight != getHeight()) {
			resized();
		}

		g = this.offscreenGraphics;
		g.clearRect(0, 0, getWidth(), getHeight());

		windowFrame   = getInsets();
		contentWidth  = this.getWidth() -
		                windowFrame.left - windowFrame.right;
		contentHeight = this.getHeight() -
		                windowFrame.top - windowFrame.left;
		text = listener.getHeaderText();
		particles = listener.fetchParticles(0, 0,
			contentWidth, contentHeight);

		for(i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			g.fillOval(windowFrame.left + (int)p.getX() - 1,
			           windowFrame.top + (int)p.getY() - 1,
			           2, 2);
		}

		FontMetrics metrics = g.getFontMetrics();

		g.drawString(text,
		             windowFrame.left + contentWidth - metrics.stringWidth(text) - 8,
		             windowFrame.top + metrics.getHeight());

		graphics.drawImage(this.offscreenBuffer, 0, 0, this);

		this.repaint();
	}
}
