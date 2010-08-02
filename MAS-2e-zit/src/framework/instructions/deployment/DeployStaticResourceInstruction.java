package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.entities.StaticResource;

public class DeployStaticResourceInstruction<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Instruction<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private int connectorId;
	private int resourceId;

	public DeployStaticResourceInstruction(long executionTime, int resourceId, int connectorId) {
		super(executionTime);
		this.connectorId = connectorId;
		this.resourceId = resourceId;
	}

	protected DeployStaticResourceInstruction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Connector<E, Ctr, Cnn> c = getInstructionManager().findSpecificObject(Connector.class, connectorId);
		StaticResource<Ctr> r = getInstructionManager().findSpecificObject(StaticResource.class, resourceId);
		getInstructionManager().getPhysicalStructure().deployResource(r,c);
	}

}
