package framework.layer.physical.command;

/**
 * The attempted execution of a command is illegal
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class IllegalCommandException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final Command<?> command;

	public IllegalCommandException(Command<?> command) {
		this.command = command;
	}

	public Command<?> getCommand() {
		return command;
	}

}
