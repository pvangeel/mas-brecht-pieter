package framework.instructions.deployment;

import framework.instructions.Instruction;
import framework.layer.agent.Agent;
import framework.layer.agent.AgentStructure;
import framework.layer.deployment.devices.Device;
import framework.layer.physical.PhysicalStructure;

public class DeployAgentInstruction extends Instruction<PhysicalStructure<?>> {

	private int agentId, deviceId;

	public DeployAgentInstruction(long executionTime, int agentId, int deviceId) {
		super(executionTime);
		this.agentId = agentId;
		this.deviceId = deviceId;
	}

	protected DeployAgentInstruction() {
	}

	@Override
	public void execute() {
		Agent agent = getInstructionManager().findSpecificObject(Agent.class, agentId);
		Device device = getInstructionManager().findSpecificObject(Device.class, deviceId);
		AgentStructure structure = getInstructionManager().getAgentStructure();
		structure.deployAgent(agent, device);
	}

}
