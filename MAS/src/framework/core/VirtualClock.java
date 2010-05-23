package framework.core;

import framework.utils.TimeUtils.TimeObject;

/**
 * 
 * A virtual clock that is used to count / read / print virtual microseconds.
 * The clock is not linked to any real clock or the system clock. Time on the
 * virtual clock only increases when increaseTime(int) is called. The clock time
 * is initialized at 0 microseconds.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */
public class VirtualClock {

	private static VirtualClock clock;

	private long microseconds = 0;

	/**
	 * Retrieve the current virtual clock time
	 */
	public long getCurrentTime() {
		return microseconds;
	}

	/**
	 * Increase the time of the virtual clock with the given amount of
	 * microseconds
	 */
	public void increaseTime(int increase) {
		if (increase <= 0) {
			throw new IllegalArgumentException("increase must be strictly positive");
		}
		microseconds += increase;
	}

	/**
	 * Retrieve the singleton instance of the virtual clock
	 */
	public static VirtualClock getClock() {
		if (clock == null) {
			clock = new VirtualClock();
		}
		return clock;
	}
	
	/**
	 * Retrieves the current time from the virtual clock
	 */
	public static long currentTime() {
		return getClock().getCurrentTime();
	}

	/**
	 * Print the current virtual time on sysout.
	 */
	public void printTime() {
		System.out.println(getTimeObject());
	}

	/**
	 * Return a TimeObject of the current virtual clock time
	 */
	public TimeObject getTimeObject() {
		return new TimeObject(getCurrentTime());
	}

	public void reset() {
		microseconds = 0;
	}

}
