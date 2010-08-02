package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationPackage;
import framework.layer.deployment.devices.Device;

/**
 * This message is sent when a device starts transmitting a message over one of
 * it's communication links.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class PackageSentEvent extends Event {

	private int deviceId;
	private int packageId;
	
	public PackageSentEvent(Device device, CommunicationPackage commPack){
		deviceId = device.getId();
		packageId = commPack.getId();
	}
	
	protected PackageSentEvent() {
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public int getPackageId() {
		return packageId;
	}
	
	@Override
	public String toString() {
		return "Device " + deviceId + " has started transmiting communication package " + packageId;
	
	}
	
}
