package pclabs3.layer.physical;

import framework.layer.physical.connections.Connection;
import framework.layer.physical.position.ConnectionPosition;

public abstract class NullConnection extends Connection<NullConnectionEntity, SiteLocation, NullConnection> {

	@Override
	protected void executeSpecificDeploymentOptions(NullConnectionEntity connectionEntity, ConnectionPosition<NullConnectionEntity, SiteLocation, NullConnection> pos) {
		
	}

}
