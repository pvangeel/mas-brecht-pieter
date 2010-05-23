package pclabs.gui.creators;

import pclabs.gui.objects.GUICommunicationCapability;
import pclabs.gui.objects.GUIDevice;
import pclabs.gui.objects.GUIStorageCapability;
import framework.events.Event;
import framework.events.deployment.DeviceCreatedEvent;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUIDeviceCreator extends GUIObjectCreator {

	public GUIDeviceCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof DeviceCreatedEvent) {
			DeviceCreatedEvent e = (DeviceCreatedEvent) event;
			GUICommunicationCapability communicationCapability = e.hasCommunicationCapability() ? getGUI().findSpecificGUIObject(
					GUICommunicationCapability.class, e.getCommunicationCapabilityId()) : null;
			GUIStorageCapability storageCapability = e.hasStorageCapability() ? getGUI().findSpecificGUIObject(GUIStorageCapability.class,
					e.getStorageCapabilityId()) : null;
			return new GUIDevice(e.getDeviceId(), communicationCapability, storageCapability);
		}
		throw new IllegalStateException();
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		throw new IllegalStateException();
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return DeviceCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return null;
	}

}
