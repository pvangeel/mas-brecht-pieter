package framework.initialization.spatial.connection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.ConnectorPosition;
import framework.layer.physical.position.Direction;

/**
 *
 * A specific kind of ConnectionSpatialPattern that returns a random position in the physical world 
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class RandomConnectionElementSpatialPattern<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionSpatialPattern<E, Ctr, Cnn> {

	private final Random random = new Random();

	protected ConnectorPosition<E, Ctr, Cnn> getRandomConnectorPosition(PhysicalConnectionStructure<E, Ctr, Cnn> structure) {
		List<Ctr> connectors = new ArrayList<Ctr>(structure.getConnectors());
		Collections.shuffle(connectors);
		return new ConnectorPosition<E, Ctr, Cnn>(connectors.get(0), true);
	}

	protected ConnectionPosition<E, Ctr, Cnn> getRandomConnectionPosition(PhysicalConnectionStructure<E, Ctr, Cnn> structure) {
		List<Connection<E, Ctr, Cnn>> connections = new ArrayList<Connection<E, Ctr, Cnn>>(structure.getConnections());
		Collections.shuffle(connections);
		Connection<E, Ctr, Cnn> connection = connections.get(0);
		int distance = random.nextInt((int) connection.getLength());
		Direction direction;
		if (connection.canTravel(Direction.TO_CONNECTOR_1)) {
			direction = Direction.TO_CONNECTOR_1;
		} else if (connection.canTravel(Direction.TO_CONNECTOR_2)) {
			direction = Direction.TO_CONNECTOR_2;
		} else {
			throw new IllegalStateException();
		}
		return new ConnectionPosition<E, Ctr, Cnn>(connection, distance, direction);
	}

	@Override
	public ConnectionElementPosition<E, Ctr, Cnn> getNextPosition(PhysicalConnectionStructure<E, Ctr, Cnn> structure) {
		if (random.nextBoolean()) {
			return getRandomConnectionPosition(structure);
		} else {
			return getRandomConnectorPosition(structure);
		}
	}

}
