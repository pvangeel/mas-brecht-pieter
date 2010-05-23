package framework.layer.physical.position;

import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;

/**
 * The position of a connector.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class ConnectorPosition<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionElementPosition<E, Ctr, Cnn> {

	private final boolean onroad;

	public ConnectorPosition(Ctr connector, boolean onroad) {
		super(connector);
		this.onroad = onroad;
		setupXY();
	}

	public boolean isOnroad() {
		return onroad;
	}

	private void setupXY() {
		setX(getConnector().getPosition().getX());
		setY(getConnector().getPosition().getY());
	}

	@SuppressWarnings("unchecked")
	public Ctr getConnector() {
		return (Ctr) getConnectionElement();
	}

	@Override
	public boolean isOnConnection() {
		return false;
	}

	@Override
	public boolean isOnConnector() {
		return true;
	}
}
