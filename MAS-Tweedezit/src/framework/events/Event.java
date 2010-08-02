package framework.events;

import framework.core.VirtualClock;

/**
 * Abstract class for an event coming out of the system
 *
 */
public abstract class Event {

	private long time;

	public Event() {
		this.time = VirtualClock.getClock().getCurrentTime();
	}

	/**
	 * Return the time at which the Event was created
	 */
	public long getTime() {
		return time;
	}

}
