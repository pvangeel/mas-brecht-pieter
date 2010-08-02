package framework.layer.physical.position;

import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;

/**
 * A position on a connection, consisting of a Connection, a travel direction
 * and a distance to the connector from which the entity entered the connection
 * (this is of course dependent on travel direction)
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class ConnectionPosition<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionElementPosition<E, Ctr, Cnn> {

	private long distance;
	private Direction direction;

	public ConnectionPosition(Connection<E, Ctr, Cnn> connection, long distance, Direction direction) {
		super(connection);
		if (direction.equals(null)) {
			throw new IllegalArgumentException();
		}
		this.direction = direction;
		setDistance(distance);
	}

	@SuppressWarnings("unchecked")
	public Cnn getConnection() {
		return (Cnn) getConnectionElement();
	}

	public Direction getDirection() {
		return direction;
	}

	public long getDistance() {
		return distance;
	}

	public void setDistance(long distance) {
		if (distance < 0 || distance > getConnection().getLength()) {
			throw new IllegalArgumentException();
		}
		this.distance = distance;
		setXY();
	}

	private void setXY() {
		Position startPosition = getStartConnector().getPosition();
		Position endPosition = getEndConnector().getPosition();
		long[] proj = startPosition.getProjectedXY(endPosition, distance);
		setX(proj[0]);
		setY(proj[1]);
	}

	private Ctr getStartConnector() {
		if (direction == Direction.TO_CONNECTOR_2) {
			return getConnection().getConnector1();
		} else {
			return getConnection().getConnector2();
		}
	}

	private Ctr getEndConnector() {
		if (direction == Direction.TO_CONNECTOR_1) {
			return getConnection().getConnector1();
		} else {
			return getConnection().getConnector2();
		}
	}

	@Override
	public boolean isOnConnection() {
		return true;
	}

	@Override
	public boolean isOnConnector() {
		return false;
	}

	public long getDistanceTillEnd() {
		return getConnection().getLength() - distance;
	}

	public Ctr getUpcomingConnector() {
		return getConnection().getConnectorAtEnd(getDirection());
	}
}
