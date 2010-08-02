package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
/**
 * 
 * Indicates an entity has left the onroad part of a connector
 *
 */
public class EntityLeavesConnectorOnRoadEvent extends Event {

	private int connectionEntityId;
	private int connectorId;
	private int connectionId;

	public EntityLeavesConnectorOnRoadEvent(ConnectionEntity<?,?,?> connectionEntity,
			Connector<?,?,?> connector, Connection<?,?,?> toConnection) {
		this.connectionEntityId = connectionEntity.getId();
		this.connectorId = connector.getId();
		this.connectionId = toConnection.getId();
	}
	
	protected EntityLeavesConnectorOnRoadEvent() {
	}

	public int getConnectionEntityId() {
		return connectionEntityId;
	}

	public int getConnectorId() {
		return connectorId;
	}

	public int getConnectionId() {
		return connectionId;
	}

	@Override
	public String toString() {
		return "Connection Entity " + connectionEntityId + " has left the on-road part of Connector " + connectorId + " on outgoing connection " + connectionId;
	}
	
}
