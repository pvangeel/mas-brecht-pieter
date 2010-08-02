package framework.layer.physical;

import framework.layer.Layer;

public class PhysicalLayer<S extends PhysicalStructure<?>> extends Layer {

	private final S physicalStructure;

	public PhysicalLayer(S physicalStructure) {
		super(5000);
		if (physicalStructure == null) {
			throw new IllegalArgumentException();
		}
		this.physicalStructure = physicalStructure;
		physicalStructure.setPhysicalLayer(this);
	}

	public S getPhysicalStructure() {
		return physicalStructure;
	}

}
