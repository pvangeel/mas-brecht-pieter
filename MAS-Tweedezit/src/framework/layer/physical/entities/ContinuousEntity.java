package framework.layer.physical.entities;

import framework.layer.physical.position.ContinuousPosition;
import framework.layer.physical.position.Position;

/**
 * Physical Entity that moves in a continuous world, without connectors or connections. 
 * 
 * @author Bart Tuts, Jelle Van Gompel
 *
 */
public abstract class ContinuousEntity<E extends ContinuousEntity<E>> extends MovingEntity<E> {

	private final ContinuousPosition continuousPosition;

	public ContinuousEntity(ContinuousPosition continuousPosition) {
		if (continuousPosition == null) {
			throw new IllegalArgumentException();
		}
		this.continuousPosition = continuousPosition;
	}

	@Override
	public Position getPosition() {
		return getContinuousPosition();
	}

	public ContinuousPosition getContinuousPosition() {
		return continuousPosition;
	}

}
