package gui.objects;

import java.awt.Graphics;

import framework.events.Event;
import framework.events.deployment.CapabilityStatusChangedEvent;
import framework.gui.GUIObject;

public abstract class GUICapability extends GUIObject {

	private boolean activated = true;
	private GUIDevice device;

	public GUICapability(int id) {
		super(id);
	}

	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	public boolean isActivated() {
		return activated;
	}

	public void setDevice(GUIDevice device) {
		if (device == null) {
			throw new IllegalArgumentException();
		}
		if (this.device != null) {
			throw new IllegalStateException();
		}
		this.device = device;
	}
	
	public GUIDevice getDevice() {
		if(!hasDevice()) {
			throw new IllegalStateException();
		}
		return device;
	}
	
	public boolean hasDevice() {
		return device != null;
	}
	
	@Override
	public void drawProtected(Graphics g) {
	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(CapabilityStatusChangedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof CapabilityStatusChangedEvent) {
			handleCapabilityStatusChangedEvent((CapabilityStatusChangedEvent) event);
		}
	}

	private void handleCapabilityStatusChangedEvent(CapabilityStatusChangedEvent event) {
		if (event.getCapabilityId() == getId()) {
			setActivated(event.isActive());
		}
	}
	
	@Override
	public boolean isConnectedOk() {
		return hasDevice() && getDevice().isConnectedOk();
	}

}
