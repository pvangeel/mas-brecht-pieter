package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.StaticResource;

public class ResourceDeployedEvent extends Event {

	private int resourceId;
	private int connectorId;

	public ResourceDeployedEvent(StaticResource<?> resource, Connector<?, ?, ?> connector) {
		if (resource == null || connector == null) {
			throw new IllegalArgumentException();
		}
		this.resourceId = resource.getId();
		this.connectorId = connector.getId();
	}
	
	protected ResourceDeployedEvent() {
	}

	public int getResourceId() {
		return resourceId;
	}

	public int getConnectorId() {
		return connectorId;
	}

	@Override
	public String toString() {
		return "Resource " + resourceId + " was deployed on connector " + connectorId;
	}

}
