package framework.events.physical;

import framework.events.Event;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.PhysicalEntity;
/**
 * An entity has completed a command
 *
 */
public class CommandCompletedEvent extends Event {

	private int entityId;
	private int commandId;

	public CommandCompletedEvent(PhysicalEntity<?> pe, Command<?> command) {
		this.entityId = pe.getId();
		this.commandId = command.getId();
	}
	
	protected CommandCompletedEvent() {
	}

	public int getEntityId() {
		return entityId;
	}

	public int getCommandId() {
		return commandId;
	}
	
	@Override
	public String toString() {
		return "Entity " + entityId + " has completed command " + commandId;
	}

}
