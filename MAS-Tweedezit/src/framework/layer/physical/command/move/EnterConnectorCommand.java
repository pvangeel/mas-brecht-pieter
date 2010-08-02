package framework.layer.physical.command.move;

import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.connections.Connector.CannotEnterOffroadException;
import framework.layer.physical.connections.Connector.CannotEnterOnroadException;
import framework.layer.physical.connections.Connector.IllegalEnterException;
import framework.layer.physical.entities.vehicles.Vehicle;

public class EnterConnectorCommand<E extends Vehicle<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends MoveCommand<E, Ctr, Cnn> {

	private final boolean toOnRoad;

	public EnterConnectorCommand(E vehicle, boolean toOnRoad) {
		super(vehicle);
		this.toOnRoad = toOnRoad;
	}

	@Override
	public void execute() throws IllegalCommandException, CommandUncompletedException {
		if(!getEntity().isOnConnection())
			throw new IllegalCommandException(this);
		
		if (toOnRoad()) {
			try {
				getEntity().getConnectionPosition().getConnection().getConnectorAtEnd(getEntity().getConnectionPosition().getDirection())
						.enterOnroad(getEntity());
			} catch (CannotEnterOnroadException e) {
				throw new CommandUncompletedException();
			} catch (IllegalEnterException e) {
				throw new IllegalCommandException(this);
			}
		} else {
			try {
				getEntity().getConnectionPosition().getConnection().getConnectorAtEnd(getEntity().getConnectionPosition().getDirection())
						.enterOffroad(getEntity());
			} catch (CannotEnterOffroadException e) {
				throw new CommandUncompletedException();
			} catch (IllegalEnterException e) {
				throw new IllegalCommandException(this);
			}
		}

	}

	public boolean toOnRoad() {
		return toOnRoad;
	}

}
