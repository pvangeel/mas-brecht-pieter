package gui.objects;

import java.awt.Graphics;

import framework.events.Event;
import framework.events.deployment.DeviceAttachedEvent;
import framework.gui.GUIObject;
import framework.layer.physical.position.Position;

public class GUIDevice extends GUIObject {

	private GUIPhysicalEntity entity;
	private GUICommunicationCapability communicationCapability;
	private GUIStorageCapability storageCapability;

	public GUIDevice(int id, GUICommunicationCapability communicationCapability, GUIStorageCapability storageCapability) {
		super(id);
		this.communicationCapability = communicationCapability;
		this.storageCapability = storageCapability;
		if (communicationCapability != null) {
			communicationCapability.setDevice(this);
		}
		if (storageCapability != null) {
			storageCapability.setDevice(this);
		}
	}

	public boolean hasCommunicationCapability() {
		return communicationCapability != null;
	}

	public GUICommunicationCapability getCommunicationCapability() {
		if (!hasCommunicationCapability()) {
			throw new IllegalStateException();
		}
		return communicationCapability;
	}

	public boolean hasStorageCapability() {
		return storageCapability != null;
	}

	public GUIStorageCapability getStorageCapability() {
		if (!hasStorageCapability()) {
			throw new IllegalStateException();
		}
		return storageCapability;
	}

	public GUIPhysicalEntity getEntity() {
		if (!hasEntity()) {
			throw new IllegalStateException();
		}
		return entity;
	}

	public boolean hasEntity() {
		return entity != null;
	}

	@Override
	public void drawProtected(Graphics g) {

	}

	@Override
	public void registerEvents() {
		getGui().getDispatcher().register(DeviceAttachedEvent.class, this);
	}

	@Override
	public void handleEvent(Event event) {
		if (event instanceof DeviceAttachedEvent) {
			handleDeviceAttachedEvent((DeviceAttachedEvent) event);
		}
	}

	private void handleDeviceAttachedEvent(DeviceAttachedEvent event) {
		if (event.getDeviceId() == getId()) {
			entity = getGui().findSpecificGUIObject(GUIPhysicalEntity.class, event.getEntityId());
		}
	}

	@Override
	public boolean isConnectedOk() {
		return hasEntity() && getEntity().isConnectedOk();
	}

	public Position getPosition() {
		if (!isConnectedOk()) {
			throw new IllegalStateException();
		}
		return getEntity().getPosition();
	}
}
