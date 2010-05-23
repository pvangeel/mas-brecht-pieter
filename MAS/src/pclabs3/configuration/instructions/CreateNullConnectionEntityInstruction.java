package pclabs3.configuration.instructions;

import pclabs3.layer.physical.NullConnectionEntity;
import framework.instructions.creation.CreateInstruction;

public class CreateNullConnectionEntityInstruction extends CreateInstruction {


	public CreateNullConnectionEntityInstruction(long executionTime, int objectId) {
		super(executionTime, objectId);
		
	}
	
	@Override
	protected Object createObject() {
		return new NullConnectionEntity();
	}

}





