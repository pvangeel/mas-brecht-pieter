package framework.graphics;

import java.net.URL;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class IconStore {
	private static IconStore single = new IconStore();

	public static IconStore getInstance() {
		return single;
	}

	private HashMap<String, ImageIcon> icons = new HashMap<String, ImageIcon>();

	public ImageIcon getImageIcon(String ref) {
		// if we've already got the sprite in the cache
		// then just return the existing version
		if (icons.containsKey(ref)) {
			return icons.get(ref);
		}
		// otherwise, go away and grab the sprite from the resource
		// loader
		ImageIcon icon = null;
		// The ClassLoader.getResource() ensures we get the sprite
		// from the appropriate place, this helps with deploying the game
		// with things like webstart. You could equally do a file look
		// up here.
		URL url = this.getClass().getClassLoader().getResource(ref);
		if (url == null) {
			fail("Can't find ref: " + ref);
		}
		icon = new ImageIcon(url);
		icons.put(ref, icon);
		return icon;
	}

	private void fail(String message) {
		// we're pretty dramatic here, if a resource isn't available
		// we dump the message and exit the game
		System.err.println(message);
		System.exit(0);
	}
}
