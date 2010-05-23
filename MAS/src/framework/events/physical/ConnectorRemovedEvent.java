package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connector;
/**
 * 
 * indicates a connector has been removed
 *
 */
public class ConnectorRemovedEvent extends Event {

	private int connectorId;

	public ConnectorRemovedEvent(Connector<?,?,?> connector) {
		if (connector == null) {
			throw new IllegalArgumentException();
		}
		this.connectorId = connector.getId();
	}

	protected ConnectorRemovedEvent() {
	}

	public int getConnectionId() {
		return connectorId;
	}

}
