package framework.layer.deployment.devices;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import framework.events.EventBroker;
import framework.events.deployment.DeviceCreatedEvent;
import framework.layer.TickListener;
import framework.layer.agent.Agent;
import framework.layer.deployment.DeploymentStructure;
import framework.layer.deployment.communication.CommunicationCapability;
import framework.layer.deployment.communication.CommunicationLink;
import framework.layer.deployment.communication.Router;
import framework.layer.deployment.storage.StorageCapability;
import framework.layer.physical.entities.PhysicalEntity;
import framework.utils.IdGenerator;

/**
 * A physical device on which an agent can run. Devices are not physical
 * entities since they are not capable of independent existence. They are
 * however always attached to a physical entity. entity.
 * 
 * It can send messages. It has an inbox for incoming communicationpackages,
 * mobile code can be executed on it and it has a data storageCapability
 * facility. Note that the inbox is unlimited in capacity and tied to the
 * communicationCapability, not the storageCapability
 * 
 * When a device sends a message is it immediately added to the
 * communicationCapability link's queue. There are no queues on devices
 * themselves.
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class Device implements TickListener {

	private final int id = IdGenerator.getIdGenerator().getNextId(Device.class);

	private final StorageCapability storageCapability;

	private final CommunicationCapability communicationCapability;

	private PhysicalEntity<?> physicalEntity;

	private final Set<Agent> agents = new HashSet<Agent>();

	private DeploymentStructure structure;

	public Device(CommunicationCapability communicationCapability, StorageCapability storageCapability) {
		this.storageCapability = storageCapability;
		this.communicationCapability = communicationCapability;
		if (communicationCapability != null) {
			communicationCapability.setDevice(this);
		}
		EventBroker.getEventBroker().notifyAll(new DeviceCreatedEvent(this));
	}

	public void setStructure(DeploymentStructure structure) {
		if (structure == null) {
			throw new IllegalArgumentException();
		}
		if (!structure.hasDevice(this)) {
			throw new IllegalStateException();
		}
		this.structure = structure;
	}

	public DeploymentStructure getStructure() {
		if (structure == null) {
			throw new IllegalStateException();
		}
		return structure;
	}

	public PhysicalEntity<?> getPhysicalEntity() {
		if (physicalEntity == null) {
			throw new IllegalStateException();
		}
		return physicalEntity;
	}

	public boolean hasPhysicalEntity() {
		return physicalEntity != null;
	}

	public void setPhysicalEntity(PhysicalEntity<?> physicalEntity) {
		if (this.physicalEntity != null) {
			throw new IllegalStateException("Device already attached to other entity");
		}
		this.physicalEntity = physicalEntity;
	}

	public int getId() {
		return id;
	}

	public boolean hasStorageCapability() {
		return storageCapability != null;
	}

	public StorageCapability getStorageCapability() {
		if (!hasStorageCapability()) {
			throw new IllegalStateException();
		}
		return storageCapability;
	}

	public boolean isStorageCapabilityActive() {
		return getStorageCapability().isActivated();
	}

	public void setStorageCapabilityActive(boolean active) {
		getStorageCapability().setActivated(active);
		
	}

	public boolean hasCommunicationCapability() {
		return communicationCapability != null;
	}

	public CommunicationCapability getCommunicationCapability() {
		if (!hasCommunicationCapability()) {
			throw new IllegalStateException("no communication capability configured at device " + getId());
		}
		return communicationCapability;
	}

	public boolean isCommunicationCapabilityActive() {
		return getCommunicationCapability().isActivated();
	}

	public void setCommunicationCapabilityActive(boolean active) {
		getCommunicationCapability().setActivated(active);
	}

	public boolean hasAgentRunningOnIt(Agent commandingAgent) {
		return agents.contains(commandingAgent);
	}

	public void addAgent(Agent agent) {
		if (agent == null) {
			throw new IllegalArgumentException();
		}
		agents.add(agent);
	}

	public void removeAgent(Agent agent) {
		agents.remove(agent);
	}

	public Set<Agent> getAgents() {
		return Collections.unmodifiableSet(agents);
	}

	@Override
	public void processTick(long timePassed) {
		if (hasCommunicationCapability() && isCommunicationCapabilityActive()) {
			getCommunicationCapability().updateRouting(timePassed);
		}
	}
	
	public HashSet<Device> getConnectedDevices() {
		HashSet<Device> devicesInRange = new HashSet<Device>();
		for (Router r : getCommunicationCapability().getRouters()) {
			for (CommunicationLink cl : r.getCommunicationLinks()) {
				Router otherRouter = cl.getOtherEnd(r);
				devicesInRange.add(otherRouter.getDevice());
			}
		}
		return devicesInRange;
	}
	
	public HashSet<PhysicalEntity<?>> getConnectedEntities() {
		HashSet<PhysicalEntity<?>> entitiesInRange = new HashSet<PhysicalEntity<?>>();
		for (Router r : getCommunicationCapability().getRouters()) {
			for (CommunicationLink cl : r.getCommunicationLinks()) {
				Router otherRouter = cl.getOtherEnd(r);
				entitiesInRange.add(otherRouter.getDevice().getPhysicalEntity());
			}
		}
		return entitiesInRange;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " " + getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Device) {
			Device device = (Device) obj;
			return device.getId() == getId();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return ("Device:" + getId()).hashCode();
	}
}
