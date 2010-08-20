package framework.layer.physical.connections;

import java.util.HashSet;
import java.util.Set;

import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.ConnectorPosition;
import framework.layer.physical.position.Direction;
import framework.utils.IdGenerator;

/**
 * A connection is a straight piece of 'road' between two connectors with a
 * certain length. It can have one or two travel directions.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Connection<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionElement<E, Ctr, Cnn, PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private final int id = IdGenerator.getIdGenerator().getNextId(Connection.class);

	private Ctr connector1;
	private Ctr connector2;

	private final Set<E> entitiesTo1 = new HashSet<E>();
	private final Set<E> entitiesTo2 = new HashSet<E>();

	public int getId() {
		return id;
	}

	public long getLength() {
		return connector1.getPosition().getDistanceTo(connector2.getPosition());
	}

	public Ctr getConnector1() {
		if (connector1 == null) {
			throw new IllegalStateException();
		}
		return connector1;
	}

	public Ctr getConnector2() {
		if (connector2 == null) {
			throw new IllegalStateException();
		}
		return connector2;
	}

	public void connect(Ctr connector1, Ctr connector2) {
		if (this.connector1 != null || this.connector2 != null) {
			throw new IllegalStateException("connectors of a connection can only be set once");
		}
		this.connector1 = connector1;
		this.connector2 = connector2;
	}

	public boolean canTravelTo(Ctr connector) {
		return (canTravel(Direction.TO_CONNECTOR_1) && getConnector1().equals(connector))
				|| (canTravel(Direction.TO_CONNECTOR_2) && getConnector2().equals(connector));
	}

	public boolean canTravelFrom(Ctr connector) {
		return (canTravel(Direction.TO_CONNECTOR_2) && getConnector1().equals(connector))
				|| (canTravel(Direction.TO_CONNECTOR_1) && getConnector2().equals(connector));
	}

	public abstract boolean canTravel(Direction direction);

	public boolean hasConnector(Connector<E, Ctr, Cnn> connector) {
		return getConnector1().equals(connector) || getConnector2().equals(connector);
	}

	public void destroy() {
		getConnector1().removeConnection(this);
		getConnector2().removeConnection(this);
	}

	void enterConnection(E connectionEntity, Ctr connector) {
		Direction directionFromConnector = getDirectionFromConnector(connector);
		connectionEntity.setConnectionElementPosition(new ConnectionPosition<E, Ctr, Cnn>(this, 0, directionFromConnector));
		if (directionFromConnector == Direction.TO_CONNECTOR_1) {
			entitiesTo1.add(connectionEntity);
		} else {
			entitiesTo2.add(connectionEntity);
		}
	}

	void leaveConnection(E connectionEntity, Ctr connector, boolean onroad) {
		Direction directionFromConnector = getDirectionToConnector(connector);
		connectionEntity.setConnectionElementPosition(new ConnectorPosition<E, Ctr, Cnn>(connector, onroad));
		if (directionFromConnector == Direction.TO_CONNECTOR_1) {
			entitiesTo1.remove(connectionEntity);
		} else {
			entitiesTo2.remove(connectionEntity);
		}
	}

	private Direction getDirectionFromConnector(Ctr connector) {
		if (getConnector1().equals(connector)) {
			return Direction.TO_CONNECTOR_2;
		} else if (getConnector2().equals(connector)) {
			return Direction.TO_CONNECTOR_1;
		} else {
			throw new IllegalArgumentException();
		}
	}

	private Direction getDirectionToConnector(Ctr connector) {
		if (getConnector1().equals(connector)) {
			return Direction.TO_CONNECTOR_1;
		} else if (getConnector2().equals(connector)) {
			return Direction.TO_CONNECTOR_2;
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * return the distance from the given connector till the first entity on
	 * this connection. If there is an entity at distance 0 on the connection,
	 * then the result of this method is 0.
	 */
	public long getDistanceTillFirstEntityFromConnector(Ctr connector) {
		Direction direction = getDirectionFromConnector(connector);
		E entityUpAhead = getEntityUpAhead(0, direction);
		if (entityUpAhead == null) {
			return getLength();
		} else {
			return entityUpAhead.getConnectionPosition().getDistance();
		}
	}

	/**
	 * return the distance until the first entity up ahead. entities at the
	 * same position as the given entity are not considered entities 'up ahead'.
	 */
	public long getDistanceTillFirstEntityFromEntity(E entity) {
		Direction direction = entity.getConnectionPosition().getDirection();
		E entityUpAhead = getEntityUpAhead(entity.getConnectionPosition().getDistance() + 1, direction);
		if (entityUpAhead == null || entityUpAhead.equals(entity)) {
			return Long.MAX_VALUE;
		} else {
			return entityUpAhead.getConnectionPosition().getDistance() - entity.getConnectionPosition().getDistance();
		}
	}

	private E getEntityUpAhead(long distance, Direction direction) {
		Set<E> entities = (direction == Direction.TO_CONNECTOR_1) ? entitiesTo1 : entitiesTo2;
		E first = null;
		for (E e : entities) {
			if (first == null || first.getConnectionPosition().getDistance() > e.getConnectionPosition().getDistance()) {
				if (e.getConnectionPosition().getDistance() >= distance) {
					first = e;
				}
			}
		}
		return first;
	}

	public boolean isAtEnd(E connectionEntity) {
		if (connectionEntity == null) {
			throw new IllegalArgumentException();
		}
		return connectionEntity.getConnectionPosition().getDistance() == getLength();
	}

	public Ctr getConnectorAtEnd(Direction direction) {
		if (direction == Direction.TO_CONNECTOR_1) {
			return getConnector1();
		} else if (direction == Direction.TO_CONNECTOR_2) {
			return getConnector2();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Ctr getOtherConnector(Ctr connector) {
		if (getConnector1().equals(connector)) {
			return getConnector2();
		} else if (getConnector2().equals(connector)) {
			return getConnector1();
		} else {
			throw new IllegalArgumentException();
		}
	}

	public Set<E> getEntitiesTo1() {
		return entitiesTo1;
	}

	public Set<E> getEntitiesTo2() {
		return entitiesTo2;
	}

	@Override
	public void deploy(E connectionEntity, ConnectionElementPosition<E, Ctr, Cnn> position) throws IllegalDeploymentException {
		if (position == null || !position.isOnConnection()) {
			throw new IllegalArgumentException();
		}
		ConnectionPosition<E, Ctr, Cnn> pos = (ConnectionPosition<E, Ctr, Cnn>) position;
		if (!pos.getConnection().equals(this)) {
			throw new IllegalArgumentException();
		}

		if (pos.getDirection() == Direction.TO_CONNECTOR_1) {
			entitiesTo1.add(connectionEntity);
		} else {
			entitiesTo2.add(connectionEntity);
		}
		executeSpecificDeploymentOptions(connectionEntity, pos);
		connectionEntity.setConnectionElementPosition(pos);
	}

	protected abstract void executeSpecificDeploymentOptions(E connectionEntity, ConnectionPosition<E, Ctr, Cnn> pos);

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Connection) {
			Connection c = (Connection) obj;
			return c.getId() == getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ("Connection" + getId()).hashCode();
	}

	public abstract Cnn createNewPiece();

}