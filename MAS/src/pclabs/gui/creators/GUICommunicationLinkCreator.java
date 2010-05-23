package pclabs.gui.creators;

import pclabs.gui.objects.GUICommunicationLink;
import framework.events.Event;
import framework.events.deployment.CommunicationLinkCreatedEvent;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUICommunicationLinkCreator extends GUIObjectCreator {

	public GUICommunicationLinkCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof CommunicationLinkCreatedEvent) {
			CommunicationLinkCreatedEvent e = (CommunicationLinkCreatedEvent) event;
			return new GUICommunicationLink(e.getCommLinkId(), e.getDelayPerMilliMeter(), e.getBandwidth());
		}
		throw new IllegalStateException();
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		throw new IllegalStateException();
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return CommunicationLinkCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return null;
	}

}
