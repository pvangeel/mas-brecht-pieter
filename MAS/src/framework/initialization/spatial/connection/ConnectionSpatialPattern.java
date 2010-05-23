package framework.initialization.spatial.connection;

import framework.initialization.spatial.SpatialPattern;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;
import framework.layer.physical.position.ConnectionElementPosition;

public abstract class ConnectionSpatialPattern<E extends ConnectionEntity<E, Ctr, Cnn>, Ctr extends Connector<E, Ctr, Cnn>, Cnn extends Connection<E, Ctr, Cnn>>
		extends SpatialPattern<PhysicalConnectionStructure<E, Ctr, Cnn>, ConnectionElementPosition<E, Ctr, Cnn>> {

}
