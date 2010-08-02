package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * Indicates a device was detached from a physical entity
 *
 */
public class DeviceAttachedEvent extends Event {

	private int deviceId;
	private int entityId;

	public DeviceAttachedEvent(Device device, PhysicalEntity<?> entity) {
		this.deviceId = device.getId();
		this.entityId = entity.getId();
	}
	
	protected DeviceAttachedEvent() {
	}

	public int getDeviceId() {
		return deviceId;
	}

	public int getEntityId() {
		return entityId;
	}
	
	@Override
	public String toString() {
		return "Device " + deviceId + " was attached to Physical Entitiy " + entityId;
	}

}
