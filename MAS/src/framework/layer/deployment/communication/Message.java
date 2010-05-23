package framework.layer.deployment.communication;

import framework.layer.deployment.devices.InactiveCapabilityException;

/**
 * A communication package that is a simple text message. On arrival it is added
 * to the device's package inbox. 
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Message extends CommunicationPackage {

	@Override
	public void onArrival() {
		try {
			getDestination().getCommunicationCapability().addInboxCommunicationPackage(this);
		} catch (InactiveCapabilityException e) {
			System.err.println("WARNING: package " + this + " has been dropped. Communication capability at destination disabled.");
		}
	}

}
