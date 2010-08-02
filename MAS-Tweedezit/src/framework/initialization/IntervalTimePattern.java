package framework.initialization;

/**
 * 
 * A infinite sequence of times with a fixed interval between every next time
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class IntervalTimePattern extends TimePattern {

	private final long interval;
	private long lastSpawn = 0;
	
	public IntervalTimePattern(long interval) {
		if (interval < 0) {
			throw new IllegalArgumentException();
		}
		this.interval = interval;
	}
	
	@Override
	public long getNextTime() {
		lastSpawn += interval;
		return lastSpawn;
	}

	@Override
	public boolean hasNextTime() {
		return true;
	}

}
