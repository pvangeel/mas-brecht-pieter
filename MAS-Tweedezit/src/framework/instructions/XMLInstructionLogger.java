package framework.instructions;

import framework.xml.XMLLogger;

/**
 * An XML Logger for logging instructions
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class XMLInstructionLogger extends XMLLogger implements InstructionListener {

	public XMLInstructionLogger(String filename, int persistenceTreshold) {
		super(filename, persistenceTreshold);
		InstructionManager.getInstructionManager().register(this);
	}

	@Override
	public void handleInstruction(Instruction<?> instruction) {
		addObject(instruction);
	}

	@Override
	public void terminate() {
		persistAll();
	}

}
