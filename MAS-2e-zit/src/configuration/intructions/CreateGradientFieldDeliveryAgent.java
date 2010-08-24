package configuration.intructions;

import layer.agent.entities.GradientFieldDeliveryAgent;
import framework.instructions.creation.CreateInstruction;

public class CreateGradientFieldDeliveryAgent extends CreateInstruction {

	private int objectId;

	public CreateGradientFieldDeliveryAgent(long currentTime, int objectId) {
		super(currentTime, objectId);
		this.objectId = objectId;
	}
	
	@Override
	protected Object createObject() {
		return new GradientFieldDeliveryAgent();
	}

}
