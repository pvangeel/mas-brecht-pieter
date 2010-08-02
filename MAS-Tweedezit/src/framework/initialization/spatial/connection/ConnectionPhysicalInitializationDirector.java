package framework.initialization.spatial.connection;

import framework.initialization.InitializationDirector;
import framework.initialization.TimePattern;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.connections.ConnectionElement.IllegalDeploymentException;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;

public abstract class ConnectionPhysicalInitializationDirector<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends InitializationDirector<PhysicalConnectionStructure<E, Ctr, Cnn>> {

	/**
	 * @pre	param spatialPattern must be a spatial pattern that returns positions of type connectionElementPosition.
	 */
	public ConnectionPhysicalInitializationDirector(TimePattern timePattern, ConnectionSpatialPattern<E,Ctr,Cnn> spatialPattern) {
		super(timePattern, spatialPattern);
	}

	@Override
	protected void createAndDeploy() {
		try {
			ConnectionElementPosition<E, Ctr, Cnn> position = (ConnectionElementPosition<E, Ctr, Cnn>) getSpatialPattern().getNextPosition(getPhysicalStructure());
			deployNewEntityAtPosition(position);
		} catch (IllegalDeploymentException e) {
		}
	}

	protected ConnectionElementPosition<E, Ctr, Cnn> getExtraConnectionElementPosition() {
		return (ConnectionElementPosition<E, Ctr, Cnn>) getSpatialPattern().getNextPosition(getPhysicalStructure());
	}

	protected abstract void deployNewEntityAtPosition(ConnectionElementPosition<E, Ctr, Cnn> position) throws IllegalDeploymentException;

}
