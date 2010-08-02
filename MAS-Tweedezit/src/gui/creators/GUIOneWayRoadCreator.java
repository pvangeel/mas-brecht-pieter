package gui.creators;

import layer.physical.events.OneWayRoadCreatedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;
import gui.objects.GUIOneWayRoad;

public class GUIOneWayRoadCreator extends GUIObjectCreator {

	public GUIOneWayRoadCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof OneWayRoadCreatedEvent) {
			OneWayRoadCreatedEvent e = (OneWayRoadCreatedEvent) event;
			return new GUIOneWayRoad(e.getOneWayRoadId());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return OneWayRoadCreatedEvent.class;
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
