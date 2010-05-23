package pclabs.physicallayer.events;

import pclabs.physicallayer.entities.Crossroads;
import framework.events.Event;

public class CrossRoadsCreatedEvent extends Event {

	private int crossroadsId;

	public CrossRoadsCreatedEvent(Crossroads crossroads) {
		this.crossroadsId = crossroads.getId();
	}
	
	protected CrossRoadsCreatedEvent() {
	}

	public int getCrossroadsId() {
		return crossroadsId;
	}
	
	@Override
	public String toString() {
		return "Crossroads " + crossroadsId + " was created";
	}
	
}