package configuration.directors;

import configuration.DelayedTimePattern;
import configuration.intructions.CreateDelegateMASDeliveryAgent;
import layer.physical.entities.Crossroads;
import layer.physical.entities.Road;
import layer.physical.entities.Truck;
import framework.core.VirtualClock;
import framework.initialization.InitializationDirector;
import framework.instructions.InstructionManager;
import framework.instructions.deployment.DeployAgentInstruction;
import framework.layer.physical.PhysicalConnectionStructure;
import framework.utils.Utils;

/**
 * populates the Agent and Deployment Layers with Agents and Devices
 * 
 * @author marioct
 *
 */
@Deprecated
public class DelegateMASAgentsInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	public DelegateMASAgentsInitializationDirector() {
		super(new DelayedTimePattern(11000));
	}

	/**
	 * Create just one Agent and deploy the vehicle on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		InstructionManager<PhysicalConnectionStructure<Truck, Crossroads, Road>> instructionManager = getInstructionManager();
		
		long currentTime = VirtualClock.currentTime();
		
		instructionManager.addInstruction(new CreateDelegateMASDeliveryAgent(currentTime, 1909090909));
		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 1909090909, 1));
		
		int delay = 300;
		instructionManager.addInstruction(new CreateDelegateMASDeliveryAgent(currentTime + Utils.minutesToMicroSeconds(delay), 1909090908));
		instructionManager.addInstruction(new DeployAgentInstruction(currentTime + Utils.minutesToMicroSeconds(delay), 1909090908, 2));

	}

}
