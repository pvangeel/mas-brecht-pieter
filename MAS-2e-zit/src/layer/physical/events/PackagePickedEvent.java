package layer.physical.events;

import layer.physical.entities.Truck;
import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class PackagePickedEvent extends Event {
	private int internalId;
	private ContinuousPosition deliveryPosition;
	private final Truck truck;

	public PackagePickedEvent(int internalId, ContinuousPosition deliveryPosition, Truck truck) {
		this.internalId = internalId;
		this.deliveryPosition = deliveryPosition;
		this.truck = truck;
	}
	
	public int getPackageId(){
		return internalId;
	}
	
	public ContinuousPosition getDeliveryPosition() {
		return deliveryPosition;
	}

	public Truck getTruck() {
		return truck;
	}

}
