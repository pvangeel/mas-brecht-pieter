package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;

public class DeployConnectionInstruction<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Instruction<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private int connectionId;
	private int connector1Id;
	private int connector2Id;

	public DeployConnectionInstruction(long executionTime, int connectionId, int connector1Id, int connector2Id) {
		super(executionTime);
		this.connectionId = connectionId;
		this.connector1Id = connector1Id;
		this.connector2Id = connector2Id;
	}

	protected DeployConnectionInstruction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Cnn con = (Cnn) getInstructionManager().findSpecificObject(Connection.class, connectionId);
		Ctr c1 = (Ctr) getInstructionManager().findSpecificObject(Connector.class, connector1Id);
		Ctr c2 = (Ctr) getInstructionManager().findSpecificObject(Connector.class, connector2Id);
		getInstructionManager().getPhysicalStructure().addConnection(con, c1, c2);
	}

}
