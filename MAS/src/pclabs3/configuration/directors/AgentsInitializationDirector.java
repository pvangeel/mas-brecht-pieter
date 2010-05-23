package pclabs3.configuration.directors;

import pclabs.configuration.DelayedTimePattern;
import pclabs3.configuration.instructions.CreateAgentInstruction;
import pclabs3.layer.agent.Broker;
import pclabs3.layer.agent.ResourceManager;
import pclabs3.layer.physical.NoC;
import pclabs3.layer.physical.NullConnection;
import pclabs3.layer.physical.NullConnectionEntity;
import pclabs3.layer.physical.SiteLocation;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.instructions.InstructionManager;
import framework.instructions.deployment.DeployAgentInstruction;
import framework.layer.physical.PhysicalConnectionStructure;

/**
 * populates the Agent and Deployment Layers with Agents and Devices
 * 
 * @author marioct
 *
 */
public class AgentsInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> {

	private NoC noc;
	
	public AgentsInitializationDirector(NoC noc) {
		super(new DelayedTimePattern(11000));
		this.noc = noc;
	}

	/**
	 * Create just one Agent and deploy the vehicle on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		InstructionManager<PhysicalConnectionStructure<NullConnectionEntity, SiteLocation, NullConnection>> instructionManager = getInstructionManager();
		
		long currentTime = VirtualClock.currentTime();
		
		instructionManager.addInstruction(new CreateAgentInstruction(currentTime, Broker.class, 1, noc));
		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 1, 1));

		instructionManager.addInstruction(new CreateAgentInstruction(currentTime, ResourceManager.class, 2, noc));
		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 2, 2));
	}

}
