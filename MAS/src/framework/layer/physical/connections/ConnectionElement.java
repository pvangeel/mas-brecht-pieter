package framework.layer.physical.connections;

import framework.layer.TickListener;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;

/**
 * Abstract super class of elements used in a graph network.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */

public abstract class ConnectionElement<E extends ConnectionEntity<E,Ctr,Cnn>, 
										Ctr extends Connector<E, Ctr, Cnn>, 
										Cnn extends Connection<E, Ctr, Cnn>, 
										P extends PhysicalConnectionStructure<E, Ctr, Cnn>>
		{

	private boolean blocked;

	public abstract void deploy(E connectionEntity, ConnectionElementPosition<E,Ctr,Cnn> position) throws IllegalDeploymentException;

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public static class IllegalDeploymentException extends Exception {
	}
}
