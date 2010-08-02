package framework.layer;

/**
 * A component that can process ticks sent to it
 * 
 */
public interface TickListener {

	/**
	 * process an incoming tick
	 * 
	 * @param timePassed	The time that has passed since the last tick was sent.
	 * 						If this is the first time the method gets called, 
	 * 						the value should be 0.
	 */
	public void processTick(long timePassed);

}
