package framework.gui;

import java.awt.Graphics;

/**
 * An object that provides the graphical representation of a business object in the simulation framework
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public abstract class GUIObject implements GUIDispatcherListener {

	private final int id;

	private GUI gui;

	/**
	 * Creates a new GUIObect with the given id
	 * @param id
	 * 		Id of the object
	 */
	public GUIObject(int id) {
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	/**
	 * @return
	 * 		The id of the guiobject
	 */
	public int getId() {
		return id;
	}

	/**
	 * Used to create a bidrectional relation between a gui and a gui object
	 * @param gui
	 * 		the gui framework
	 */
	void setGui(GUI gui) {
		if (gui == null) {
			throw new IllegalArgumentException();
		}
		if (this.gui != null) {
			throw new IllegalStateException();
		}
		this.gui = gui;
	}

	/**
	 * @return
	 * 		The gui framework that manages this guiobject
	 */
	public GUI getGui() {
		return gui;
	}

	/**
	 * Should register the events this object is interested in
	 */
	public abstract void registerEvents();

	/**
	 * Convenience method to convert an x-coordinate to a position on the screen
	 */
	protected int convertX(long x) {
		return getGui().convertX(x);
	}

	/**
	 * Convenience method to convert an y-coordinate to a position on the screen
	 */
	protected int convertY(long y) {
		return getGui().convertY(y);
	}

	/**
	 * Indicates this object should draw itself on the given screen
	 * Draws only if the object is connected ok
	 * @param g
	 * 		Graphics object of the screen
	 */
	void draw(Graphics g) {
		if (isConnectedOk()) {
			drawProtected(g);
		}
	}

	/**
	 * Actual draw method, only called when the object is connected ok
	 * @param g
	 * 		Graphics object of the screen
	 */
	protected abstract void drawProtected(Graphics g);
	
	/**
	 * @return
	 * 		Returns whether this guiobject is initialized ok
	 */
	public abstract boolean isConnectedOk();
}
