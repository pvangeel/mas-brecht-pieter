package framework.layer.deployment.communication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import framework.events.EventBroker;
import framework.events.deployment.CommunicationCapabilityCreatedEvent;
import framework.events.deployment.CommunicationPackageDroppedOnCommunicationLinkEvent;
import framework.events.deployment.CommunicationPackageDroppedOnDeviceEvent;
import framework.events.deployment.CommunicationPackageReceivedEvent;
import framework.events.deployment.PackageSentEvent;
import framework.layer.deployment.communication.Router.NoNextCommunicationlinkException;
import framework.layer.deployment.devices.Capability;
import framework.layer.deployment.devices.Device;
import framework.layer.deployment.devices.InactiveCapabilityException;

/**
 * 
 * The whole of communicative capability that a device has is brought together
 * here. This means every device has exactly one Communication Capability. A
 * communication capability consists of a number of Routers and an inbox for
 * received communication packages.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class CommunicationCapability extends Capability {

	private final List<CommunicationPackage> inbox = new ArrayList<CommunicationPackage>();

	// 1 microsec
	private final long inboxMessageAge = 1L;

	private Device device;

	// list of routers, first router is more prior
	private final List<Router> routers = new ArrayList<Router>();

	public CommunicationCapability(List<Router> routers) {
		if (routers == null) {
			throw new IllegalArgumentException();
		}
		for (Router router : routers) {
			addRouter(router);
		}
		EventBroker.getEventBroker().notifyAll(
				new CommunicationCapabilityCreatedEvent(this));
	}

	//FIXME MARIO, added constructor to be able to create communication capability without routers..
	public CommunicationCapability() {
		EventBroker.getEventBroker().notifyAll(
				new CommunicationCapabilityCreatedEvent(this));
	}

	
	public void setDevice(Device device) {
		if (device == null) {
			throw new IllegalArgumentException();
		}
		if (this.device != null) {
			throw new IllegalStateException();
		}
		this.device = device;
	}

	public Device getDevice() {
		if (device == null) {
			throw new IllegalStateException();
		}
		return device;
	}

	public void addRouter(Router router) {
		if (router == null) {
			throw new IllegalArgumentException();
		}
		routers.add(router);
		router.setCommunicationCapability(this);
	}

	public List<Router> getRouters() {
		return routers;
	}

	private Router getOutgoingRouter(CommunicationPackage communicationPackage)
			throws NoNextCommunicationlinkException {
		for (Router router : getRouters()) {
			CommunicationLink link = router
					.getNextCommunicationLink(communicationPackage);
			if (link != null) {
				return router;
			}
		}
		throw new NoNextCommunicationlinkException();
	}

	public void sendCommunicationPackage(
			CommunicationPackage communicationPackage)
			throws InactiveCapabilityException {
		if (!isActivated()) {
			EventBroker.getEventBroker().notifyAll(
					new CommunicationPackageDroppedOnDeviceEvent(getDevice(),
							communicationPackage));
			throw new InactiveCapabilityException();
		}
		if (communicationPackage.getDestination().equals(getDevice())) {
			EventBroker.getEventBroker().notifyAll(
					new CommunicationPackageReceivedEvent(getDevice(),
							communicationPackage));
			communicationPackage.onArrival();
		} else {
			try {
				sendToNextDevice(communicationPackage);
			} catch (NoNextCommunicationlinkException e) {
				EventBroker.getEventBroker().notifyAll(
						new CommunicationPackageDroppedOnDeviceEvent(
								getDevice(), communicationPackage));
				System.err.println("WARNING: package " + communicationPackage
						+ " has been dropped. No next link");
			} catch (CommunicationLinkDownException e) {
				// No need to issue an event here. this is already done in
				// sendCommunicationPackage
				System.err.println("WARNING: package " + communicationPackage
						+ " has been dropped. Next link is down");
			}
		}
	}

	private void sendToNextDevice(CommunicationPackage communicationPackage)
			throws CommunicationLinkDownException,
			NoNextCommunicationlinkException, InactiveCapabilityException {
		Router outgoingRouter = getOutgoingRouter(communicationPackage);
		CommunicationLink nextLink = outgoingRouter
				.getNextCommunicationLink(communicationPackage);
		if (!nextLink.isActive()) {
			EventBroker.getEventBroker().notifyAll(
					new CommunicationPackageDroppedOnCommunicationLinkEvent(
							nextLink, communicationPackage));
			throw new CommunicationLinkDownException();
		}
		nextLink.send(communicationPackage, outgoingRouter);
		EventBroker.getEventBroker().notifyAll(
				new PackageSentEvent(getDevice(), communicationPackage));
	}

	public void addInboxCommunicationPackage(
			CommunicationPackage communicationPackage)
			throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		if (communicationPackage == null) {
			throw new IllegalArgumentException();
		}
		inbox.add(communicationPackage);
	}

	public List<CommunicationPackage> getInbox() {
		return Collections.unmodifiableList(inbox);
	}

	public void clearInbox() {
		inbox.clear();
	}

	public static class CommunicationLinkDownException extends Exception {
	}

	public void clearOldMessages() {
		for (CommunicationPackage communicationPackage : new ArrayList<CommunicationPackage>(
				inbox)) {
			if (communicationPackage.getAge() > inboxMessageAge) {
				inbox.remove(communicationPackage);
			}
		}
	}

	public void updateRouting(long timePassed) {
		for (Router router : getRouters()) {
			router.updateRouting(timePassed);
		}
	}

	public Collection<Router> getRoutersAtOtherEnd() {
		Collection<Router> routersAtOtherEnd = new HashSet<Router>();
		for (Router router : getRouters()) {
			for (CommunicationLink communicationLink : router
					.getCommunicationLinks()) {
				routersAtOtherEnd.add(communicationLink.getOtherEnd(router));
			}
		}
		return routersAtOtherEnd;
	}

	@Override
	protected void onActiveChanged(boolean activated) {
		if (!activated) {
			for (Router r : routers) {
				r.removeAllCommunicationLinks();
			}
		}
	}
}
