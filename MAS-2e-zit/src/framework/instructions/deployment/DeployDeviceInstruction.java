package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.deployment.DeploymentStructure;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.PhysicalStructure;
import framework.layer.physical.entities.PhysicalEntity;

public class DeployDeviceInstruction extends Instruction<PhysicalStructure<?>> {

	private int deviceId, entityId;

	public DeployDeviceInstruction(long executionTime, int deviceId, int entityId) {
		super(executionTime);
		this.deviceId = deviceId;
		this.entityId = entityId;
	}

	protected DeployDeviceInstruction() {
	}
	
	@Override
	public void execute() {
		Device device = getInstructionManager().findSpecificObject(Device.class, deviceId);
		PhysicalEntity<?> entity = getInstructionManager().findSpecificObject(PhysicalEntity.class, entityId);
		DeploymentStructure structure = getInstructionManager().getDeploymentStructure();
		structure.attachDevice(device, entity);
	}

}
