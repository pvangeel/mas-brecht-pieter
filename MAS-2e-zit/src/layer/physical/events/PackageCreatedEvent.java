package layer.physical.events;

import layer.physical.entities.PDPPackage;
import framework.events.Event;
import framework.layer.physical.position.ContinuousPosition;

public class PackageCreatedEvent extends Event {

	private PDPPackage pdpPackage;
	private ContinuousPosition position;

	public PackageCreatedEvent(PDPPackage p, ContinuousPosition position) {
		this.pdpPackage = p;
		this.position = position;
	}
	
	public PDPPackage getPackage(){
		return pdpPackage;
	}

	public ContinuousPosition getPosition() {
		return position;
	}
}
