package framework.layer.agent;

import java.util.Collection;
import java.util.HashSet;

import framework.events.EventBroker;
import framework.events.agent.AgentDeployedEvent;
import framework.layer.deployment.devices.Device;

/**
 * 
 * Contains the Agents existing in the Agent Layer.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class AgentStructure {

	private final AgentLayer agentLayer;

	private final Collection<Agent> agents = new HashSet<Agent>();

	public AgentStructure(AgentLayer agentLayer) {
		if (agentLayer == null) {
			throw new IllegalArgumentException();
		}
		this.agentLayer = agentLayer;
	}

	public void deployAgent(Agent agent, Device device) {
		// agent mag niet reeds op een andere device deployed zijn
		if (agent == null || device == null || agent.hasDevice()) {
			throw new IllegalArgumentException();
		}
		device.addAgent(agent);
		agent.setDevice(device);
		agents.add(agent);
		agentLayer.getRegistry().register(agent);

		agent.executeDeploymentOptions();
		EventBroker.getEventBroker().notifyAll(new AgentDeployedEvent(agent, device));
	}

	public AgentLayer getAgentLayer() {
		return agentLayer;
	}
}
