package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.storage.StorageCapability;
/**
 * A storage capability has been created
 *
 */
public class StorageCapabilityCreatedEvent extends Event {

	private int storageCapabilityId;
	private long storageCapacity;

	public StorageCapabilityCreatedEvent(StorageCapability storageCapability) {
		if (storageCapability == null) {
			throw new IllegalArgumentException();
		}
		this.storageCapabilityId = storageCapability.getId();
		this.storageCapacity = storageCapability.getCapacity();
	}

	protected StorageCapabilityCreatedEvent() {
	}

	public int getStorageCapabilityId() {
		return storageCapabilityId;
	}

	public long getStorageCapacity() {
		return storageCapacity;
	}
}
