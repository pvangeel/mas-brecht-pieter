package pclabs3.layer.physical;

import framework.layer.physical.command.Command;
import framework.layer.physical.entities.vehicles.Vehicle;

public class NullConnectionEntity extends Vehicle<NullConnectionEntity, SiteLocation, NullConnection>{

	public NullConnectionEntity() {
	}

	/**
	 * The failsafe command tells the vehicle what to do if the agent doesn't tell the 
	 * vehicle what to do
	 */
	@Override
	protected Command<? extends NullConnectionEntity> loadFailSafeCommand() {
		return null;
	}
}
