package framework.layer.deployment.communication;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import framework.layer.deployment.devices.Device;
import framework.utils.IdGenerator;
import framework.utils.Utils;

/**
 * 
 * A Router creates and maintains a number of communication links with routers
 * on other devices. Various Routers of the same or different type can be
 * present on a single communication capability of a device
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 * 
 */

public abstract class Router {

	private final int id = IdGenerator.getIdGenerator().getNextId(Router.class);
	private CommunicationCapability communicationCapability;

	// storage van de datalinks en mapping naar het device dat aan de andere
	// kant van de link is te vinden.
	private final Map<Router, CommunicationLink> communicationLinks = new HashMap<Router, CommunicationLink>();

	private final long routingUpdateInterval;
	private long timeSinceLastUpdate;

	public Router(long routingUpdateInterval) {
		if (routingUpdateInterval < 0) {
			throw new IllegalArgumentException();
		}
		this.routingUpdateInterval = routingUpdateInterval;
		// initialize to interval so we update routing during first tick
		this.timeSinceLastUpdate = routingUpdateInterval;
	}

	public Router() {
		this(Utils.secondsToMicroSeconds(3600));
	}

	public int getId() {
		return id;
	}

	void setCommunicationCapability(
			CommunicationCapability communicationCapability) {
		if (communicationCapability == null) {
			throw new IllegalArgumentException();
		}
		this.communicationCapability = communicationCapability;
	}

	protected CommunicationCapability getCommunicationCapability() {
		if (communicationCapability == null) {
			throw new IllegalStateException();
		}
		return communicationCapability;
	}

	public void addCommlink(CommunicationLink datalink) {
		if (datalink == null) {
			throw new IllegalArgumentException();
		}
		if (!datalink.hasRouter(this)) {
			throw new IllegalStateException();
		}
		communicationLinks.put(datalink.getOtherEnd(this), datalink);
	}

	public Collection<CommunicationLink> getCommunicationLinks() {
		return Collections.unmodifiableCollection(communicationLinks.values());
	}

	public boolean hasCommunicationLinkTo(Router router) {
		if (router == null) {
			throw new IllegalArgumentException();
		}
		return communicationLinks.containsKey(router);
	}

	public CommunicationLink getCommunicationLinkTo(Router router) {
		if (router == null) {
			throw new IllegalArgumentException();
		}
		return communicationLinks.get(router);
	}

	/**
	 * Method can only be used by CommunicationLink.destroy()
	 */
	public void removeCommunicationLink(CommunicationLink datalink) {
		if (datalink == null) {
			throw new IllegalArgumentException();
		}
		if (!datalink.hasRouter(this)) {
			throw new IllegalStateException();
		}
		communicationLinks.remove(datalink.getOtherEnd(this));
	}

	public Device getDevice() {
		return getCommunicationCapability().getDevice();
	}

	public CommunicationLink getNextCommunicationLink(
			CommunicationPackage communicationPackage) {
		for (Router router : communicationPackage.getDestination()
				.getCommunicationCapability().getRouters()) {
			if (hasCommunicationLinkTo(router)) {
				return getCommunicationLinkTo(router);
			}
		}
		return null;
	}

	/**
	 * remove dead datalinks and add newly created datalinks and update the
	 * routing tables
	 */
	public void updateRouting(long timePassed) {
		if (timeSinceLastUpdate >= routingUpdateInterval) {
			protectedUpdateRouting();
			timeSinceLastUpdate = timeSinceLastUpdate - routingUpdateInterval;
			return;
		}
		timeSinceLastUpdate += timePassed;
	}

	protected abstract void protectedUpdateRouting();

	public static class NoNextCommunicationlinkException extends Exception {
	}

	public void removeAllCommunicationLinks() {
		for (CommunicationLink link : new HashSet<CommunicationLink>(
				getCommunicationLinks())) {
			link.destroy();
		}
	}
}
