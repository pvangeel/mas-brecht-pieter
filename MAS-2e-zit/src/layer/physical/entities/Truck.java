package layer.physical.entities;

import layer.devices.Trajectory;
import layer.physical.events.TruckCreatedEvent;
import environment.Environment;
import framework.events.EventBroker;
import framework.layer.physical.command.Command;
import framework.layer.physical.entities.vehicles.Vehicle;

public class Truck extends Vehicle<Truck, Crossroads, Road>{

	private final long minDistance = 0L;
	private PDPPackage pdpPackage;


	public Truck(double speedKmPerHour) {
		setSpeed(speedKmPerHour);
		
		EventBroker.getEventBroker().notifyAll(new TruckCreatedEvent(this));
	}

	public long getMinDistance() {
		return minDistance;
	}

	/**
	 * The failsafe command tells the vehicle what to do if the agent doesn't tell the 
	 * vehicle what to do
	 */
	@Override
	protected Command<? extends Truck> loadFailSafeCommand() {
//		return new RandomWalkFailSafeCommand(this);
		return null;
	}
	
	/**
	 * Sensing methods, CAN be executed directly by the agent
	 */
	public PDPPackageDTO getPDPPackageInfo() {
		if(pdpPackage != null) {
			return pdpPackage.getDTO();
		}
		else
			return null;
	}
	
	/**
	 * This method should be called just by commands.
	 * ACTION, should be called by command
	 */
	public void load() {
		if(this.pdpPackage != null) {
			throw new IllegalStateException("Truck is already carrying a package");
		}
		
		this.pdpPackage = getConnectorPosition().getConnector().pickPackage();
	}
	
	public void unload() {
		getConnectorPosition().getConnector().receivePackage(this.pdpPackage);
		pdpPackage = null;
	}
	
	
	public boolean hasPackage() {
		return pdpPackage != null;
	}
}
