package layer.physical.events;

import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class PackagePickedEvent extends Event {
	private int internalId;
	private ContinuousPosition deliveryPosition;

	public PackagePickedEvent(int internalId, ContinuousPosition deliveryPosition) {
		this.internalId = internalId;
		this.deliveryPosition = deliveryPosition;
	}
	
	public int getPackageId(){
		return internalId;
	}
	
	public ContinuousPosition getDeliveryPosition() {
		return deliveryPosition;
	}

}
