package framework.gui;

import java.awt.Graphics;

/**
 * A plugin for the gui framework
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public abstract class GUIPlugin {

	private GUI gui;

	/**
	 * Used for creating the bidirectional relation between gui and plugin
	 * @param gui
	 * 		the gui framework
	 */
	void setGui(GUI gui) {
		if (this.gui != null) {
			throw new IllegalStateException();
		}
		this.gui = gui;
		addedToGui();
	}
	
	/**
	 * Called when this plugin is actually added to the gui framework
	 */
	protected abstract void addedToGui();

	/**
	 * @return
	 * 		The gui framework this plugin is attached to
	 */
	protected GUI getGui() {
		if (gui == null) {
			throw new IllegalStateException();
		}
		return gui;
	}

	/**
	 * Called when the gui framework is redrawn so the plugin can redraw itself
	 * @param g
	 * 		Graphics object of the screen
	 */
	public abstract void executeDrawOperations(Graphics g);

}
