package framework.instructions;

public interface InstructionListener {
	
	public void handleInstruction(Instruction<?> instruction);

	public void terminate();

}
