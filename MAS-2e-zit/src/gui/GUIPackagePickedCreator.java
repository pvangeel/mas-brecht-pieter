package gui;

import layer.physical.events.PackageDeliveredEvent;
import layer.physical.events.PackagePickedEvent;
import framework.events.Event;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUIPackagePickedCreator extends GUIObjectCreator {

	public GUIPackagePickedCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof PackagePickedEvent ) {
			PackagePickedEvent e = (PackagePickedEvent) event;
			return new GUIPDPPickedPackage(e.getPackageId(), e.getDeliveryPosition());
		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return PackagePickedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return PackageDeliveredEvent.class;
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		if (event instanceof PackageDeliveredEvent ) {
			System.out.println("GUI PICKED");
			PackageDeliveredEvent e = (PackageDeliveredEvent) event;
			return new GUIPDPPickedPackage(e.getPackageId(), null);
		} else {
			throw new IllegalStateException();
		}
	}

}
