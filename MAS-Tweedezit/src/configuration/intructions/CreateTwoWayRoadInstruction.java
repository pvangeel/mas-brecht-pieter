package configuration.intructions;

import layer.physical.entities.TwoWayRoad;
import framework.instructions.creation.CreateInstruction;

public class CreateTwoWayRoadInstruction extends CreateInstruction {

	public CreateTwoWayRoadInstruction(long executionTime, int twoWayRoadId) {
		super(executionTime, twoWayRoadId);
	}

	protected CreateTwoWayRoadInstruction() {
	}
	
	@Override
	protected Object createObject() {
		return new TwoWayRoad();
	}

}
