package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationLink;
import framework.layer.deployment.communication.CommunicationPackage;
/**
 * A communicationpackage was lost on a communication link
 *
 */
public class CommunicationPackageDroppedOnCommunicationLinkEvent extends Event {

	private int commLinkId;
	private int packageId;

	public CommunicationPackageDroppedOnCommunicationLinkEvent(CommunicationLink commLink, CommunicationPackage commPack) {
		commLinkId = commLink.getId();
		packageId = commPack.getId();
	}

	protected CommunicationPackageDroppedOnCommunicationLinkEvent() {
	}

	public int getCommLinkId() {
		return commLinkId;
	}

	public int getPackageId() {
		return packageId;
	}
	
	@Override
	public String toString() {
		return "Communication package " + packageId + " was dropped while travelling over communication link " + commLinkId;
	}

}
