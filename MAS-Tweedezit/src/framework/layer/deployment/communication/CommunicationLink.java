package framework.layer.deployment.communication;

import java.util.LinkedList;

import framework.core.VirtualClock;
import framework.events.EventBroker;
import framework.events.deployment.CommunicationLinkCreatedEvent;
import framework.events.deployment.CommunicationPackageDroppedOnCommunicationLinkEvent;
import framework.events.deployment.CommunicationPackageDroppedOnDeviceEvent;
import framework.layer.TickListener;
import framework.layer.deployment.devices.InactiveCapabilityException;
import framework.utils.IdGenerator;

/**
 * Two way full duplex link between two devices, over which communication
 * packages are sent. It has a bandwidth and delay per millimeter, which can be
 * set. When a device sends a message is it immediately added to the
 * communication link's queue. There are no queues on devices themselves.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class CommunicationLink implements TickListener {

	private final int id = IdGenerator.getIdGenerator().getNextId(CommunicationLink.class);

	public int getId() {
		return id;
	}

	private final LinkedList<DatalinkObject> queue = new LinkedList<DatalinkObject>();

	private Router router1;
	private Router router2;

	// bytes per microseconds
	private double bandwidth;
	// delay in microseconden
	private long delayPerMillimeter;

	private boolean active;

	/**
	 * 
	 * @param delayPerMeter
	 *            delay in microseconds per millimeter
	 * @param bandwidth
	 *            bandwidth in bits per second
	 */
	public CommunicationLink(long delayPerMeter, long bandwidth) {
		if (delayPerMeter < 0 || bandwidth < 0) {
			throw new IllegalArgumentException();
		}
		setDelayPerMillimeter(delayPerMeter);
		setBandwidth(bandwidth);
		setActive(true);

		EventBroker.getEventBroker().notifyAll(new CommunicationLinkCreatedEvent(this));
	}

	public void setActive(boolean active) {
		this.active = active;
		if (!active) {
			queue.clear();
		}
	}

	public boolean isActive() {
		return active;
	}

	/**
	 * Set a new bandwidth
	 * 
	 * All future sent packages will use the new bandwidth. packages that are
	 * already queued or underway will continue to use the old bandwidth
	 * 
	 * @param bandwidth
	 *            bandwidth in MegaBytes per second
	 * 
	 */
	public void setBandwidth(long bandwidth) {
		if (bandwidth < 0) {
			throw new IllegalArgumentException();
		}
		this.bandwidth = bandwidth / 1000000d / 8d;
	}

	private long getLength() {
		return getRouter1().getDevice().getPhysicalEntity().getPosition().getDistanceTo(
				getRouter2().getDevice().getPhysicalEntity().getPosition());
	}

	private long getDelay() {
		return (long) Math.round(delayPerMillimeter * getLength());
	}

	/**
	 * Sets a new delayPerMeter.
	 * 
	 * All future sent packages will use the new delayPerMeter. packages that
	 * are already queued or underway will continue to use the old delayPerMeter
	 * 
	 * @param delayPerMeter
	 *            delay in microseconds per meter
	 */
	public void setDelayPerMillimeter(long delayPerMeter) {
		if (delayPerMeter < 0) {
			throw new IllegalArgumentException();
		}
		this.delayPerMillimeter = delayPerMeter;
	}

	void send(CommunicationPackage communicationPackage, Router fromRouter) {
		if (!isActive()) {
			throw new IllegalStateException("communicationLink not active");
		}
		if (communicationPackage == null || fromRouter == null) {
			throw new IllegalArgumentException();
		}
		queue.add(new DatalinkObject(communicationPackage, getOtherEnd(fromRouter), getArrivalTime(communicationPackage)));
	}

	public Router getOtherEnd(Router fromRouter) {
		if (getRouter1().equals(fromRouter)) {
			return getRouter2();
		} else if (getRouter2().equals(fromRouter)) {
			return getRouter1();
		} else {
			throw new IllegalStateException();
		}
	}

	private long getArrivalTime(CommunicationPackage communicationPackage) {
		long now = VirtualClock.getClock().getCurrentTime();
		long transferTime = Math.round(communicationPackage.getByteSize() / bandwidth);
		return now + transferTime + getDelay();
	}

	private void flush() {
		while (canSend()) {
			DatalinkObject datalinkObject = popDatalinkObject();
			try {
				datalinkObject.getNextRouter().getDevice().getCommunicationCapability().sendCommunicationPackage(datalinkObject.getCommunicationPackage());
			} catch (InactiveCapabilityException e) {
				EventBroker.getEventBroker().notifyAll(
						new CommunicationPackageDroppedOnDeviceEvent(datalinkObject.getNextRouter().getDevice(), datalinkObject
								.getCommunicationPackage()));
				System.err.println("WARNING: package " + datalinkObject.getCommunicationPackage()
						+ " has been dropped. Communication capability at next device disabled.");
			}
		}
	}

	private DatalinkObject popDatalinkObject() {
		DatalinkObject datalinkObject = queue.get(0);
		queue.remove(0);
		return datalinkObject;
	}

	private boolean canSend() {
		if (queue.isEmpty()) {
			return false;
		} else {
			return queue.get(0).getArrivalTime() <= VirtualClock.getClock().getCurrentTime();
		}
	}

	/**
	 * return if this communication link is connected to the given device on
	 * either end
	 */
	public boolean hasRouter(Router router) {
		return getRouter1().equals(router) || getRouter2().equals(router);
	}

	public void destroy() {
//		System.out.println("CommunicationLink.destroy() link " + getId() + " between router " + getRouter1().getId() + " and router "
//				+ getRouter2().getId());
		getRouter1().removeCommunicationLink(this);
		getRouter2().removeCommunicationLink(this);
		for (DatalinkObject object : queue) {
			EventBroker.getEventBroker().notifyAll(new CommunicationPackageDroppedOnCommunicationLinkEvent(this, object.communicationPackage));
			System.err.println("WARNING: package " + object.getCommunicationPackage()
					+ " has been dropped. Communication link has been destroyed.");
		}
		queue.clear();
	}

	private class DatalinkObject {
		private final CommunicationPackage communicationPackage;
		private final Router nextRouter;
		private final long arrivalTime;

		public DatalinkObject(CommunicationPackage communicationPackage, Router nextRouter, long arrivalTime) {
			this.communicationPackage = communicationPackage;
			this.nextRouter = nextRouter;
			this.arrivalTime = arrivalTime;
		}

		public long getArrivalTime() {
			return arrivalTime;
		}

		public CommunicationPackage getCommunicationPackage() {
			return communicationPackage;
		}

		public Router getNextRouter() {
			return nextRouter;
		}
	}

	public void processTick(long timePassed) {
		if (isActive()) {
			flush();
		}
	}

	public Router getRouter1() {
		if (router1 == null) {
			throw new IllegalStateException();
		}
		return router1;
	}

	public Router getRouter2() {
		if (router2 == null) {
			throw new IllegalStateException();
		}
		return router2;
	}

	public void connect(Router router1, Router router2) {
		if (this.router1 != null || this.router2 != null) {
			throw new IllegalStateException("devices of a communication link can only be set once");
		}
		this.router1 = router1;
		this.router2 = router2;
//		System.out.println("CommunicationLink.connect() link " + getId() + " between router " + getRouter1().getId() + " and router "
//				+ getRouter2().getId());
		router1.addCommlink(this);
		router2.addCommlink(this);
	}

	public double getBandwidth() {
		return bandwidth;
	}

	public long getDelayPerMillimeter() {
		return delayPerMillimeter;
	}

	@Override
	public String toString() {
		return "CommunicationLink from " + router1 + " to " + router2;
	}
}
