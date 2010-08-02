package framework.events;

import java.util.HashSet;

/**
 * A standard EventBroker where event listeners can register to receive the
 * events dispatched by the EventBroker
 * 
 */
public class EventBroker {

	private final HashSet<EventListener> eventListeners = new HashSet<EventListener>();

	private static EventBroker eventBroker;

	/**
	 * Retrieve the singleton instance of the EventBroker
	 */
	public static EventBroker getEventBroker() {
		if (eventBroker == null) {
			eventBroker = new EventBroker();
		}
		return eventBroker;
	}

	public HashSet<EventListener> getEventListeners() {
		return eventListeners;
	}

	public void register(EventListener eventListener) {
		if (eventListener == null) {
			throw new IllegalArgumentException();
		}
		eventListeners.add(eventListener);
	}

	public void unregister(EventListener eventListener) {
		if (eventListener == null) {
			throw new IllegalArgumentException();
		}
		eventListeners.remove(eventListener);
	}

	public void notifyAll(Event event) {
		for (EventListener el : eventListeners) {
			el.handleEvent(event);
		}
	}

	/**
	 * calls the terminate method on all eventListeners and then clears the list
	 * of event listeners in the EventBroker
	 */
	public void terminate() {
		for (EventListener el : eventListeners) {
			el.terminate();
		}
		eventListeners.clear();
		eventBroker = null;
	}
}