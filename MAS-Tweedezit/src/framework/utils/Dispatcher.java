package framework.utils;

import framework.core.VirtualClock;

/**
 * 
 * Dispatcher for ticks
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class Dispatcher {

	private final int turnoverCount;
	private int ticksLeft = 0;
	private long lastTime = 0;

	public Dispatcher(int turnoverCount) {
		if (turnoverCount < 1) {
			throw new IllegalArgumentException();
		}
		this.turnoverCount = turnoverCount;
	}

	public void notifyTickUpdate() {
		if (ticksLeft == 0) {
			processTick();
			ticksLeft = turnoverCount;
			lastTime = VirtualClock.getClock().getCurrentTime();
		}
		ticksLeft--;
	}
	
	protected abstract void processTick();

	protected long getTimeSinceLastTick() {
		return VirtualClock.getClock().getCurrentTime() - lastTime;
	}

}
