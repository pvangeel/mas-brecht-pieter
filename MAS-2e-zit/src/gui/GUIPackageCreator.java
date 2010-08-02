package gui;

import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUIPackageCreator extends GUIObjectCreator {

	public GUIPackageCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof PackageCreatedEvent ) {
			PackageCreatedEvent e = (PackageCreatedEvent) event;
			return new GUIPDPPackage(e.getPackageId(), e.getPosition());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return PackageCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return PackagePickedEvent.class;
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		if (event instanceof PackagePickedEvent ) {
			System.out.println("GUI PICKED");
			PackagePickedEvent e = (PackagePickedEvent) event;
			return new GUIPDPPackage(e.getPackageId(), null);
		} else {
			throw new IllegalStateException();
		}
	}

}
