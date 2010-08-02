package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationPackage;
import framework.layer.deployment.devices.Device;
/**
 * A communication package was dropped on a device
 *
 */
public class CommunicationPackageDroppedOnDeviceEvent extends Event{
	
	private int deviceId;
	private int packageId;
	
	public CommunicationPackageDroppedOnDeviceEvent(Device device, CommunicationPackage commPack){
		deviceId = device.getId();
		packageId = commPack.getId();
	}
	
	protected CommunicationPackageDroppedOnDeviceEvent() {
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public int getPackageId() {
		return packageId;
	}
	
	@Override
	public String toString() {
		return "Communication package " + packageId + " was dropped on Device " + deviceId;
	}
	
}
