package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.Direction;

public class PositionOnConnectionUpdatedEvent extends Event {

	private int entityId;
	private int connectionId;
	private long newDistance;
	private Direction newDirection;

	protected PositionOnConnectionUpdatedEvent() {
	}

	public PositionOnConnectionUpdatedEvent(ConnectionEntity<?,?,?> entity) {
		if (!entity.getPosition().isOnConnection()) {
			throw new IllegalStateException();
		}
		this.entityId = entity.getId();
		this.connectionId = entity.getConnectionPosition().getConnection().getId();
		this.newDistance = entity.getConnectionPosition().getDistance();
		this.newDirection = entity.getConnectionPosition().getDirection();
	}

	public int getEntityId() {
		return entityId;
	}

	public int getConnectionId() {
		return connectionId;
	}

	public long getNewDistance() {
		return newDistance;
	}

	public Direction getNewDirection() {
		return newDirection;
	}
	
	@Override
	public String toString() {
		return "Entity " + entityId + " is now travelling on connection " + connectionId + " in direction " + newDirection + " at distance " + newDistance;
	}

}
