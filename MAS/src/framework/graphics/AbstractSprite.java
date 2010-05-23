package framework.graphics;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * A convenience class to represent an image object that can be drawn on the screen
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public abstract class AbstractSprite {

	private static final AlphaComposite defaultAlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);

	public abstract BufferedImage getImage(int frame);

	public int getWidth() {
		return getImage(0).getWidth();
	}

	public int getHeight() {
		return getImage(0).getHeight();
	}

	public void draw(Graphics g, int x, int y, int frame) {
		g.drawImage(getImage(frame), x, y, null);
	}

	public void draw(Graphics g, int x, int y, int frame, float transparancy) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparancy));
		g.drawImage(getImage(frame), x, y, null);
		g2d.setComposite(defaultAlphaComposite);
	}
}
