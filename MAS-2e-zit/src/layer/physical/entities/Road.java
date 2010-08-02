package layer.physical.entities;

import framework.layer.physical.connections.Connection;
import framework.layer.physical.position.ConnectionPosition;

public abstract class Road extends Connection<Truck, Crossroads, Road> {

	@Override
	protected void executeSpecificDeploymentOptions(Truck connectionEntity, ConnectionPosition<Truck, Crossroads, Road> pos) {
		
	}

}
