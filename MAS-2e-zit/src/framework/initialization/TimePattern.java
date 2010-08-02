package framework.initialization;

/**
 * 
 * A time pattern is a finite or infinite sequence of times, with every next time being equal or greater than the previous    
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */
public abstract class TimePattern {
	
	public abstract long getNextTime();

	public abstract boolean hasNextTime();

}
