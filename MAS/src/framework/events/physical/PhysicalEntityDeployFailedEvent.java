package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.ConnectorPosition;

public class PhysicalEntityDeployFailedEvent extends Event {

	private final int entityId;
	private final int connectionId;
	private final long newDistance;
	private final int connectorId;

	public PhysicalEntityDeployFailedEvent(ConnectionEntity<?,?,?> entity, ConnectionElementPosition<?,?,?> position) {
		this.entityId = entity.getId();
		if (position.isOnConnection()) {
			this.connectionId = ((ConnectionPosition<?,?,?>) position).getConnection().getId();
			this.newDistance = ((ConnectionPosition<?,?,?>) position).getDistance();
			this.connectorId = -1;
		} else {
			this.connectionId = -1;
			this.newDistance = -1;
			this.connectorId = ((ConnectorPosition<?,?,?>) position).getConnector().getId();
		}
	}

	public int getEntityId() {
		return entityId;
	}

	public int getConnectionId() {
		return connectionId;
	}

	public long getNewDistance() {
		return newDistance;
	}

	public int getConnectorId() {
		return connectorId;
	}
	
	@Override
	public String toString() {
		if(connectorId == -1)
		{
			return "Failed to deploy Entity " + entityId + " on Connection " + connectionId + " at distance " + newDistance;
		} else {
			return "Failed to deploy Entity " + entityId + " on Connector " + connectorId;
		}
	}
	

}
