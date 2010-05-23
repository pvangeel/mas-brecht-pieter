package framework.layer.agent;

import framework.layer.Layer;

public class AgentLayer extends Layer {

	private final AgentStructure agentStructure = new AgentStructure(this);

	public AgentLayer() {
		super(1000);
	}

	public AgentStructure getAgentStructure() {
		return agentStructure;
	}
}
