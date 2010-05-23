package pclabs.physicallayer.command;

import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;
import framework.layer.physical.command.move.MoveCommand;
import framework.layer.physical.entities.vehicles.MoveUncompletedException;

public class MoveForwardCommand extends MoveCommand<Truck, Crossroads, Road> {

	public MoveForwardCommand(Truck sv) {
		super(sv);
	}

	@Override
	public void execute() throws IllegalCommandException, CommandUncompletedException {
		if (!getEntity().getPosition().isOnConnection()) {
			throw new IllegalCommandException(this);
		}
		long availableDistance = getEntity().getConnectionPosition().getConnection().getDistanceTillFirstEntityFromEntity(getEntity())
				- getEntity().getMinDistance();
		long connectionLengthLeftDistance = getEntity().getConnectionPosition().getDistanceTillEnd();
		long timeDistance = (long) Math.floor(getEntity().getAvailableTime() * getEntity().getSpeed());
		long wannaDriveDistance = getEntity().getConnectionPosition().getDistanceTillEnd();

		long smallestDistance = Math.min(availableDistance, timeDistance);
		smallestDistance = Math.min(wannaDriveDistance, smallestDistance);
		smallestDistance = Math.min(connectionLengthLeftDistance, smallestDistance);
		smallestDistance = (smallestDistance < 0) ? 0 : smallestDistance;

		try {
			getEntity().moveOnConnection(smallestDistance, wannaDriveDistance);
		} catch (MoveUncompletedException e) {
			throw new CommandUncompletedException();
		}
	}
}