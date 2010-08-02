package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * Indicates an entity attempted to execute an illegal command
 *
 */
public class IllegalCommandEvent extends Event {

	private int entityId;
	private int commandId;

	public IllegalCommandEvent(PhysicalEntity<?> physicalEntity, Command<?> command) {
		this.entityId = physicalEntity.getId();
		this.commandId = command.getId();
	}
	
	protected IllegalCommandEvent() {
	}

	public int getEntityId() {
		return entityId;
	}

	public int getCommandId() {
		return commandId;
	}
	
	@Override
	public String toString() {
		return "Entity " + entityId + " has issued the illegal command " + commandId;
	}
	
}
