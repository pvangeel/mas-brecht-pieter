package framework.events.physical;

import framework.events.Event;
import framework.layer.agent.Agent;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * An agent has issued a command to a physical entity
 *
 */
public class CommandIssuedEvent extends Event {

	private int physicalEntityId;
	private int commandId;
	private int commandingAgentId;

	public CommandIssuedEvent(Agent commandingAgent, PhysicalEntity<?> physicalEntity, Command<?> command) {
		this.commandingAgentId = commandingAgent.getId();
		this.physicalEntityId = physicalEntity.getId();
		this.commandId = command.getId();
	}

	protected CommandIssuedEvent() {
	}

	public int getPhysicalEntityId() {
		return physicalEntityId;
	}

	public int getCommandId() {
		return commandId;
	}

	public int getCommandingAgentId() {
		return commandingAgentId;
	}
	
	@Override
	public String toString() {
		return "Agent " + commandingAgentId + " has issued command " + commandId + " to Physical Entity " + physicalEntityId;
	}

}
