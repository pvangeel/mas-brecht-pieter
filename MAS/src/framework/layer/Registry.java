package framework.layer;

import java.util.Collection;
import java.util.HashSet;

/**
 * A simple registry where listeners can register/unregister and from which the
 * list of listeners can be requested.
 * 
 */
public class Registry {

	private final HashSet<TickListener> listeners = new HashSet<TickListener>();
	private final HashSet<TickListener> toBeAdded = new HashSet<TickListener>();
	private final HashSet<TickListener> toBeRemoved = new HashSet<TickListener>();

	public void register(TickListener tickListener) {
		if (tickListener == null) {
			throw new IllegalArgumentException();
		}
		if (containsListener(tickListener)) {
			throw new IllegalArgumentException(tickListener.toString());
		}
		toBeAdded.add(tickListener);
	}

	public Collection<TickListener> getListeners() {
		return listeners;
	}

	public void unregister(TickListener tickListener) {
		if (tickListener == null) {
			throw new IllegalArgumentException();
		}
		if (!containsListener(tickListener)) {
			throw new IllegalArgumentException();
		}
		toBeRemoved.add(tickListener);
	}

	private boolean containsListener(TickListener listener) {
		return (listeners.contains(listener) || toBeAdded.contains(listener)) && !toBeRemoved.contains(listener);
	}

	public void copyNewListeners() {
		listeners.addAll(toBeAdded);
		toBeAdded.clear();
		listeners.removeAll(toBeRemoved);
		toBeRemoved.clear();
	}

}
