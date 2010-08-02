package framework.graphics;

import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

/**
 * A resource manager for sprites in the game. Its often quite important how and
 * where you get your game resources from. In most cases it makes sense to have
 * a central resource loader that goes away, gets your resources and caches them
 * for future use.
 * <p>
 * [singleton]
 * <p>
 * 
 * @author Kevin Glass
 */
public class SpriteStore {
	/** The single instance of this class */
	private static SpriteStore single = new SpriteStore();

	/**
	 * Get the single instance of this class
	 * 
	 * @return The single instance of this class
	 */
	public static SpriteStore get() {
		return single;
	}

	/** The cached sprite map, from reference to sprite instance */
	private final HashMap<String, AbstractSprite> abstractSprites = new HashMap<String, AbstractSprite>();
	
	private final HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();

	/**
	 * Retrieve a sprite from the store
	 * 
	 * @param ref
	 *            The reference to the image to use for the sprite
	 * @return A sprite instance containing an accelerate image of the request
	 *         reference
	 */
	public AbstractSprite getSprite(String ref) {
		// if we've already got the sprite in the cache
		// then just return the existing version
		if (abstractSprites.get(ref) != null) {
			return abstractSprites.get(ref);
		}

		AbstractSprite abstractSprite;
		if (isAnimation(ref)) {
			abstractSprite = loadAnimatedSprite(ref);
		} else {
			abstractSprite = loadSprite(ref);
		}

		abstractSprites.put(ref, abstractSprite);
		return abstractSprite;
	}

	private AbstractSprite loadSprite(String ref) {
		BufferedImage image = loadImage(ref);
		if (image == null) {
			fail("Can't find sprite: " + ref);
		}
		return new Sprite(image);
	}

	private AnimatedSprite loadAnimatedSprite(String ref) {
		ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
		while (true) {
			String url = ref + images.size() + ".gif";
			BufferedImage image = loadImage(url);
			if (image == null && images.isEmpty()) {
				fail("Can't find animation: " + url);
			} else if (image == null && !images.isEmpty()) {
				break;
			} else {
				images.add(image);
			}
		}
		BufferedImage[] imageArray = new BufferedImage[images.size()];
		images.toArray(imageArray);
		return new AnimatedSprite(imageArray);
	}
	
	public BufferedImage getImage(String ref){
		if (images.get(ref) != null) {
			return images.get(ref);
		}
		
		BufferedImage image = loadImage(ref);
		if(image == null){
			fail("Can't find image: " + ref);
		}
		images.put(ref, image);
		return image;
	}

	private BufferedImage loadImage(String ref) {
		// otherwise, go away and grab the sprite from the resource
		// loader
		BufferedImage sourceImage = null;

		try {
			// The ClassLoader.getResource() ensures we get the sprite
			// from the appropriate place, this helps with deploying the game
			// with things like webstart. You could equally do a file look
			// up here.
			URL url = this.getClass().getClassLoader().getResource(ref);

			if (url == null) {
				return null;
			}

			// use ImageIO to read the image in
			sourceImage = ImageIO.read(url);
		} catch (IOException e) {
			return null;
		}

		// create an accelerated image of the right size to store our sprite in
		GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getDefaultConfiguration();
		BufferedImage image = gc.createCompatibleImage(sourceImage.getWidth(), sourceImage.getHeight(),
				Transparency.TRANSLUCENT);

		// draw our source image into the accelerated image
		image.getGraphics().drawImage(sourceImage, 0, 0, null);
		return image;
	}

	private boolean isAnimation(String ref) {
		Pattern pattern = Pattern.compile("_ani_");
		Matcher matcher = pattern.matcher(ref);
		return matcher.find();
	}

	/**
	 * Utility method to handle resource loading failure
	 * 
	 * @param message
	 *            The message to display on failure
	 */
	private void fail(String message) {
		// we're pretty dramatic here, if a resource isn't available
		// we dump the message and exit the game
		System.out.println("ERROR:" + message);
		//System.exit(0);
	}
}