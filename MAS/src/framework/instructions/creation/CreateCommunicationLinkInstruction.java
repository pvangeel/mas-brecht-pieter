package framework.instructions.creation;

import framework.layer.deployment.communication.CommunicationLink;

public class CreateCommunicationLinkInstruction extends CreateInstruction {

	private long delayPerMillimeter;
	private long bandwidth;
	
	public CreateCommunicationLinkInstruction(long executionTime, int linkId, long delayPerMillimeter, long bandwidth) {
		super(executionTime, linkId);
		if (delayPerMillimeter < 0 || bandwidth < 0) {
			throw new IllegalArgumentException();
		}
		this.delayPerMillimeter = delayPerMillimeter;
		this.bandwidth = bandwidth;
	}
	
	protected CreateCommunicationLinkInstruction() {
	}
	
	@Override
	protected Object createObject() {
		return new CommunicationLink(delayPerMillimeter, bandwidth);
	}

}
