package gui;

import layer.physical.events.GradientFieldCreatedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUIGradientFieldCreator extends GUIObjectCreator {

	public GUIGradientFieldCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof GradientFieldCreatedEvent ) { //change this to listen to GF creation
			GradientFieldCreatedEvent e = (GradientFieldCreatedEvent) event;
			return new GUIGradientField(e.getPackageId(), e.getPosition(), e.getRadius());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return GradientFieldCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return GradientFieldDestroyedEvent.class;
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		if (event instanceof GradientFieldDestroyedEvent ) {
			GradientFieldDestroyedEvent e = (GradientFieldDestroyedEvent) event;
			return new GUIGradientField(e.getPackageId(), null, 0);
		} else {
			throw new IllegalStateException();
		}
	}

}
