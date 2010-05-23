package pclabs3.configuration.instructions;

import pclabs3.layer.physical.Site;
import framework.instructions.creation.CreateInstruction;

public class CreateSiteInstruction extends CreateInstruction {

	public CreateSiteInstruction(long executionTime, int siteId) {
		super(executionTime, siteId);
	}

	protected CreateSiteInstruction() {
	}

	@Override
	protected Object createObject() {
		return new Site();
	}

}
