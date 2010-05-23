package framework.layer.physical.position;

/**
 * A position for usage in a continous world, where there are no
 * connections/connectors, but simply a plane on which the entities drive
 * around.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class ContinuousPosition extends Position {

	private long x;

	private long y;

	public ContinuousPosition(long x, long y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public long getX() {
		return x;
	}

	@Override
	public long getY() {
		return y;
	}

	public void setX(long l) {
		this.x = l;
	}

	public void setY(long y) {
		this.y = y;
	}
}
