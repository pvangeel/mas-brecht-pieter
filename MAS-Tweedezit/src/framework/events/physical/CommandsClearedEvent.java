package framework.events.physical;

import framework.events.Event;
import framework.layer.agent.Agent;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * Indicates an agent has cleared the list of commands on a physical entity
 *
 */
public class CommandsClearedEvent extends Event {

	private int agentId;
	private int physicalEntityId;

	public CommandsClearedEvent(Agent commandingAgent, PhysicalEntity<?> physicalEntity) {
		this.agentId = commandingAgent.getId();
		this.physicalEntityId = physicalEntity.getId();
	}

	protected CommandsClearedEvent() {
	}

	public int getAgentId() {
		return agentId;
	}

	public int getPhysicalEntityId() {
		return physicalEntityId;
	}
	
	@Override
	public String toString() {
		return "Agent " + agentId + " has cleared the command list of Physical Entitiy " + physicalEntityId;
	}
}
