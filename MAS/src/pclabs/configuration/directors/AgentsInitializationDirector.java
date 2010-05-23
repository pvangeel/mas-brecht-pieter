package pclabs.configuration.directors;

import pclabs.configuration.DelayedTimePattern;
import pclabs.configuration.intructions.CreateSimpleAgent;
import pclabs.physicallayer.entities.Crossroads;
import pclabs.physicallayer.entities.Road;
import pclabs.physicallayer.entities.Truck;
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
public class AgentsInitializationDirector extends InitializationDirector<PhysicalConnectionStructure<Truck, Crossroads, Road>> {

	public AgentsInitializationDirector() {
		super(new DelayedTimePattern(11000));
	}

	/**
	 * Create just one Agent and deploy the vehicle on the CROSSROAD, at position (x,y)
	 */
	@Override
	protected void createAndDeploy() {
		InstructionManager<PhysicalConnectionStructure<Truck, Crossroads, Road>> instructionManager = getInstructionManager();
		
		long currentTime = VirtualClock.currentTime();
		
		instructionManager.addInstruction(new CreateSimpleAgent(currentTime, 1));
		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 1, 1));


//		instructionManager.addInstruction(new CreateSimpleAgent(currentTime, 2));
//		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 2, 2));
//		
//		instructionManager.addInstruction(new CreateSimpleAgent(currentTime, 3));
//		instructionManager.addInstruction(new DeployAgentInstruction(currentTime, 3, 3));

	}

}
