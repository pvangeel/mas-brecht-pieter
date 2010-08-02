package framework.layer;

/**
 * An abstract class for the conceptual layers that make up the core of the
 * simulator, each containing a dispatcher that dispatches incoming ticks from
 * the simulator to each of the registered objects in this layer
 * 
 */
public abstract class Layer {

	private final LayerDispatcher dispatcher;
	private final Registry registry = new Registry();

	public Layer(int turnoverCount) {
		if (turnoverCount < 1) {
			throw new IllegalArgumentException();
		}
		this.dispatcher = new LayerDispatcher(this, turnoverCount);
	}

	public LayerDispatcher getDispatcher() {
		return dispatcher;
	}

	public Registry getRegistry() {
		return registry;
	}
}
