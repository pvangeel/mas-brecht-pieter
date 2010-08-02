package framework.gui;

import framework.events.Event;
/**
 * Interface to listen to events dispatched by the gui framework
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public interface GUIDispatcherListener {

	/**
	 * The method called when an event should be handled
	 * @param event
	 * 		The veent to be handled
	 */
	public void handleEvent(Event event);
	
}
