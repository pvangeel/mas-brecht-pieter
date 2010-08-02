package framework.graphics;

import java.awt.image.BufferedImage;


public class AnimatedSprite extends AbstractSprite {
	private final BufferedImage[] images;
	private final int randomStartFrame;

	public AnimatedSprite(BufferedImage[] images) {
		if (images == null || images.length == 0) {
			throw new IllegalArgumentException();
		}
		this.images = images;
		this.randomStartFrame = 0;
	}

	@Override
	public BufferedImage getImage(int frame) {
		int correctFrame = (frame + randomStartFrame)% images.length;
		return images[correctFrame];
	}

}
