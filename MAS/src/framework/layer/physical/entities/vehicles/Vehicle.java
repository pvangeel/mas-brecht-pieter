package framework.layer.physical.entities.vehicles;

import framework.events.EventBroker;
import framework.events.physical.PositionOnConnectionUpdatedEvent;
import framework.layer.physical.connections.Connection;
import framework.layer.physical.connections.Connector;
import framework.layer.physical.entities.ConnectionEntity;

/**
 * A vehicle represents any physical entity that can move over a
 * connectionElement. Examples are cars, boats (on canals, not on sea), trains
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Vehicle<E extends Vehicle<E, Ctr, Cnn>, 
							Ctr extends Connector<E, Ctr, Cnn>, 
							Cnn extends Connection<E, Ctr, Cnn>>
		extends ConnectionEntity<E, Ctr, Cnn> {

	// millimeter/microseconds
	private double speed;

	/**
	 * @param speed
	 *            speed in millimeter/microseconds
	 */
	public final void setSpeed(double speed) {
		if (speed < 0) {
			throw new IllegalArgumentException();
		}
		this.speed = speed;
	}

	public final double getSpeed() {
		return speed;
	}

	/**
	 * Verplaatst de Vehicle op de Connection. Er wordt niet gekeken of deze
	 * verplaatsing wel mogelijk is!
	 * 
	 * FIXME CARE WITH THIS METHOD.. The agent should NEVER call it directly. this method should be called just by COMMANDS
	 */
	public void moveOnConnection(long smallestDistance, long wannaDriveDistance) throws MoveUncompletedException {
		getConnectionPosition().setDistance(getConnectionPosition().getDistance() + smallestDistance);
		EventBroker.getEventBroker().notifyAll(new PositionOnConnectionUpdatedEvent(this));
		consumeTime(getTimeConsumed(smallestDistance));
		if (smallestDistance < wannaDriveDistance) {
			throw new MoveUncompletedException();
		}
	}

	public final long getTimeConsumed(long distance) {
		return (long) Math.round(distance / getSpeed());
	}
}
