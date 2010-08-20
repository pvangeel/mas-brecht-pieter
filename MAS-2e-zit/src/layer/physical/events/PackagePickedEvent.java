package layer.physical.events;

import layer.physical.entities.PDPPackage;
import layer.physical.entities.Truck;
import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class PackagePickedEvent extends Event {
	private int internalId;
	private ContinuousPosition deliveryPosition;
	private final Truck truck;
	private final PDPPackage pdpPackage;

	public PackagePickedEvent(PDPPackage ret, ContinuousPosition deliveryPosition, Truck truck) {
		this.pdpPackage = ret;
		this.internalId = ret.getId();
		this.deliveryPosition = deliveryPosition;
		this.truck = truck;
	}
	
	public PDPPackage getPackage(){
		return pdpPackage;
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
