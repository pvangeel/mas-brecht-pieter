package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ContinuousPosition;

public class DeployConnectorInstruction<E extends ConnectionEntity<E, Ctr, Cnn>, 
										Ctr extends Connector<E, Ctr, Cnn>, 
										Cnn extends Connection<E, Ctr, Cnn>>
		extends Instruction<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private int connectorId;
	private long positionX;
	private long positionY;

	public DeployConnectorInstruction(long executionTime, int connectorId, long positionX, long positionY) {
		super(executionTime);
		this.connectorId = connectorId;
		this.positionX = positionX;
		this.positionY = positionY;
	}

	protected DeployConnectorInstruction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Ctr c = (Ctr) getInstructionManager().findSpecificObject(Connector.class, connectorId);
		getInstructionManager().getPhysicalStructure().addConnector(c, new ContinuousPosition(positionX, positionY));
	}

}
