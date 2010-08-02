package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.deployment.communication.CommunicationLink;
import framework.layer.deployment.communication.Router;
import framework.layer.physical.PhysicalStructure;

public class DeployCommunicationLinkInstruction extends Instruction<PhysicalStructure<?>> {

	private int linkId;
	private int router1Id;
	private int router2Id;

	public DeployCommunicationLinkInstruction(long executionTime, int linkId, int router1Id, int router2Id) {
		super(executionTime);
		if (linkId < 0 || router1Id < 0 || router2Id < 0) {
			throw new IllegalArgumentException();
		}
		this.linkId = linkId;
		this.router1Id = router1Id;
		this.router2Id = router2Id;
	}

	protected DeployCommunicationLinkInstruction() {
	}

	@Override
	public void execute() {
		CommunicationLink link = getInstructionManager().findSpecificObject(CommunicationLink.class, linkId);
		Router router1 = getInstructionManager().findSpecificObject(Router.class, router1Id);
		Router router2 = getInstructionManager().findSpecificObject(Router.class, router2Id);
		getInstructionManager().getDeploymentStructure().addCommunicationlink(link, router1, router2);
	}

}
