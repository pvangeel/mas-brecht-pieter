package pclabs.physicallayer.events;

import pclabs.physicallayer.entities.TwoWayRoad;
import framework.events.Event;

public class TwoWayRoadCreatedEvent extends Event {

	private int twoWayRoadId;

	public TwoWayRoadCreatedEvent(TwoWayRoad twoWayRoad) {
		this.twoWayRoadId = twoWayRoad.getId();
	}
	
	protected TwoWayRoadCreatedEvent() {
	}

	public int getTwoWayRoadId() {
		return twoWayRoadId;
	}
	
	@Override
	public String toString() {
		return "Two Way Road " + twoWayRoadId + " was created"; 
	}
	
}
