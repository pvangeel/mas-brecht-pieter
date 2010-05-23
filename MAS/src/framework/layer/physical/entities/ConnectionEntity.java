package framework.layer.physical.entities;

import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.ConnectorPosition;

/**
 * PhysicalEntity that travels over a network of connections and connectors
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class ConnectionEntity<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends MovingEntity<E> {

	private ConnectionElementPosition<E, Ctr, Cnn> connectionElementPosition;

	//FIXME MARiO encapsulation problem
	@Override
	public ConnectionElementPosition<E, Ctr, Cnn> getPosition() {
		if (connectionElementPosition == null) {
			throw new IllegalStateException();
		}
		return connectionElementPosition;
	}

	public boolean isOnConnector() {
		return getPosition().isOnConnector();
	}

	public boolean isOnConnection() {
		return getPosition().isOnConnection();
	}

	public ConnectionPosition<E,Ctr,Cnn> getConnectionPosition() {
		if (!isOnConnection()) {
			throw new IllegalStateException();
		}
		return (ConnectionPosition<E,Ctr,Cnn>) getPosition();
	}

	public ConnectorPosition<E,Ctr,Cnn> getConnectorPosition() {
		if (!isOnConnector()) {
			throw new IllegalStateException();
		}
		return (ConnectorPosition<E,Ctr,Cnn>) getPosition();
	}

	//FIXME MARiO encapsulation problem
	public void setConnectionElementPosition(ConnectionElementPosition<E,Ctr,Cnn> connectionElementPosition) {
		if (connectionElementPosition == null) {
			throw new IllegalArgumentException();
		}
		this.connectionElementPosition = connectionElementPosition;
	}
}
