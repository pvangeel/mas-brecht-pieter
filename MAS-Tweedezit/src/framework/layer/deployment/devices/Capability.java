package framework.layer.deployment.devices;

import framework.events.EventBroker;
import framework.events.deployment.CapabilityStatusChangedEvent;
import framework.utils.IdGenerator;

/**
 * 
 * A capability encapsulates a certain feature of a device, for example the
 * capability to communicate with other devices or the capability to store data
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public abstract class Capability {

	private final int id = IdGenerator.getIdGenerator().getNextId(
			Capability.class);
	private boolean activated = true;

	public int getId() {
		return id;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
		onActiveChanged(activated);
		EventBroker.getEventBroker().notifyAll(
				new CapabilityStatusChangedEvent(this, activated));
	}

	/**
	 * When a capability is activated or deactivated, this method is called. If
	 * the capability was already in the state it is set to, this method is
	 * still called.
	 * 
	 * @param active
	 *            indicates what the capability's new status is
	 */
	protected abstract void onActiveChanged(boolean active);
}