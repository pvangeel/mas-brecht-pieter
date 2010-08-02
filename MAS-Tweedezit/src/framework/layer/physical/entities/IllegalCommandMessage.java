package framework.layer.physical.entities;

import framework.layer.deployment.communication.CommunicationPackage;
import framework.layer.physical.command.Command;

/**
 * A message sent to the devices on a physical entity to notify the agents
 * running on the devices that the physical entity attempted to execute an
 * illegal command
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class IllegalCommandMessage extends CommunicationPackage {

	private final Command<?> command;

	public IllegalCommandMessage(Command<?> command) {
		this.command = command;
	}

	public Command<?> getCommand() {
		return command;
	}

	@Override
	public void onArrival() {
		// do nothing
	}

	@Override
	public double getByteSize() {
		return 0;
	}

}
