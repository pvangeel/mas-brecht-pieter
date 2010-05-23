package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationLink;
/**
 * 
 * Indicates a communicationLink was removed
 *
 */
public class CommunicationLinkRemovedEvent extends Event {

	private int communicationLinkId;
	private int router1Id;
	private int router2Id;
	private int device1Id;
	private int device2Id;

	protected CommunicationLinkRemovedEvent() {
	}

	public CommunicationLinkRemovedEvent(CommunicationLink commlink) {
		this.communicationLinkId = commlink.getId();
		this.router1Id = commlink.getRouter1().getId();
		this.router2Id = commlink.getRouter2().getId();
		this.device1Id = commlink.getRouter1().getDevice().getId();
		this.device2Id = commlink.getRouter2().getDevice().getId();
	}

	public int getCommLinkId() {
		return communicationLinkId;
	}

	public int getDevice1Id() {
		return device1Id;
	}

	public int getDevice2Id() {
		return device2Id;
	}

	public int getRouter1Id() {
		return router1Id;
	}

	public int getRouter2Id() {
		return router2Id;
	}

	@Override
	public String toString() {
		return "Communication link " + communicationLinkId + " was removed between router " + router1Id + " at " + device1Id
				+ " and router " + router2Id + " at " + device2Id;
	}

}
