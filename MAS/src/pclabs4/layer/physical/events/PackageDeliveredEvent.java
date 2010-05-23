package pclabs4.layer.physical.events;

import framework.events.Event;

public class PackageDeliveredEvent extends Event {
	private int internalId;

	public PackageDeliveredEvent(int internalId) {
		this.internalId = internalId;
	}
	
	public int getPackageId(){
		return internalId;
	}
	
}
