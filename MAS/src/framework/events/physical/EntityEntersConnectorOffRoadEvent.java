package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
/**
 * 
 * Indicates an entity has entered the off road part of a connector
 *
 */
public class EntityEntersConnectorOffRoadEvent extends Event {

	private int connectionEntityId;
	private int connectorId;

	public EntityEntersConnectorOffRoadEvent(ConnectionEntity<?,?,?> connectionEntity,
			Connector<?,?,?> connector) {
		this.connectionEntityId = connectionEntity.getId();
		this.connectorId = connector.getId();
	}
	
	protected EntityEntersConnectorOffRoadEvent() {
	}

	public int getConnectionEntityId() {
		return connectionEntityId;
	}

	public int getConnectorId() {
		return connectorId;
	}
	
	@Override
	public String toString() {
		return "Connection Entity " + connectionEntityId + " has entered the off-road part of Connector " + connectorId;
	}
	
}
