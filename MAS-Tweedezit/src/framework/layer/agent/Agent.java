package framework.layer.agent;

import framework.layer.TickListener;
import framework.layer.deployment.devices.Device;
import framework.utils.IdGenerator;

/**
 * 
 * An Agent, the main entity in the agent layer, who are deployed on Devices 
 *
 */

public abstract class Agent implements TickListener {

	private Device device;
	private final int id = IdGenerator.getIdGenerator().getNextId(Agent.class);

	public int getId() {
		return id;
	}

	public Device getDevice() {
		if (!hasDevice()) {
			throw new IllegalStateException();
		}
		return device;
	}

	public boolean hasDevice() {
		return device != null;
	}

	public void setDevice(Device device) {
		if (this.device != null) {
			throw new IllegalStateException("agent already deployed on a device");
		}
		this.device = device;
	}

	public abstract void executeDeploymentOptions();

}
