package framework.layer.deployment;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import framework.events.EventBroker;
import framework.events.deployment.CommunicationLinkAddedEvent;
import framework.events.deployment.CommunicationLinkRemovedEvent;
import framework.events.deployment.DeviceAttachedEvent;
import framework.layer.deployment.communication.CommunicationLink;
import framework.layer.deployment.communication.Router;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.entities.PhysicalEntity;

/**
 * Represents the network formed by the deployment devices
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class DeploymentStructure {

	private final DeploymentLayer deploymentLayer;

	private final Collection<Device> devices = new HashSet<Device>();
	private final Collection<CommunicationLink> datalinks = new HashSet<CommunicationLink>();

	public DeploymentStructure(DeploymentLayer deploymentLayer) {
		if (deploymentLayer == null) {
			throw new IllegalArgumentException();
		}
		this.deploymentLayer = deploymentLayer;
	}

	/**
	 * Attach the given device to the given physical entity
	 */
	public void attachDevice(Device device, PhysicalEntity<?> entity) {
		// device mag niet reeds aan andere physicalEntity zijn toegevoegd
		if (device == null || entity == null || device.hasPhysicalEntity()) {
			throw new IllegalArgumentException();
		}
		entity.attachDevice(device);
		device.setPhysicalEntity(entity);

		devices.add(device);
		device.setStructure(this);
		deploymentLayer.getRegistry().register(device);

		EventBroker.getEventBroker().notifyAll(new DeviceAttachedEvent(device, entity));
	}

	/**
	 * Add a communicationlink between two devices
	 */
	public void addCommunicationlink(CommunicationLink commlink, Router router1, Router router2) {
		if (commlink == null || router1 == null || router2 == null) {
			throw new IllegalArgumentException();
		}
		if (!hasDevice(router1.getDevice()) || !hasDevice(router2.getDevice())) {
			throw new IllegalStateException();
		}

		commlink.connect(router1, router2);
		datalinks.add(commlink);
		deploymentLayer.getRegistry().register(commlink);

		EventBroker.getEventBroker().notifyAll(new CommunicationLinkAddedEvent(commlink));
	}

	public boolean hasDevice(Device device) {
		return devices.contains(device);
	}

	public void removeCommunicationLink(CommunicationLink link) {
		if (link == null) {
			throw new IllegalArgumentException();
		}
		link.destroy();
		datalinks.remove(link);
		deploymentLayer.getRegistry().unregister(link);
		EventBroker.getEventBroker().notifyAll(new CommunicationLinkRemovedEvent(link));
	}

	public Collection<Device> getDevices() {
		return Collections.unmodifiableCollection(devices);
	}

	public Collection<CommunicationLink> getDatalinks() {
		return Collections.unmodifiableCollection(datalinks);
	}
}
