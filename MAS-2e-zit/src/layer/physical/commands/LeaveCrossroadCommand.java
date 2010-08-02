package layer.physical.commands;


import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.layer.physical.command.CommandUncompletedException;
import framework.layer.physical.command.IllegalCommandException;
import framework.layer.physical.command.move.MoveCommand;
import framework.layer.physical.connections.Connector.CannotLeaveOffroadException;
import framework.layer.physical.connections.Connector.CannotLeaveOnroadException;
import framework.layer.physical.connections.Connector.IllegalLeaveException;

public class LeaveCrossroadCommand extends MoveCommand<Truck, Crossroads, Road> {

	private Road toConnection;

	public LeaveCrossroadCommand(Truck vehicle, Road toConnection) {
		super(vehicle);
		if (toConnection == null) {
			throw new IllegalArgumentException();
		}
		this.toConnection = toConnection;
	}

	@Override
	public void execute() throws IllegalCommandException, CommandUncompletedException {
		if(getEntity().isOnConnector()) {
			if (toConnection.getDistanceTillFirstEntityFromConnector(getEntity().getConnectorPosition().getConnector()) < getEntity()
					.getMinDistance()) {
				throw new CommandUncompletedException();
			}
			
			if ( getEntity().getConnectorPosition().isOnroad()) {
				leaveConnectorOnroad();
			} else {
				leaveConnectorOffroad();
			}
		}
	}

	private void leaveConnectorOnroad() throws CommandUncompletedException, IllegalCommandException {
		try {
			getEntity().getConnectorPosition().getConnector().leaveOnroad(getEntity(), toConnection);
		} catch (CannotLeaveOnroadException e) {
			throw new CommandUncompletedException();
		} catch (IllegalLeaveException e) {
			throw new IllegalCommandException(this);
		}
	}

	private void leaveConnectorOffroad() throws CommandUncompletedException, IllegalCommandException {
		try {
			getEntity().getConnectorPosition().getConnector().leaveOffroad(getEntity(), toConnection);
		} catch (CannotLeaveOffroadException e) {
			throw new CommandUncompletedException();
		} catch (IllegalLeaveException e) {
			throw new IllegalCommandException(this);
		}
	}
}