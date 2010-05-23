package framework.instructions.creation;

import framework.layer.deployment.storage.StorageCapability;

public class CreateStorageCapabilityInstruction extends CreateInstruction {
	
	private long capacity;

	public CreateStorageCapabilityInstruction(long executionTime, int id, long capacity) {
		super(executionTime, id);
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
	}

	protected CreateStorageCapabilityInstruction() {
	}

	@Override
	protected Object createObject() {
		return new StorageCapability(capacity);
	}

}
