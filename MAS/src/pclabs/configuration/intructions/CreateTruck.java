package pclabs.configuration.intructions;

import pclabs.physicallayer.entities.Truck;
import pclabs.physicallayer.events.TruckCreatedEvent;
import framework.events.EventBroker;
import framework.instructions.creation.CreateInstruction;

/**
 * Instruction to create a TRUCK and DEPLOY the truck in the first available CROSSROAD
 * 
 * @author marioct
 *
 */
public class CreateTruck extends CreateInstruction {

	private double speed;

	public CreateTruck(long executionTime, int objectId, double speed) {
		super(executionTime, objectId);
		
		this.speed = speed;
	}
	
	@Override
	protected Object createObject() {
		return new Truck(speed);
	}

}





