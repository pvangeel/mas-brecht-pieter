package framework.initialization.startup;

import framework.initialization.TimePattern;

/**
 * 
 * A time pattern used for startup initializers. It consists of just one time, being the starttime of the clock.
 *
 */

public class StartupTimePattern extends TimePattern {

	private boolean started = false;

	@Override
	public long getNextTime() {
		if (started) {
			throw new IllegalStateException();
		}
		started = true;
		return 0;
	}

	@Override
	public boolean hasNextTime() {
		return !started;
	}

}
