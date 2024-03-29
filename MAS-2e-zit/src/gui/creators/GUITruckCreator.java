package gui.creators;

import layer.physical.events.TruckCreatedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;
import gui.objects.GUIDeliveryVehicle;

public class GUITruckCreator extends GUIObjectCreator {

	public GUITruckCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof TruckCreatedEvent ) {
			return new GUIDeliveryVehicle( ((TruckCreatedEvent) event).getVehicleId() );
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return TruckCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return null;
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		throw new UnsupportedOperationException();
	}

}
