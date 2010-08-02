package framework.instructions.creation;

import java.util.ArrayList;
import java.util.List;

import framework.layer.deployment.communication.CommunicationCapability;
import framework.layer.deployment.communication.Router;

public class CreateCommunicationCapabilityInstruction extends CreateInstruction {

	private int[] routerIds;

	public CreateCommunicationCapabilityInstruction(long executionTime, int id, int... routerIds) {
		super(executionTime, id);
		this.routerIds = routerIds;
	}

	protected CreateCommunicationCapabilityInstruction() {
	}

	@Override
	protected Object createObject() {
		
		if(routerIds == null){
			return new CommunicationCapability();
		}
		else {
			List<Router> routers = new ArrayList<Router>();
			for (int routerId : routerIds) {
				Router router = getInstructionManager().findSpecificObject(Router.class, routerId);
				routers.add(router);
			}
			
			return new CommunicationCapability(routers);
		}
	}

}
