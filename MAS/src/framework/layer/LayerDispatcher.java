package framework.layer;

import framework.utils.Dispatcher;

/**
 * A dispatcher that receives simulation ticks from the simulation core and
 * passes them on to the listeners in this registry of this dispatcher's layer.
 * 
 */
public class LayerDispatcher extends Dispatcher {

	private final Layer layer;

	public LayerDispatcher(Layer layer, int turnoverCount) {
		super(turnoverCount);
		if (layer == null) {
			throw new IllegalArgumentException();
		}
		this.layer = layer;
	}

	@Override
	protected void processTick() {
		layer.getRegistry().copyNewListeners();
		for (TickListener listener : layer.getRegistry().getListeners()) {
			listener.processTick(getTimeSinceLastTick());
		}
	}

}
