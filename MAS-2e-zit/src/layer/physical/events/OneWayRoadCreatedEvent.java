package layer.physical.events;

import layer.physical.entities.OneWayRoad;
import framework.events.Event;

public class OneWayRoadCreatedEvent extends Event {

	private int oneWayRoadId;

	public OneWayRoadCreatedEvent(OneWayRoad oneWayRoad) {
		this.oneWayRoadId = oneWayRoad.getId();
	}
	
	protected OneWayRoadCreatedEvent() {
	}

	public int getOneWayRoadId() {
		return oneWayRoadId;
	}
	
	@Override
	public String toString() {
		return "One Way Road " + oneWayRoadId + " was created.";
	}
}
