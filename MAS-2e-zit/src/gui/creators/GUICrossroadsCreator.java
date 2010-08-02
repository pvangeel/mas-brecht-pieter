package gui.creators;

import layer.physical.events.CrossRoadsCreatedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;
import gui.objects.GUICrossRoads;

public class GUICrossroadsCreator extends GUIObjectCreator {

	public GUICrossroadsCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if(event instanceof CrossRoadsCreatedEvent) {
			CrossRoadsCreatedEvent e = (CrossRoadsCreatedEvent) event;
			return new GUICrossRoads(e.getCrossroadsId());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return CrossRoadsCreatedEvent.class;
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
