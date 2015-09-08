package org.tiralab.particles;

import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.FontMetrics;
import java.awt.AWTEvent;

public class Window extends Frame implements MouseMotionListener, MouseListener {
	static final long serialVersionUID = 0x1424234725l;
	private GuiListener listener;
	private Image offscreenBuffer;
	private Graphics offscreenGraphics;

	/* These are for detecting size changes */
	private int oldWidth, oldHeight;
	private float x, y;

	/* view movement variables */
	private boolean pointerDrag;
	private float dragStartX, dragStartY;
	private float kineticMoveX, kineticMoveY;
	private int pointerX, pointerY;
	private int pointerPrevX, pointerPrevY;

	public Window(GuiListener listener) {
		this.setSize(128, 128);
		this.setResizable(true);
		this.setTitle("particles-demo");
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		this.listener = listener;

		pointerDrag = false;
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

	public void mouseDragged(MouseEvent e) {
		if(!this.pointerDrag) {
			this.dragStartX = this.x;
			this.dragStartY = this.y;
			this.pointerX = e.getX();
			this.pointerY = e.getY();

			this.pointerPrevX = e.getX();
			this.pointerPrevY = e.getY();
		}
		this.kineticMoveX = this.pointerPrevX - e.getX();
		this.kineticMoveY = this.pointerPrevY - e.getY();
		this.pointerPrevX = e.getX();
		this.pointerPrevY = e.getY();
		this.pointerDrag = true;

		this.x = this.dragStartX + this.pointerX - e.getX();
		this.y = this.dragStartY + this.pointerY - e.getY();
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
	}
	public void mouseReleased(MouseEvent e) {
		this.pointerDrag = false;
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

		if(!this.pointerDrag) {
			this.x += this.kineticMoveX * .1;
			this.y += this.kineticMoveY * .1;
			this.kineticMoveX *= 0.99f;
			this.kineticMoveY *= 0.99f;
		}

		g = this.offscreenGraphics;
		g.clearRect(0, 0, getWidth(), getHeight());

		windowFrame   = getInsets();
		contentWidth  = this.getWidth() -
		                windowFrame.left - windowFrame.right;
		contentHeight = this.getHeight() -
		                windowFrame.top - windowFrame.left;
		text = listener.getHeaderText();
		particles = listener.fetchParticles((int)x, (int)y,
			contentWidth, contentHeight);

		for(i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			g.fillOval(windowFrame.left - (int)x + (int)p.getX() - 1,
			           windowFrame.top - (int)y + (int)p.getY() - 1,
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
