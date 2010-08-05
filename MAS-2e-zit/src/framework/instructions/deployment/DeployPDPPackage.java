package framework.instructions.deployment;

import layer.physical.entities.Crossroads;
import layer.physical.entities.PDPPackage;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.instructions.Instruction;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.PhysicalStructure;
import framework.layer.physical.entities.PhysicalEntity;
import framework.layer.physical.entities.Resource;

public class DeployPDPPackage extends Instruction<PhysicalStructure<?>> {

	private final long executionTime2;
	private final int packageId;

	public DeployPDPPackage(long executionTime, int packageId) {
		executionTime2 = executionTime;
		this.packageId = packageId;
		
	}
	
	@Override
	public void execute() {
		// TODO Auto-generated method stub
		PhysicalEntity PDPPackage = getInstructionManager().findSpecificObject(PhysicalEntity.class, packageId);
		getInstructionManager().getAgentStructure().getAgentLayer().getRegistry().register(PDPPackage);
	}

}
