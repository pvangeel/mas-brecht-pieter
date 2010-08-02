package framework.instructions;

import framework.layer.physical.PhysicalStructure;

/**
 *
 * An instruction that the simulator should execute at a given time
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public abstract class Instruction<P extends PhysicalStructure<?>> {

	private long executionTime;
	
	//transient is ignored by xstream
	private transient InstructionManager<? extends P> instructionManager;

	public Instruction(long executionTime) {
		if (executionTime < 0) {
			throw new IllegalArgumentException();
		}
		this.executionTime = executionTime;
	}

	protected Instruction() {
	}

	void setInstructionManager(InstructionManager<? extends P> instructionManager) {
		if (instructionManager == null) {
			throw new IllegalArgumentException();
		}
		if (this.instructionManager != null) {
			throw new IllegalStateException();
		}
		this.instructionManager = instructionManager;
	}

	protected InstructionManager<? extends P> getInstructionManager() {
		if (instructionManager == null) {
			throw new IllegalStateException();
		}
		return instructionManager;
	}

	public long getExecutionTime() {
		return executionTime;
	}

	public abstract void execute();

}
