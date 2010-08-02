package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;
/**
 * 
 * Indicates a ConnectionEntity was deployed
 *
 */
public class ConnectionEntityDeployedEvent extends Event {

	private int entityId;
	private int connectionId;
	private long newDistance;
	private int connectorId;

	public ConnectionEntityDeployedEvent(ConnectionEntity<?,?,?> entity, ConnectionElementPosition<?,?,?> position) {
		this.entityId = entity.getId();
		if(entity.getPosition().isOnConnection())
		{			
			this.connectionId = entity.getConnectionPosition().getConnection().getId();
			this.newDistance = entity.getConnectionPosition().getDistance();
			this.connectorId = -1;
		} else {
			this.connectionId = -1;
			this.newDistance = -1;
			this.connectorId = entity.getConnectorPosition().getConnector().getId();
		}
	}

	protected ConnectionEntityDeployedEvent() {
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
			return "Entity " + entityId + " was deployed on Connection " + connectionId + " at distance " + newDistance;
		} else {
			return "Entity " + entityId + " was deployed on Connector " + connectorId;
		}
	}

}
