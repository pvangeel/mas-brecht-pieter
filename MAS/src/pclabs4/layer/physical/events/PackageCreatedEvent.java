package pclabs4.layer.physical.events;

import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class PackageCreatedEvent extends Event {

	private int internalId;
	private ContinuousPosition position;

	public PackageCreatedEvent(int internalId, ContinuousPosition position) {
		this.internalId = internalId;
		this.position = position;
	}
	
	public int getPackageId(){
		return internalId;
	}

	public ContinuousPosition getPosition() {
		return position;
	}
}
