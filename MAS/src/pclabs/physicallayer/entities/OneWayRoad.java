package pclabs.physicallayer.entities;

import pclabs.physicallayer.events.OneWayRoadCreatedEvent;
import framework.events.EventBroker;
import framework.layer.physical.position.Direction;

public class OneWayRoad extends Road {

	public OneWayRoad() {
		EventBroker.getEventBroker().notifyAll(new OneWayRoadCreatedEvent(this));
	}

	@Override
	public boolean canTravel(Direction direction) {
		return direction == Direction.TO_CONNECTOR_2;
	}

	@Override
	public Road createNewPiece() {
		return new OneWayRoad();
	}

}
