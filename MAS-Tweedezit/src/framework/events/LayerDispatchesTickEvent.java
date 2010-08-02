package framework.events;

import framework.layer.Layer;

/**
 * Event that fires when a layer dispatches a tick event to it's internal
 * structure
 * 
 */
public class LayerDispatchesTickEvent extends Event {

	private String layerTypeName;

	/**
	 * create new LayerDispatchesTickEvent
	 * @param layer	The layer that is dispatching the event 
	 */
	public LayerDispatchesTickEvent(Layer layer) {
		this.layerTypeName = layer.getClass().getName();
	}

	/*
	 * protected constructor, needed for loading events from XML file using reflection
	 */
	protected LayerDispatchesTickEvent() {
	}

	public String getLayerTypeName() {
		return layerTypeName;
	}

	@Override
	public String toString() {
		return "Layer " + layerTypeName + " has dispatched a tick";
	}
}
