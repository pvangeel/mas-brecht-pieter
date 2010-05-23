package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.connections.ConnectionElement.IllegalDeploymentException;
import framework.layer.physical.entities.ConnectionEntity;

public class DeployConnectionEntityInstruction<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Instruction<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private int entityId;
	private long posX, posY;
	private boolean onroad;

	public DeployConnectionEntityInstruction(long executionTime, int entityId, long posX, long posY, boolean onroad) {
		super(executionTime);
		this.entityId = entityId;
		this.posX = posX;
		this.posY = posY;
		this.onroad = onroad;
	}

	protected DeployConnectionEntityInstruction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		try {
			E entity = (E) getInstructionManager().findSpecificObject(ConnectionEntity.class, entityId);
			getInstructionManager().getPhysicalStructure().deployOnConnector(entity, posX, posY, onroad);
		} catch (IllegalDeploymentException e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}
}
