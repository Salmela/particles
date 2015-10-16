package org.tiralab.particles;

import org.tiralab.particles.gui.Rectangle;
import org.junit.Assert;
import org.junit.Test;

public class RectangleTest {
	public RectangleTest() {
	}

	@Test
	public void getX() {
		Rectangle rect = new Rectangle(2, 0, 0, 0);
		Assert.assertEquals(2.0, rect.getX(), 0.01);
	}

	@Test
	public void getY() {
		Rectangle rect = new Rectangle(0, 8, 0, 0);
		Assert.assertEquals(8.0, rect.getY(), 0.01);
	}

	@Test
	public void getWidth() {
		Rectangle rect = new Rectangle(7, 0, 3, 0);
		Assert.assertEquals(3.0, rect.getWidth(), 0.01);
	}

	@Test
	public void getHeight() {
		Rectangle rect = new Rectangle(7, 0, 3, 2);
		Assert.assertEquals(2.0, rect.getHeight(), 0.01);
	}
}
