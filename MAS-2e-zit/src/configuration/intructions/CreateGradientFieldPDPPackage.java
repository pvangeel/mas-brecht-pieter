package configuration.intructions;

import layer.physical.entities.Crossroads;
import layer.physical.entities.GradientFieldPDPPackage;
import framework.instructions.creation.CreateInstruction;

/**
 * Instruction to create a TRUCK and DEPLOY the truck in the first available CROSSROAD
 * 
 * @author marioct
 *
 */
public class CreateGradientFieldPDPPackage extends CreateInstruction {

	private Crossroads origin;
	private Crossroads destination;
	private double weigth;
	private int id;

	public CreateGradientFieldPDPPackage(long executionTime, int objectId, Crossroads origin, Crossroads destination, double weight) {
		super(executionTime, objectId);

		this.weigth = weight;
		this.origin = origin;
		this.destination = destination;
		this.id = objectId;
	}
	
	@Override
	protected Object createObject() {
		GradientFieldPDPPackage ret = new GradientFieldPDPPackage(id, origin, destination, weigth);
		origin.addPackage(ret); //deploys this package in the crossroad that is the package origin
		return ret;
	}

}





