package framework.layer.physical.entities;

import framework.layer.physical.connections.Connector;
import framework.layer.physical.position.Position;

/**
 *
 * A Resource deployed on a connector. It cannot be moved.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class StaticResource<Ctr extends Connector<?, Ctr, ?>> extends Resource<StaticResource<Ctr>> {

	private Ctr connector;

	public void setConnector(Ctr connector) {
		if (connector == null) {
			throw new IllegalArgumentException();
		}
		if (this.connector != null) {
			throw new IllegalStateException();
		}
		this.connector = connector;
	}

	public Ctr getConnector() {
		if (connector == null) {
			throw new IllegalStateException();
		}
		return connector;
	}

	@Override
	public Position getPosition() {
		return getConnector().getPosition();
	}

	
}
