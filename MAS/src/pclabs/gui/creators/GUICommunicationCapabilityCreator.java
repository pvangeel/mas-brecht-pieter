package pclabs.gui.creators;

import java.util.ArrayList;
import java.util.List;

import pclabs.gui.objects.GUICommunicationCapability;
import pclabs.gui.objects.GUIRouter;
import framework.events.Event;
import framework.events.deployment.CommunicationCapabilityCreatedEvent;
import framework.gui.GUI;
import framework.gui.GUIObject;
import framework.gui.GUIObjectCreator;

public class GUICommunicationCapabilityCreator extends GUIObjectCreator {

	public GUICommunicationCapabilityCreator(GUI gui) {
		super(gui);
	}

	@Override
	protected GUIObject createGUIObject(Event event) {
		if (event instanceof CommunicationCapabilityCreatedEvent) {
			CommunicationCapabilityCreatedEvent e = (CommunicationCapabilityCreatedEvent) event;
			List<GUIRouter> routers = new ArrayList<GUIRouter>();
			for (int id : e.getRouterIds()) {
				routers.add(getGUI().findSpecificGUIObject(GUIRouter.class, id));
			}
			return new GUICommunicationCapability(e.getCommunicationCapabilityId(), routers);
		}
		throw new IllegalStateException();
	}

	@Override
	protected GUIObject getDestroyGUIObject(Event event) {
		throw new IllegalStateException();
	}

	@Override
	protected Class<? extends Event> getCreationEventClass() {
		return CommunicationCapabilityCreatedEvent.class;
	}

	@Override
	protected Class<? extends Event> getDestroyEventClass() {
		return null;
	}

}
