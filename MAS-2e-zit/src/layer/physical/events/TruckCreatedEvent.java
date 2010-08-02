package layer.physical.events;

import layer.physical.entities.Truck;
import framework.events.Event;

/**
 * Truck creation event
 * 
 * @author marioct
 *
 */
public class TruckCreatedEvent extends Event {

	private int vehicleId;
	private double speed;

	public TruckCreatedEvent(Truck truck) {
		this.vehicleId = truck.getId();
		this.speed = truck.getSpeed();
	}

	protected TruckCreatedEvent() {
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public double getSpeed() {
		return speed;
	}

	@Override
	public String toString() {
		return "Truck: " + vehicleId + " with a speed of " + speed + " was created";
	}

}
