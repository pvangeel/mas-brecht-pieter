package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
/**
 * Indicates a connection was added to the physical structure
 *
 */
public class ConnectionAddedEvent extends Event {

	private int connectionId;
	private int connector1Id;
	private int connector2Id;

	public ConnectionAddedEvent(Connection<?,?,?> connection, Connector<?,?,?> connector1, Connector<?,?,?> connector2) {
		this.connectionId = connection.getId();
		this.connector1Id = connector1.getId();
		this.connector2Id = connector2.getId();
	}

	protected ConnectionAddedEvent() {
	}

	public int getConnectionId() {
		return connectionId;
	}

	public int getConnector1Id() {
		return connector1Id;
	}

	public int getConnector2Id() {
		return connector2Id;
	}
	
	@Override
	public String toString() {
		return "Connection " + connectionId + " was added between connectors " + connector1Id + " and " + connector2Id;
	}

}
