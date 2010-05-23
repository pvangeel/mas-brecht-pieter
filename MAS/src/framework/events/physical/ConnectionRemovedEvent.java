package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connection;
/**
 * 
 * Indicates a connection has been removed
 *
 */
public class ConnectionRemovedEvent extends Event {

	private int connectionId;

	public ConnectionRemovedEvent(Connection<?,?,?> connection) {
		if (connection == null) {
			throw new IllegalArgumentException();
		}
		this.connectionId = connection.getId();
	}

	protected ConnectionRemovedEvent() {
	}

	public int getConnectionId() {
		return connectionId;
	}
}
