package org.tiralab.particles;

import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;
import java.awt.FontMetrics;
import java.awt.AWTEvent;

/**
 * Window class renders the whole gui.
 */
public class Window extends Frame implements MouseMotionListener, MouseListener, KeyListener {
	static final long serialVersionUID = 0x1424234725l;

	final int MOUSE_NONE = 0;
	final int MOUSE_ADD  = 1;
	final int MOUSE_MOVE = 2;
	final int MOUSE_ZOOM = 3;

	private GuiListener listener;
	private Image offscreenBuffer;
	private Graphics offscreenGraphics;
	private boolean paused;

	private long prevFrameTime;
	private long prevInputTime;
	private float fps;

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
		this.addKeyListener(this);
		this.listener = listener;
		this.zoom = 1.0f;
		this.paused = false;

		this.pointerDrag = false;
		this.prevFrameTime = System.currentTimeMillis();
		this.prevInputTime = this.prevFrameTime;
	}

	/**
	 * Handle the window close event and activate/deactivate events.
	 */
	protected void processWindowEvent(WindowEvent event) {
		switch(event.getID()) {
			case WindowEvent.WINDOW_CLOSING:
				this.dispose();
				break;
			case WindowEvent.WINDOW_ACTIVATED:
				this.paused = false;
				this.repaint();
				break;
			case WindowEvent.WINDOW_DEACTIVATED:
				this.paused = true;
				break;
			default:
				break;
		}
	}

	/**
	 * Update the kinetic variables when mouse moves.
	 *
	 * @param e The mouse event.
	 */
	private void mouseInputUpdate(MouseEvent e) {
		float kx, ky;
		long currentTime, timeDelta;

		/* initialize values as zeroes when the drag starts */
		if(!this.pointerDrag) {
			this.kineticMoveX = 0f;
			this.kineticMoveY = 0f;
			return;
		}

		/* Compute time difference between previous
		 * update and current */
		currentTime = System.currentTimeMillis();
		timeDelta = currentTime - this.prevInputTime;
		this.prevInputTime = currentTime;

		if(timeDelta == 0) return;

		kx = this.pointerPrevX - e.getX();
		ky = this.pointerPrevY - e.getY();

		kx /= timeDelta;
		ky /= timeDelta;

		/* change the kinetic values slowly */
		this.kineticMoveX = kx * .01f + this.kineticMoveX * .99f;
		this.kineticMoveY = ky * .01f + this.kineticMoveY * .99f;
	}

	public int computeTimeDelta() {
		long currentTime, timeDelta;

		currentTime = System.currentTimeMillis();
		timeDelta = currentTime - this.prevFrameTime;
		this.prevFrameTime = currentTime;

		return (int)timeDelta;
	}

	/**
	 * Apply the kinetic move variables to view position or zoom.
	 */
	private void applyKineticMotion(int timeDelta) {
		if(this.mouseEvent == MOUSE_MOVE) {
			this.x += this.kineticMoveX * timeDelta / this.zoom;
			this.y += this.kineticMoveY * timeDelta / this.zoom;

		} else if(this.mouseEvent == MOUSE_ZOOM) {
			this.zoom *= (float)Math.pow(2.0,
				this.kineticMoveY * timeDelta * .01);
		}

		this.kineticMoveX *= 1 - 0.001f * timeDelta;
		this.kineticMoveY *= 1 - 0.001f * timeDelta;
	}

	/**
	 * Mouse dragging event handler.
	 */
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
		mouseInputUpdate(e);
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

	/*
	 * Unneeded event handlers.
	 */

	/** Unused event handler.
	 * @param e The mouse event.
	 */
	public void mouseMoved(MouseEvent e) {
	}
	/** Unused event handler.
	 * @param e The mouse event.
	 */
	public void mouseClicked(MouseEvent e) {
	}
	/** Unused event handler.
	 * @param e The mouse event.
	 */
	public void mouseEntered(MouseEvent e) {
	}
	/** Unused event handler.
	 * @param e The mouse event.
	 */
	public void mouseExited(MouseEvent e) {
	}
	/** Unused event handler.
	 * @param e The mouse event.
	 */
	public void keyTyped(KeyEvent e) {
	}
	/** Unused event handler.
	 * @param e The key event.
	 */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * Event handler for key released.
	 * @param e The key event.
	 */
	public void keyReleased(KeyEvent e) {
		listener.keyPress(KeyEvent.getKeyText(e.getKeyCode()));
	}

	/**
	 * Mouse pressed event handler.
	 * @param e The mouse event.
	 */
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

	/**
	 * Mouse released event handler.
	 * @param e The mouse event.
	 */
	public void mouseReleased(MouseEvent e) {
		if(this.mouseEvent == MOUSE_ADD) {
			System.out.println("add particles");
		}
		/* Mouse event is left as is so that the kinetic move
		 * will affect correct variables. */
		mouseInputUpdate(e);

		this.pointerDrag = false;
	}

	/**
	 * Resize event handler.
	 */
	private void resized() {
		this.offscreenBuffer = createImage(getWidth(), getHeight());
		this.offscreenGraphics = offscreenBuffer.getGraphics();

		this.oldWidth = getWidth();
		this.oldHeight = getHeight();
	}

	/**
	 * Handles the screen update.
	 */
	public void update(Graphics g){
		paint(g);
	}

	/**
	 * Renders the content of the window.
	 */
	public void paint(Graphics graphics) {
		Graphics g;
		Particle[] particles;
		int contentWidth, contentHeight, i;
		int timeDelta;
		float offsetX, offsetY;
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

		timeDelta = computeTimeDelta();
		applyKineticMotion(timeDelta);

		this.fps = (1000.0f / timeDelta) * .01f + this.fps * .99f;

		/*TODO: use floats instead of ints*/
		offsetX = this.x - contentWidth / (2 * this.zoom);
		offsetY = this.y - contentHeight / (2 * this.zoom);
		particles = listener.fetchParticles(
			(int)offsetX, (int)offsetY,
			(int)(contentWidth / this.zoom),
			(int)(contentHeight / this.zoom));

		g = this.offscreenGraphics;
		offsetX = -this.x * this.zoom + windowFrame.left +
		           contentWidth / 2;
		offsetY = -this.y * this.zoom + windowFrame.top +
		           contentHeight / 2;

		/* Draw the whole screen with semi-transparent white.
		 * This will create motion blur effect.
		 */
		g.setColor(new Color(1f, 1f, 1f, 0.3f));
		g.fillRect(0, 0, getWidth(), getHeight());
		/* restore the color of the paint */
		g.setColor(new Color(0f, 0f, 0f, 1f));

		for(i = 0; i < particles.length; i++) {
			Particle p = particles[i];
			g.fillOval((int)(offsetX + p.getX() * this.zoom - 1),
			           (int)(offsetY + p.getY() * this.zoom - 1),
			           2, 2);
		}

		FontMetrics metrics = g.getFontMetrics();

		text = listener.getHeaderText();
		g.drawString(text,
		             windowFrame.left + contentWidth - metrics.stringWidth(text) - 8,
		             windowFrame.top + metrics.getHeight());

		graphics.drawImage(this.offscreenBuffer, 0, 0, this);

		if(!this.paused) {
			this.repaint();
		}
	}

	/**
	 * Gets the x coordinate of the view.
	 *
	 * @return Gives the x coordinate of the view.
	 */
	public float getScrollX() {
		return this.x;
	}

	/**
	 * Gets the y coordinate of the view.
	 *
	 * @return Gives the y coordinate of the view.
	 */
	public float getScrollY() {
		return this.y;
	}

	/**
	 * Gets the fps.
	 */
	public float getFPS() {
		return this.fps;
	}
}
