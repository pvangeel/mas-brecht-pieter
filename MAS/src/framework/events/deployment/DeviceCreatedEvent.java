package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.devices.Device;
/**
 * A device was created
 *
 */
public class DeviceCreatedEvent extends Event {

	private int deviceId;
	private int communicationCapabilityId;
	private int storageCapabilityId;

	public DeviceCreatedEvent(Device device) {
		deviceId = device.getId();
		communicationCapabilityId = device.hasCommunicationCapability() ? device.getCommunicationCapability().getId() : -1;
		storageCapabilityId = device.hasStorageCapability() ? device.getStorageCapability().getId() : -1;
	}

	protected DeviceCreatedEvent() {
	}

	public int getDeviceId() {
		return deviceId;
	}

	public boolean hasCommunicationCapability() {
		return communicationCapabilityId != -1;
	}

	public int getCommunicationCapabilityId() {
		if (!hasCommunicationCapability()) {
			throw new IllegalStateException();
		}
		return communicationCapabilityId;
	}

	public boolean hasStorageCapability() {
		return storageCapabilityId != -1;
	}

	public int getStorageCapabilityId() {
		if (!hasStorageCapability()) {
			throw new IllegalStateException();
		}
		return storageCapabilityId;
	}

	@Override
	public String toString() {
		return "Device " + deviceId + " was created. With communication capability " + communicationCapabilityId
				+ " and storage capability " + storageCapabilityId;
	}

}
