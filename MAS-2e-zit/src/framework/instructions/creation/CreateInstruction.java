package framework.instructions.creation;

import configuration.intructions.CreatePDPPackage;
import framework.instructions.Instruction;
import framework.layer.physical.PhysicalStructure;

public abstract class CreateInstruction extends Instruction<PhysicalStructure<?>> {

	private int objectId;

	public CreateInstruction(long executionTime, int objectId) {
		super(executionTime);
		if (objectId < 0) {
			throw new IllegalArgumentException("illegal id: " + objectId);
		}
		this.objectId = objectId;
	}

	protected CreateInstruction() {
	}

	@Override
	public void execute() {
		Object object = createObject();
//		System.out.println("creating: " + object + " with id: " +objectId );
		getInstructionManager().addCreatedObject(objectId, object);
	}

	protected abstract Object createObject();

}
