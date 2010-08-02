package framework.gui;

import framework.events.Event;

/**
 * Object that listens to creator and destructor event to manage the creation of specific gui objects
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public abstract class GUIObjectCreator implements GUIDispatcherListener {

	private final GUI gui;

	/**
	 * Initiates the creator for the given gui framework
	 * @param gui
	 * 		the gui framework
	 */
	public GUIObjectCreator(GUI gui) {
		if (gui == null) {
			throw new IllegalArgumentException();
		}
		this.gui = gui;
		gui.getDispatcher().register(getCreationEventClass(), this);
		if (getDestroyEventClass() != null) {
			gui.getDispatcher().register(getDestroyEventClass(), this);
		}
	}

	/**
	 * Creates the new guiobject if the event is the creator event
	 * Destroys the guiobject if the event is the destroy event
	 */
	@Override
	public void handleEvent(Event event) {
		if (event.getClass().equals(getCreationEventClass())) {
			GUIObject guiObject = createGUIObject(event);
			getGUI().addGUIObject(guiObject);
		} else if (event.getClass().equals(getDestroyEventClass())) {
			GUIObject guiObject = getDestroyGUIObject(event);
			getGUI().removeGUIObject(guiObject);
		} else {
			throw new IllegalStateException();
		}
	}

	/**
	 * @return
	 * 		The gui framework
	 */
	protected GUI getGUI() {
		return gui;
	}

	/**
	 * @return
	 * 		The class of the event on which a guiobject should be created
	 */
	protected abstract Class<? extends Event> getCreationEventClass();

	/**
	 * @return
	 * 		The class of the event on which a guiobject should be destroyed
	 */
	protected abstract Class<? extends Event> getDestroyEventClass();

	/**
	 * @param event
	 * @return
	 * 		The GUIObject created on the given event
	 */
	protected abstract GUIObject createGUIObject(Event event);

	/**
	 * @param event
	 * @return
	 * 		The GUIObject that should be destroyed for the given event
	 */
	protected abstract GUIObject getDestroyGUIObject(Event event);

}
