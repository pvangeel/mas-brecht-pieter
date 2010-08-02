package framework.events.agent;

import framework.events.Event;
import framework.layer.agent.Agent;
import framework.layer.deployment.devices.Device;

/**
 * Event that fires when an agent with agentId was deployed on the device with deviceId 
 *
 */
public class AgentDeployedEvent extends Event {

	private int agentId;
	private int deviceId;

	public AgentDeployedEvent(Agent agent, Device device) {
		this.agentId = agent.getId();
		this.deviceId = device.getId();
	}
	
	protected AgentDeployedEvent() {
	}

	public int getAgentId() {
		return agentId;
	}

	public int getDeviceId() {
		return deviceId;
	}
	
	public String toString() {
		return "Agent " + agentId + " has been deployed on Device " + deviceId;
	}

}
