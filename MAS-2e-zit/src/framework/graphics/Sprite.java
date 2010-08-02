package framework.graphics;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sprite extends AbstractSprite {

	private BufferedImage image;

	public Sprite(BufferedImage image) {
		this.image = image;
	}

	public void drawTransformed(Graphics g, int x, int y, int frame) {
		g.drawImage(image, x, y, null);
	}

	@Override
	public BufferedImage getImage(int frame) {
		return image;
	}

}