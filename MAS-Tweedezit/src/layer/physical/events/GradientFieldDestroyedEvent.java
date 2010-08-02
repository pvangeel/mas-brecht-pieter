package layer.physical.events;

import framework.events.Event;

public class GradientFieldDestroyedEvent extends Event {
	private int internalId;

	public GradientFieldDestroyedEvent(int internalId) {
		this.internalId = internalId;
	}
	
	public int getPackageId(){
		return internalId;
	}	
}
