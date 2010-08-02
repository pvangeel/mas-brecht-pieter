package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationPackage;

public class CommunicationPackageCreatedEvent extends Event {

	private int communicationId;
	private long creationTime;
	private double byteSize;

	public CommunicationPackageCreatedEvent(CommunicationPackage communicationPackage) {
		if (communicationPackage == null) {
			throw new IllegalArgumentException();
		}
		this.communicationId = communicationPackage.getId();
		this.creationTime = communicationPackage.getCreationTime();
		this.byteSize = communicationPackage.getByteSize();
	}

	protected CommunicationPackageCreatedEvent() {
	}

	public int getCommunicationId() {
		return communicationId;
	}

	public long getCreationTime() {
		return creationTime;
	}

	public double getByteSize() {
		return byteSize;
	}
}
