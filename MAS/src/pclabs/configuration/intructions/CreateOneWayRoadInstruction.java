package pclabs.configuration.intructions;

import pclabs.physicallayer.entities.OneWayRoad;
import framework.instructions.creation.CreateInstruction;

public class CreateOneWayRoadInstruction extends CreateInstruction {

	public CreateOneWayRoadInstruction(long executionTime, int oneWayRoadId) {
		super(executionTime, oneWayRoadId);
	}

	protected CreateOneWayRoadInstruction() {
	}
	
	@Override
	protected Object createObject() {
		return new OneWayRoad();
	}

}
