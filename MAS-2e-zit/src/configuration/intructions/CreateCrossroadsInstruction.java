package configuration.intructions;

import layer.physical.entities.Crossroads;
import framework.instructions.creation.CreateInstruction;

public class CreateCrossroadsInstruction extends CreateInstruction {

	public CreateCrossroadsInstruction(long executionTime, int crossroadsId) {
		super(executionTime, crossroadsId);
	}

	protected CreateCrossroadsInstruction() {
	}

	@Override
	protected Object createObject() {
		return new Crossroads();
	}

}
