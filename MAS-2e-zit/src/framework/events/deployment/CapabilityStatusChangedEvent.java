package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.devices.Capability;

/**
 * Indicates that the status of the capability with capabilityId was changed to
 * the value of parameter active.
 * 
 */
public class CapabilityStatusChangedEvent extends Event {

	private int capabilityId;
	private boolean active;

	public CapabilityStatusChangedEvent(Capability capability, boolean active) {
		this.capabilityId = capability.getId();
		this.active = active;
	}

	protected CapabilityStatusChangedEvent() {
	}

	public int getCapabilityId() {
		return capabilityId;
	}

	public boolean isActive() {
		return active;
	}

	@Override
	public String toString() {
		if (active) {
			return "The Communication Capability of device " + capabilityId + " was set to active";
		} else {
			return "The Communication Capability of device " + capabilityId
					+ " was set to non-active";
		}
	}

}
