package framework.instructions;

import framework.core.SimulationCore;
import framework.layer.physical.PhysicalStructure;

/**
 * An instruction that finalizes and shuts down the simulation as soon as it is executed
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class ShutDownSimulatorInstruction extends Instruction<PhysicalStructure<?>> {

	public ShutDownSimulatorInstruction(long executionTime) {
		super(executionTime);
	}

	@Override
	public void execute() {
		SimulationCore.getSimulationCore().stopSimulation();
		System.exit(0);
	}

}
