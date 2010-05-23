package framework.layer.deployment.communication;

import java.io.Serializable;

import framework.core.VirtualClock;
import framework.events.EventBroker;
import framework.events.deployment.CommunicationPackageCreatedEvent;
import framework.layer.deployment.devices.Device;
import framework.utils.IdGenerator;

/**
 * A communication package that travels from device to device over communication
 * links.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class CommunicationPackage implements Serializable {

	private final int id = IdGenerator.getIdGenerator().getNextId(CommunicationPackage.class);
	private final long creationTime = VirtualClock.getClock().getCurrentTime();
	private transient Device destination;

	public CommunicationPackage() {
		EventBroker.getEventBroker().notifyAll(new CommunicationPackageCreatedEvent(this));
	}

	public int getId() {
		return id;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public long getAge() {
		return VirtualClock.getClock().getCurrentTime() - getCreationTime();
	}

	public void setDestination(Device destination) {
		if (destination == null) {
			throw new IllegalArgumentException();
		}
		if (this.destination != null) {
			throw new IllegalStateException();
		}
		this.destination = destination;
	}

	public Device getDestination() {
		if (destination == null) {
			throw new IllegalStateException("no destination defined for this package");
		}
		return destination;
	}

	/**
	 * Executed on arrival of a package at it's destination device.
	 */
	public abstract void onArrival();

	/**
	 * Return the size of this communicationPackage in bytes
	 */
	public abstract double getByteSize();
}
