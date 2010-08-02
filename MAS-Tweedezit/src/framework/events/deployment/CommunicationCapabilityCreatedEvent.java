package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationCapability;
/**
 * Indicates a communication capability was created
 *
 */
public class CommunicationCapabilityCreatedEvent extends Event {

	private int communicationCapabilityId;
	private int[] routerIds;

	public CommunicationCapabilityCreatedEvent(CommunicationCapability communicationCapability) {
		if (communicationCapability == null) {
			throw new IllegalArgumentException();
		}
		this.communicationCapabilityId = communicationCapability.getId();
		routerIds = new int[communicationCapability.getRouters().size()];
		for (int i = 0; i < communicationCapability.getRouters().size(); i++) {
			routerIds[i] = communicationCapability.getRouters().get(i).getId();
		}
	}

	protected CommunicationCapabilityCreatedEvent() {
	}

	public int getCommunicationCapabilityId() {
		return communicationCapabilityId;
	}

	public int[] getRouterIds() {
		return routerIds;
	}

	@Override
	public String toString() {
		return "Communication capability " + communicationCapabilityId + " created with routers " + routerIds;
	}
}
