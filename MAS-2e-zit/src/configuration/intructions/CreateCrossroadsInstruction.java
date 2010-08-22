package configuration.intructions;

import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.instructions.creation.CreateInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalLayer;

public class CreateCrossroadsInstruction extends CreateInstruction {

	private PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>> physicalLayer;

	public CreateCrossroadsInstruction(long executionTime, int crossroadsId) {
		super(executionTime, crossroadsId);
	}

	protected CreateCrossroadsInstruction() {
	}

	public CreateCrossroadsInstruction(PhysicalLayer<PhysicalConnectionStructure<Truck, Crossroads, Road>> physicalLayer, long currentTime, int nextId) {
		this(currentTime, nextId);
		this.physicalLayer = physicalLayer;
	}

	@Override
	protected Object createObject() {
		Crossroads crossroads = new Crossroads();
		physicalLayer.getRegistry().register(crossroads);
		return crossroads;
	}

}
