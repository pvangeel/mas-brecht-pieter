package framework.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

import framework.events.Event;
/**
 * A dispatcher used by the gui framework to dispatch events to the interested listeners
 * 
 * @author Jelle Van Gompel & Bart Tuts
 */
public class GUIEventDispatcher {

	private final HashMap<Class<? extends Event>, Collection<GUIDispatcherListener>> listeners = new HashMap<Class<? extends Event>, Collection<GUIDispatcherListener>>();

	/**
	 * Registers the given listener to listen to the given event
	 * @param c
	 * 		Class of the event to listen to
	 * @param l
	 * 		The listener
	 */
	public void register(Class<? extends Event> c, GUIDispatcherListener l) {
		if (c == null || l == null) {
			throw new IllegalArgumentException();
		}
		Collection<GUIDispatcherListener> objects;
		if (listeners.containsKey(c)) {
			objects = listeners.get(c);
		} else {
			objects = new HashSet<GUIDispatcherListener>();
		}
		if (objects.contains(l)) {
			throw new IllegalStateException("WARNING: double registration of listener " + l + " for class " + c);
		}
		objects.add(l);
		listeners.put(c, objects);
	}

	/**
	 * Dispatches an event to all interested listeners
	 * @param event
	 * 		The event to be dispatched
	 */
	public void dispacthEvent(Event event) {
		if (event == null) {
			throw new IllegalArgumentException();
		}
		for (GUIDispatcherListener l : new HashSet<GUIDispatcherListener>(getListeners(event))) {
			l.handleEvent(event);
		}
	}

	/**
	 * @param event
	 * @return
	 * 		All listeners for a given event
	 */
	private Collection<GUIDispatcherListener> getListeners(Event event) {
		if (listeners.containsKey(event.getClass())) {
			return listeners.get(event.getClass());
		}
		return new HashSet<GUIDispatcherListener>();
	}

	/**
	 * Indicates the given GUIObject should stop listen to its events
	 * @param guiObject
	 * 		the gui object
	 */
	public void removeFromListeners(GUIObject guiObject) {
		for (Collection<GUIDispatcherListener> ls : listeners.values()) {
			if (ls.contains(guiObject)) {
				ls.remove(guiObject);
			}
		}
	}

}
