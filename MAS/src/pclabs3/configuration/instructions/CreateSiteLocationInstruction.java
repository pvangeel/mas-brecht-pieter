package pclabs3.configuration.instructions;

import pclabs3.layer.physical.SiteLocation;
import framework.instructions.creation.CreateInstruction;

public class CreateSiteLocationInstruction extends CreateInstruction {

	public CreateSiteLocationInstruction(long executionTime, int siteId) {
		super(executionTime, siteId);
	}

	protected CreateSiteLocationInstruction() {
	}

	@Override
	protected Object createObject() {
		return new SiteLocation();
	}

}
