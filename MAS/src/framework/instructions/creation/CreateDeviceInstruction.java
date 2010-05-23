package framework.instructions.creation;

import framework.layer.deployment.communication.CommunicationCapability;
import framework.layer.deployment.devices.Device;
import framework.layer.deployment.storage.StorageCapability;

public class CreateDeviceInstruction extends CreateInstruction {

	private int communicationCapabilityId;
	private int storageCapabilityId;

	public CreateDeviceInstruction(long executionTime, int deviceId, int communicationCapabilityId, int storageCapabilityId) {
		super(executionTime, deviceId);
		this.communicationCapabilityId = communicationCapabilityId;
		this.storageCapabilityId = storageCapabilityId;
	}

	protected CreateDeviceInstruction() {
	}

	@Override
	protected Object createObject() {
		CommunicationCapability communicationCapability = null;
		if (communicationCapabilityId != -1) {
			communicationCapability = getInstructionManager().findSpecificObject(CommunicationCapability.class, communicationCapabilityId);
		}
		StorageCapability storageCapability = null;
		if (storageCapabilityId != -1) {
			storageCapability = getInstructionManager().findSpecificObject(StorageCapability.class, storageCapabilityId);
		}
		return new Device(communicationCapability, storageCapability);
	}

}
