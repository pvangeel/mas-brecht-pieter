package gui.creators;

import layer.physical.events.TwoWayRoadCreatedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;
import gui.objects.GUITwoWayRoad;

public class GUITwoWayRoadCreator extends GUIObjectCreator {

	public GUITwoWayRoadCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof TwoWayRoadCreatedEvent) {
			TwoWayRoadCreatedEvent e = (TwoWayRoadCreatedEvent) event;
			return new GUITwoWayRoad(e.getTwoWayRoadId());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return TwoWayRoadCreatedEvent.class;
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
