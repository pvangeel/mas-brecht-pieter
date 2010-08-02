package framework.layer.deployment.communication;

import framework.layer.deployment.devices.Device;

/**
 * A communication package that contains executable code that is executed on the destination device on arrival
 * @author Bart Tuts, Jelle Van Gompel
 *
 */
public abstract class MobileCode extends CommunicationPackage {

	@Override
	public void onArrival() {
		executeCode(getDestination());
	}

	protected abstract void executeCode(Device device);

}
