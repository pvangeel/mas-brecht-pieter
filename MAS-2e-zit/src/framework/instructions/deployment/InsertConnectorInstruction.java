package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionPosition;
import framework.layer.physical.position.Direction;

public class InsertConnectorInstruction<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends Instruction<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	private int connectorId;
	private int connectionId;
	private long distanceFromConnector1;
	private int piece1Id;
	private int piece2Id;

	public InsertConnectorInstruction(long executionTime, int connectorId, int connectionId, long distanceFromConnector1, int piece1Id,
			int piece2Id) {
		super(executionTime);
		this.connectorId = connectorId;
		this.connectionId = connectionId;
		this.distanceFromConnector1 = distanceFromConnector1;
		this.piece1Id = piece1Id;
		this.piece2Id = piece2Id;
	}

	protected InsertConnectorInstruction() {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Ctr c = (Ctr) getInstructionManager().findSpecificObject(Connector.class, connectorId);
		Cnn cn = (Cnn) getInstructionManager().findSpecificObject(Connection.class, connectionId);
		Cnn piece1 = cn.createNewPiece();
		Cnn piece2 = cn.createNewPiece();
		getInstructionManager().addCreatedObject(piece1Id, piece1);
		getInstructionManager().addCreatedObject(piece2Id, piece2);
		getInstructionManager().getPhysicalStructure().insertConnector(c,
				new ConnectionPosition<E, Ctr, Cnn>(cn, distanceFromConnector1, Direction.TO_CONNECTOR_2), piece1, piece2);
	}
}
