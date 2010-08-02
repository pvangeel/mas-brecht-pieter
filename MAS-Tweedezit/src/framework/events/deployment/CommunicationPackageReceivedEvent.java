package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationPackage;
import framework.layer.deployment.devices.Device;

/**
 * This event is sent when a device accepts a message over one of
 * it's communication links.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class CommunicationPackageReceivedEvent extends Event {

	private int deviceId;
	private int packageId;
	
	public CommunicationPackageReceivedEvent(Device device, CommunicationPackage commPack){
		deviceId = device.getId();
		packageId = commPack.getId();
	}
	
	protected CommunicationPackageReceivedEvent() {
	}
	
	public int getDeviceId() {
		return deviceId;
	}
	
	public int getPackageId() {
		return packageId;
	}
	
	@Override
	public String toString() {
		return "Device " + deviceId + " has received communication package " + packageId;
	}
	
}
