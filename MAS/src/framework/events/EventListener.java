package framework.events;

/**
 * An simple event listener interface
 * 
 */
public interface EventListener {
	
	public void handleEvent(Event event);
	
	public void terminate();
	
}