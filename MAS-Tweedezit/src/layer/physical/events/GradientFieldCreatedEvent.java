package layer.physical.events;

import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class GradientFieldCreatedEvent extends Event {
	private int internalId;
	private ContinuousPosition position;
	private long radius;

	public GradientFieldCreatedEvent(int internalId, ContinuousPosition position, long radius) {
		this.internalId = internalId;
		this.position = position;
		this.radius = radius;
	}
	
	public int getPackageId(){
		return internalId;
	}

	public ContinuousPosition getPosition() {
		return position;
	}
	
	public long getRadius() {
		return radius;
	}
}
