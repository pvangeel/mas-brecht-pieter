package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.ContinuousPosition;
/**
 * 
 * Indicates a connector has been added
 *
 */
public class ConnectorAddedEvent extends Event {

	private int connectorId;
	private long positionX;
	private long positionY;

	public ConnectorAddedEvent(Connector<?,?,?> connector, ContinuousPosition position) {
		this.connectorId = connector.getId();
		this.positionX = position.getX();
		this.positionY = position.getY();
	}
	
	protected ConnectorAddedEvent() {
	}

	public int getConnectorId() {
		return connectorId;
	}

	public long getPositionX() {
		return positionX;
	}

	public long getPositionY() {
		return positionY;
	}
	
	@Override
	public String toString() {
		return "Connector " + connectorId + " was added at position x: " + positionX + " y:" + positionY; 
	}


}
