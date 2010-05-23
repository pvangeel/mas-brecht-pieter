package framework.layer.physical.position;

import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.ConnectionElement;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;

/**
 * 
 * A position on a connection element
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public abstract class ConnectionElementPosition<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Position {

	private final ConnectionElement<E, Ctr, Cnn, PhysicalConnectionStructure<E, Ctr, Cnn>> connectionElement;
	private long x;
	private long y;

	protected void setX(long x) {
		this.x = x;
	}

	protected void setY(long y) {
		this.y = y;
	}

	public ConnectionElementPosition(ConnectionElement<E, Ctr, Cnn, PhysicalConnectionStructure<E, Ctr, Cnn>> connectionElement) {
		this.connectionElement = connectionElement;
	}

	public ConnectionElement<E, Ctr, Cnn, PhysicalConnectionStructure<E, Ctr, Cnn>> getConnectionElement() {
		return connectionElement;
	}

	@Override
	public long getX() {
		return x;
	}

	@Override
	public long getY() {
		return y;
	}

	public abstract boolean isOnConnector();

	public abstract boolean isOnConnection();

}
