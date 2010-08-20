package layer.physical.events;

import layer.physical.entities.Truck;
import framework.events.Event;

public class PackageDeliveredEvent extends Event {
	private int internalId;
	private final Truck truck;

	public PackageDeliveredEvent(int internalId, Truck truck) {
		this.internalId = internalId;
		this.truck = truck;
	}
	
	public int getPackageId(){
		return internalId;
	}

	public Truck getTruck() {
		return truck;
	}
	
}
