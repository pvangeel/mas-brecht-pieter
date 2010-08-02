package configuration;

import framework.core.VirtualClock;
import framework.initialization.TimePattern;

public class DelayedTimePattern extends TimePattern {
	private boolean started = false;
	private long milisecondsToDelay;

	public DelayedTimePattern(long miliseconds) {
		if(miliseconds <= 0) 
			throw new IllegalArgumentException("Delayed time should be greater then 0 and not null!");
		this.milisecondsToDelay = miliseconds;
	}
	
	@Override
	public long getNextTime() {
		if (started) {
			throw new IllegalStateException();
		}
		started = true;
		return VirtualClock.currentTime() + milisecondsToDelay;
	}

	@Override
	public boolean hasNextTime() {
		return !started;
	}

}
