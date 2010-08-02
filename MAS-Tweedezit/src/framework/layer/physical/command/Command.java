package framework.layer.physical.command;

import framework.layer.physical.entities.PhysicalEntity;
import framework.utils.IdGenerator;

/**
 * A command is any order that an agent wants to give to the physical entity to
 * which his device his attached.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Command<E extends PhysicalEntity<?>> {

	private final int id = IdGenerator.getIdGenerator().getNextId(Command.class);

	private final E physicalEntity;

	public int getId() {
		return id;
	}

	public Command(E physicalEntity) {
		if (physicalEntity == null) {
			throw new IllegalArgumentException();
		}
		this.physicalEntity = physicalEntity;
	}

	/**
	 * Execute instructions to attain the desired result for this command. The
	 * instructions are able to attain the result, even when part of the result
	 * has already been obtained by a previous partial execution of this
	 * command, that was signalled by the commandUncompletedException
	 * 
	 */
	public abstract void execute() throws IllegalCommandException, CommandUncompletedException;

	public E getEntity() {
		return physicalEntity;
	}
}