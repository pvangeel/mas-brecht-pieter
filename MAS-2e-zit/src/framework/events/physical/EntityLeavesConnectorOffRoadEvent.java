package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
/**
 * 
 * Indicates an entity has left the offroad part of a connector
 *
 */
public class EntityLeavesConnectorOffRoadEvent extends Event {

	private int connectionEntityId;
	private int connectorId;
	private int connectionId;

	public EntityLeavesConnectorOffRoadEvent(ConnectionEntity<?,?,?> connectionEntity,
			Connector<?,?,?> connector, Connection<?,?,?> toConnection) {
		this.connectionEntityId = connectionEntity.getId();
		this.connectorId = connector.getId();
		this.connectionId = toConnection.getId();
	}
	
	protected EntityLeavesConnectorOffRoadEvent() {
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
		return "Connection Entity " + connectionEntityId + " has left the off-road part of Connector " + connectorId + " on outgoing connection " + connectionId;
	}
}
