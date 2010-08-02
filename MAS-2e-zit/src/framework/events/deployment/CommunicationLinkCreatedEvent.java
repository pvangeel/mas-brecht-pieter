package framework.events.deployment;

import framework.events.Event;
import framework.layer.deployment.communication.CommunicationLink;
/**
 * Indicates a new communication link was created.
 *
 */
public class CommunicationLinkCreatedEvent extends Event {

	private int commLinkId;
	private double bandwidth;
	private long delayPerMilliMeter;

	public CommunicationLinkCreatedEvent(CommunicationLink commlink) {
		this.commLinkId = commlink.getId();
		this.bandwidth = commlink.getBandwidth();
		this.delayPerMilliMeter = commlink.getDelayPerMillimeter();
	}

	protected CommunicationLinkCreatedEvent() {
	}

	public int getCommLinkId() {
		return commLinkId;
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public long getDelayPerMilliMeter() {
		return delayPerMilliMeter;
	}
	
	@Override
	public String toString() {
		return "Communication link " + commLinkId + " was created, with a bandwidth of " + bandwidth + " and a delay per milimeter of " + delayPerMilliMeter;
	}

}
