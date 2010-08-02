package gui.creators;

import framework.events.Event;
import framework.events.deployment.StorageCapabilityCreatedEvent;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;
import gui.objects.GUIStorageCapability;

public class GUIStorageCapabilityCreator extends GUIObjectCreator {

	public GUIStorageCapabilityCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof StorageCapabilityCreatedEvent) {
			StorageCapabilityCreatedEvent e = (StorageCapabilityCreatedEvent) event;
			return new GUIStorageCapability(e.getStorageCapabilityId(), e.getStorageCapacity());
		}
		throw new IllegalStateException();
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		throw new IllegalStateException();
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return StorageCapabilityCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return null;
	}

}
