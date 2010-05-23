//package pclabs.gui.creators;
//
//import pclabs.events.FeasibilityDataHolderCreatedEvent;
//import pclabs.gui.objects.GUIFeasibilityDataStoreHolder;
//import framework.events.Event;
//import framework.gui.GUI;
//import framework.gui.GUIObject;
//import framework.gui.GUIObjectCreator;
//
//public class GUIFeasibilityDataStoreHolderCreator extends GUIObjectCreator {
//	public GUIFeasibilityDataStoreHolderCreator(GUI gui) {
//		super(gui);
//	}
//	
//	@Override
//	protected GUIObject createGUIObject(Event event) {
//		if (event instanceof FeasibilityDataHolderCreatedEvent) {
//			FeasibilityDataHolderCreatedEvent e = (FeasibilityDataHolderCreatedEvent) event;
//			return new GUIFeasibilityDataStoreHolder(e.getFeasibilityDataHolderId());
//		} else {
//			throw new IllegalStateException();
//		}
//	}
//
//	@Override
//	protected GUIObject getDestroyGUIObject(Event event) {
//		throw new IllegalStateException();
//	}
//
//	@Override
//	protected Class<? extends Event> getCreationEventClass() {
//		return FeasibilityDataHolderCreatedEvent.class;
//	}
//
//	@Override
//	protected Class<? extends Event> getDestroyEventClass() {
//		return null;
//	}
//
//}
