package configuration.intructions;

import layer.agent.entities.DelegateMASDeliveryAgent;
import framework.instructions.creation.CreateInstruction;

public class CreateDelegateMASDeliveryAgent extends CreateInstruction {

	private int objectId;

	public CreateDelegateMASDeliveryAgent(long currentTime, int objectId) {
		super(currentTime, objectId);
		this.objectId = objectId;
	}
	
	@Override
	protected Object createObject() {
		return new DelegateMASDeliveryAgent();
	}

}
