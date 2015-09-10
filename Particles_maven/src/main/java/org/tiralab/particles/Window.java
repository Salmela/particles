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

	final int MOUSE_NONE = 0;
	final int MOUSE_ADD  = 1;
	final int MOUSE_MOVE = 2;
	final int MOUSE_ZOOM = 3;

	private GuiListener listener;
	private Image offscreenBuffer;
	private Graphics offscreenGraphics;

	/* These are for detecting size changes */
	private int oldWidth, oldHeight;
	private float zoom;
	private float x, y;

	/* view movement variables */
	private int mouseEvent;
	private boolean pointerDrag;
	private float dragStartX, dragStartY, zoomStart;
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
		this.zoom = 1.0f;

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
			this.zoomStart  = this.zoom;
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

		if(this.mouseEvent == MOUSE_MOVE) {
			this.x = this.dragStartX + (this.pointerX - e.getX()) / this.zoom;
			this.y = this.dragStartY + (this.pointerY - e.getY()) / this.zoom;
		} else if(this.mouseEvent == MOUSE_ZOOM) {
			this.zoom = this.zoomStart * (float)Math.pow(2.0, (this.pointerY - e.getY()) * .02);
		}
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
		switch(e.getButton()) {
		    case MouseEvent.BUTTON1:
			this.mouseEvent = MOUSE_ADD;
			break;
		    case MouseEvent.BUTTON3:
			this.mouseEvent = MOUSE_ZOOM;
			break;
		    case MouseEvent.BUTTON2:
			this.mouseEvent = MOUSE_MOVE;
			break;
		    default:
			this.mouseEvent = MOUSE_NONE;
			break;
		}
	}
	public void mouseReleased(MouseEvent e) {
		if(this.mouseEvent == MOUSE_ADD) {
			System.out.println("add particles");
		}
		this.pointerDrag = false;
		this.mouseEvent = MOUSE_NONE;
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
		float x_offset, y_offset;
		String text;
		Insets windowFrame;

		if(this.offscreenGraphics == null ||
		   this.oldWidth != getWidth() ||
		   this.oldHeight != getHeight()) {
			resized();
		}

		windowFrame   = getInsets();
		contentWidth  = this.getWidth() -
		                windowFrame.left - windowFrame.right;
		contentHeight = this.getHeight() -
		                windowFrame.top - windowFrame.left;

		if(!this.pointerDrag && this.mouseEvent == MOUSE_MOVE) {
			this.x += this.kineticMoveX * .1;
			this.y += this.kineticMoveY * .1;

			this.kineticMoveX *= 0.99f;
			this.kineticMoveY *= 0.99f;

		} else if(!this.pointerDrag &&
		          this.mouseEvent == MOUSE_ZOOM) {
			//this.zoom *= 1.0 + this.kineticMoveY * .1;
			//this.kineticMoveX  = 0;
			//this.kineticMoveY *= 0.99f;
		}

		x_offset = -x * this.zoom + windowFrame.left +
		           contentWidth / 2;
		y_offset = -y * this.zoom + windowFrame.top +
		           contentHeight / 2;

		g = this.offscreenGraphics;
		g.clearRect(0, 0, getWidth(), getHeight());

		particles = listener.fetchParticles((int)x, (int)y,
			contentWidth, contentHeight);

		for(i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			g.fillOval((int)(x_offset + p.getX() * this.zoom - 1),
			           (int)(y_offset + p.getY() * this.zoom - 1),
			           2, 2);
		}

		FontMetrics metrics = g.getFontMetrics();

		text = listener.getHeaderText();
		g.drawString(text,
		             windowFrame.left + contentWidth - metrics.stringWidth(text) - 8,
		             windowFrame.top + metrics.getHeight());

		graphics.drawImage(this.offscreenBuffer, 0, 0, this);

		this.repaint();
	}
}
