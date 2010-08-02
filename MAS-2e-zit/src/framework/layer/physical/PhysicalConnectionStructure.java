package framework.layer.physical;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import framework.events.EventBroker;
import framework.events.physical.ConnectionAddedEvent;
import framework.events.physical.ConnectionEntityDeployedEvent;
import framework.events.physical.ConnectionRemovedEvent;
import framework.events.physical.ConnectorAddedEvent;
import framework.events.physical.ConnectorRemovedEvent;
import framework.events.physical.PhysicalEntityDeployFailedEvent;
import framework.events.physical.ResourceDeployedEvent;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.connections.ConnectionElement.IllegalDeploymentException;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.entities.StaticResource;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.ConnectorPosition;
import framework.layer.physical.position.ContinuousPosition;
import framework.layer.physical.position.Direction;

/**
 * 
 * A structure containing all the existing entities in a physical connection layer
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class PhysicalConnectionStructure<E extends ConnectionEntity<E, Ctr, Cnn>, 
										Ctr extends Connector<E, Ctr, Cnn>, 
										Cnn extends Connection<E, Ctr, Cnn>>
		extends PhysicalStructure<E> {

	private final Collection<Ctr> connectors = new HashSet<Ctr>();
	private final Collection<Cnn> connections = new HashSet<Cnn>();

	/**
	 * Add the given Connector<E,Ctr,Cnn> to the physical Connection<E,
	 * Connector<E,Ctr,Cnn>, Cnn> Structure on the given position. If another
	 * connector or connection is already occupying the given position, the two
	 * are _not_ connected to one another. In this way, bridges, tunnels and
	 * other multi-level crossings can be constructed.
	 * 
	 */
	public void addConnector(Ctr connector, ContinuousPosition position) {
		if (connector == null || position == null) {
			throw new IllegalArgumentException();
		}
		connector.setPosition(position);
		connectors.add(connector);
		EventBroker.getEventBroker().notifyAll(new ConnectorAddedEvent(connector, position));
	}

	public void addConnection(Cnn connection, Ctr connector1, Ctr connector2) {
		if (connection == null || connector1 == null || connector2 == null) {
			throw new IllegalArgumentException();
		}
		if (!(connectors.contains(connector1) && connectors.contains(connector2))) {
			throw new IllegalStateException();
		}
		connection.connect(connector1, connector2);
		connector1.addConnection(connection);
		connector2.addConnection(connection);
		connections.add(connection);
		EventBroker.getEventBroker().notifyAll(new ConnectionAddedEvent(connection, connector1, connector2));
	}

	/**
	 * Splits a given connection at a given position into two new connections
	 * and a new connector, thereby moving all physical entities on the original
	 * connection to correct positions on the new connection elements.
	 * 
	 * connector1 -> originalconnection -> connector2 will be replaced with
	 * connector1 -> con1 -> newConnector -> con2 -> connector2 The arrows
	 * correspond with the direction TO_CONNECTOR2 connector1 ->
	 * originalconnection -> connector2 will be replaced with connector1 -> con1
	 * -> newConnector -> con2 -> connector2 The arrows correspond with the
	 * direction TO_CONNECTOR2
	 * 
	 * ATTENTION: because of rounding errors the combined lengths of the new
	 * connections may exceed the length of the original connection by 1 or 2 mm
	 * 
	 * Use this method with caution
	 */
	public void insertConnector(Ctr newConnector, ConnectionPosition<E, Ctr, Cnn> connectionPosition, Cnn newCon1, Cnn newCon2) {
		if (newConnector == null || connectionPosition == null || newCon1 == null || newCon2 == null) {
			throw new IllegalArgumentException();
		}

		Cnn originalConnection = connectionPosition.getConnection();

		// controle of de eigenschappen van de 2 nieuwe connections conform zijn
		// aan die van de oude originele connection
		checkNewConnectionsSplit(newCon1, newCon2, originalConnection);

		Ctr startConnector = originalConnection.getConnector1();
		Ctr endConnector = originalConnection.getConnector2();
		Set<E> entitiesTo1 = originalConnection.getEntitiesTo1();
		Set<E> entitiesTo2 = originalConnection.getEntitiesTo2();

		removeConnection(originalConnection);

		addConnector(newConnector, new ContinuousPosition(connectionPosition.getX(), connectionPosition.getY()));

		addConnection(newCon1, startConnector, newConnector);
		addConnection(newCon2, newConnector, endConnector);

		placeVehicles(entitiesTo2, Direction.TO_CONNECTOR_2, newCon1, newCon2);
		placeVehicles(entitiesTo1, Direction.TO_CONNECTOR_1, newCon2, newCon1);
	}

	private void checkNewConnectionsSplit(Connection<E, Ctr, Cnn> con1, Connection<E, Ctr, Cnn> con2,
			Connection<E, Ctr, Cnn> originalConnection) {

		// notice that we do not need to check the length of the roads since the
		// length of a road is determined by the two end connectors

		// must be able to travel over new connections in the same directions as
		// over the old because there may still be vehicles on it
		if (originalConnection.canTravel(Direction.TO_CONNECTOR_1)
				&& !(con1.canTravel(Direction.TO_CONNECTOR_1) && con2.canTravel(Direction.TO_CONNECTOR_1))) {
			throw new IllegalArgumentException();
		}
		if (originalConnection.canTravel(Direction.TO_CONNECTOR_2)
				&& !(con1.canTravel(Direction.TO_CONNECTOR_2) && con2.canTravel(Direction.TO_CONNECTOR_2))) {
			throw new IllegalArgumentException();
		}
		if (!originalConnection.canTravel(Direction.TO_CONNECTOR_1)
				&& (con1.canTravel(Direction.TO_CONNECTOR_1) || con2.canTravel(Direction.TO_CONNECTOR_1))) {
			throw new IllegalArgumentException();
		}
		if (!originalConnection.canTravel(Direction.TO_CONNECTOR_2)
				&& (con1.canTravel(Direction.TO_CONNECTOR_2) || con2.canTravel(Direction.TO_CONNECTOR_2))) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Extract a given Connector from the graph structure, replacing its
	 * connections with the given new connection, thereby moving all physical
	 * entities on the original connections and connector to correct positions
	 * on the replacement connection. physical entities that are on the
	 * connector when it is extracted are put on the new Connection in the
	 * direction of the given destination Connector. direction1connector is the
	 * connector which will be the connection's destination when travelling in
	 * direction 1.
	 * 
	 * ATTENTION:
	 * 
	 * - All vehicles on the connector are moved to the 0 position of the
	 * destinationConnection. This means vehicles may be on top of each other.
	 * Driving instructions should take this into account when using this method
	 * 
	 * - Because of rounding errors the new connection may be by 1 or 2 mm
	 * shorter than the sum of the lengths of the original connections. This may
	 * mean vehicles are placed closer together than their minimum distance
	 * allows and may even be placed on top of each other.
	 * 
	 * Use this method with caution
	 */
	public void extractConnector(Connector<E, Ctr, Cnn> centralConnector, Connection<E, Ctr, Cnn> newConnection,
			Connector<E, Ctr, Cnn> destinationConnector, Connector<E, Ctr, Cnn> direction1Connector) {
		throw new UnsupportedOperationException();
		// if (centralConnector == null || newConnection == null ||
		// destinationConnector == null
		// || (centralConnector.getConnections().size() != 2)) {
		// throw new IllegalArgumentException();
		// }
		// if (centralConnector.equals(direction1Connector) ||
		// centralConnector.equals(destinationConnector)) {
		// throw new IllegalArgumentException();
		// }
		// if (!centralConnector.isConnected(direction1Connector) ||
		// !centralConnector.isConnected(destinationConnector)) {
		// throw new IllegalArgumentException();
		// }
		//
		// Vector<Connection<E, Ctr, Cnn>> v = new Vector<Connection<E, Ctr,
		// Cnn>>(centralConnector.getConnections());
		//
		// Connection<E, Ctr, Cnn> old1 = v.get(0);
		// Connection<E, Ctr, Cnn> old2 = v.get(1);
		//
		// Connector<E, Ctr, Cnn> direction2Connector = null;
		//
		// if (old1.hasConnector(direction1Connector)) {
		// direction2Connector = old1.getOtherConnector(centralConnector);
		// } else {
		// direction2Connector = old2.getOtherConnector(centralConnector);
		// }

		// TODO: FROM HERE ON THE METHOD IS INCORRECT. MUST TAKE INTO ACCOUNT
		// THE FACT
		// THAT YOU DON'T KNOW WHAT THE DIRECTION 1 and 2 of the current
		// connections are. ie it could be (A -> centralC -> B) or (A <-
		// centralC -> B), etc

		// checkNewConnectionExtract(old1, old2, newConnection,
		// centralConnector);
		//
		// Set<ConnectionEntity> entitiesTo1 = old1.getEntitiesTo1();
		// entitiesTo1.addAll(old2.getEntitiesTo1());
		// Set<ConnectionEntity> entitiesTo2 = old1.getEntitiesTo2();
		// entitiesTo1.addAll(old2.getEntitiesTo2());
		//
		// removeConnection(old1);
		// removeConnection(old2);

		// addConnection
		//		
		// HIER VERDER SCHRIJVEN
		//		
		// //from split
		// Connector startConnector = originalConnection.getConnector1();
		// Connector endConnector = originalConnection.getConnector2();
		// removeConnection(originalConnection);
		//
		// addConnector(newConnector, new
		// ContinuousPosition(connectionPosition.getX(),
		// connectionPosition.getY()));
		//
		// addConnection(newCon1, startConnector, newConnector);
		// addConnection(newCon2, newConnector, endConnector);
		//
		// placeVehicles(entitiesTo2, Direction.TO_CONNECTOR_2, newCon1,
		// newCon2);
		// placeVehicles(entitiesTo1, Direction.TO_CONNECTOR_1, newCon2,
		// newCon1);
	}

	// private void checkNewConnectionExtract(Connection<E, Ctr, Cnn> old1,
	// Connection<E, Ctr, Cnn> old2,
	// Connection<E, Ctr, Cnn> newConnection, Connector<E, Ctr, Cnn> connector)
	// {
	//
	// // notice that we do not need to check the length of the roads since the
	// // length of a road is determined by the two end connectors
	//
	// // must be able to travel over new connection in the same directions as
	// // over the old ones because there may still be vehicles on it. notice
	// // that even if we can travel in direction1 on old1 and not in
	// // direction1 on old2, we still need to be able to travel in direction1
	// // over newConnection.
	// if ((old1.canTravel(Direction.TO_CONNECTOR_1) ||
	// old2.canTravel(Direction.TO_CONNECTOR_1))
	// && !newConnection.canTravel(Direction.TO_CONNECTOR_1)) {
	// throw new IllegalArgumentException();
	// }
	// if ((old1.canTravel(Direction.TO_CONNECTOR_2) ||
	// old2.canTravel(Direction.TO_CONNECTOR_2))
	// && !newConnection.canTravel(Direction.TO_CONNECTOR_2)) {
	// throw new IllegalArgumentException();
	// }
	// }

	public void removeConnection(Connection<E, Ctr, Cnn> connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		if (connection.getEntitiesTo1().size() != 0 || connection.getEntitiesTo2().size() != 0) {
			throw new IllegalStateException("can not remove a connection that still has entities on it");
		}
		connection.destroy();
		connections.remove(connection);
		EventBroker.getEventBroker().notifyAll(new ConnectionRemovedEvent(connection));
	}

	public void removeConnector(Connector<E, Ctr, Cnn> connector) {
		if (connector == null) {
			throw new IllegalArgumentException();
		}
		if (connector.getConnections().size() != 0) {
			throw new IllegalStateException("can not remove a connector that still has connections attached to it");
		}
		if (connector.getOnroadEntities().size() != 0 || connector.getOffroadEntities().size() != 0) {
			throw new IllegalStateException("can not remove a connector that still has entities on it");
		}
		connectors.remove(connector);
		EventBroker.getEventBroker().notifyAll(new ConnectorRemovedEvent(connector));
	}

	/**
	 * Place the given vehicles on the new connections
	 * 
	 * @param entitiesInThisDirection
	 *            entities travelling in the given direction
	 * @param direction
	 *            given travel direction
	 * @param firstConnectionPiece
	 *            first connection piece that vehicles travel over when
	 *            travelling in the given travel direction
	 * @param secondConnectionPiece
	 *            second connection piece that vehicles travel over when
	 *            travelling in the given travel direction
	 */
	private void placeVehicles(Set<E> entitiesInThisDirection, Direction direction, Connection<E, Ctr, Cnn> firstConnectionPiece,
			Connection<E, Ctr, Cnn> secondConnectionPiece) {
		long distanceTillNewConnector = firstConnectionPiece.getLength();

		for (E entity : entitiesInThisDirection) {
			long distanceOnOldConnection = entity.getConnectionPosition().getDistance();
			if (entity.getConnectionPosition().getDistance() <= distanceTillNewConnector) {
				entity.setConnectionElementPosition(new ConnectionPosition<E, Ctr, Cnn>(firstConnectionPiece, distanceOnOldConnection,
						direction));
			} else {
				entity.setConnectionElementPosition(new ConnectionPosition<E, Ctr, Cnn>(secondConnectionPiece, distanceOnOldConnection
						- distanceTillNewConnector, direction));
			}
		}
	}

	public Collection<Cnn> getConnections() {
		return Collections.unmodifiableCollection(connections);
	}

	public Collection<Ctr> getConnectors() {
		return Collections.unmodifiableCollection(connectors);
	}

	public void deploy(E connectionEntity, ConnectionElementPosition<E, Ctr, Cnn> position) throws IllegalDeploymentException {
		if (connectionEntity == null) {
			throw new IllegalArgumentException();
		}
		try {
			position.getConnectionElement().deploy(connectionEntity, position);
			addMovingEntity(connectionEntity);
			EventBroker.getEventBroker().notifyAll(new ConnectionEntityDeployedEvent(connectionEntity, position));
		} catch (IllegalDeploymentException e) {
			EventBroker.getEventBroker().notifyAll(new PhysicalEntityDeployFailedEvent(connectionEntity, position));
			throw e;
		}
	}

	public void undeploy(E connectionEntity) throws UnsupportedOperationException {
		// bij implementatie events gooien bij succes en failure
		throw new UnsupportedOperationException();
	}

	public Connector<E, Ctr, Cnn> getConnectorAtPosition(ContinuousPosition position) {
		if (position == null) {
			throw new IllegalArgumentException();
		}
		for (Connector<E, Ctr, Cnn> connector : connectors) {
			if (connector.getPosition().equals(position)) {
				return connector;
			}
		}
		throw new IllegalArgumentException();
	}

	public void deployOnConnector(E entity, long posX, long posY, boolean onroad) throws IllegalDeploymentException {
		if (entity == null) {
			throw new IllegalArgumentException();
		}
		Ctr connector = findConnector(posX, posY);
		deploy(entity, new ConnectorPosition<E, Ctr, Cnn>(connector, onroad));
	}

	private Ctr findConnector(long posX, long posY) {
		if (posX < 0 || posY < 0) {
			throw new IllegalArgumentException();
		}
		for (Ctr connector : getConnectors()) {
			if (connector.getPosition().getX() == posX && connector.getPosition().getY() == posY) {
				return connector;
			}
		}
		throw new IllegalArgumentException();
	}

	public void deployResource(StaticResource<Ctr> resource, Connector<E, Ctr, Cnn> connector) {
		if (resource == null || connector == null) {
			throw new IllegalArgumentException();
		}
		if (!connectors.contains(connector)) {
			throw new IllegalArgumentException();
		}
		if (getResources().contains(resource)) {
			throw new IllegalArgumentException();
		}
		connector.addResource(resource);
		addResource(resource);
		EventBroker.getEventBroker().notifyAll(new ResourceDeployedEvent(resource, connector));
	}
}
