package framework.layer.physical.command.move;

import framework.layer.physical.command.Command;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.vehicles.Vehicle;

/**
 * A generic move command instructing a vehicle to move in the physical world
 * 
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */
 
public abstract class MoveCommand<E extends Vehicle<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Command<E> {

	public MoveCommand(E vehicle) {
		super(vehicle);
	}

}
