package pclabs4.configuration.instructions;

import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.PDPPackage;
import framework.instructions.creation.CreateInstruction;

/**
 * Instruction to create a TRUCK and DEPLOY the truck in the first available CROSSROAD
 * 
 * @author marioct
 *
 */
public class CreatePDPPackage extends CreateInstruction {

	private Crossroads origin;
	private Crossroads destination;
	private double weigth;
	private int id;

	public CreatePDPPackage(long executionTime, int objectId, Crossroads origin, Crossroads destination, double weight) {
		super(executionTime, objectId);

		this.weigth = weight;
		this.origin = origin;
		this.destination = destination;
		this.id = objectId;
	}
	
	@Override
	protected Object createObject() {
		PDPPackage ret = new PDPPackage(id, origin, destination, weigth);
		origin.addPackage(ret); //deploys this package in the crossroad that is the package origin
		return ret;
	}

}





