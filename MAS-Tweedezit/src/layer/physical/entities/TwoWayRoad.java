package layer.physical.entities;

import layer.physical.events.TwoWayRoadCreatedEvent;
import framework.events.EventBroker;
import framework.layer.physical.position.Direction;

public class TwoWayRoad extends Road {

	public TwoWayRoad() {
		EventBroker.getEventBroker().notifyAll(new TwoWayRoadCreatedEvent(this));
	}

	@Override
	public boolean canTravel(Direction direction) {
		return true;
	}

	@Override
	public Road createNewPiece() {
		return new TwoWayRoad();
	}

}
