package pclabs.configuration.intructions;

import pclabs.agentlayer.entities.SimpleAgent;
import framework.instructions.creation.CreateInstruction;

public class CreateSimpleAgent extends CreateInstruction {

	private int objectId;

	public CreateSimpleAgent(long currentTime, int objectId) {
		super(currentTime, objectId);
		this.objectId = objectId;
	}
	
	@Override
	protected Object createObject() {
		return new SimpleAgent();
	}

}
