package framework.initialization;

import framework.experiment.ProgramParameters;

/**
 * 
 * A infinite sequence of times with a variable interval between every next
 * time. THe variable interval is the parameter "intervalParameter" in the
 * environment
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */

public class VariableIntervalTimePattern extends TimePattern {

	private final String intervalParameter;
	private long lastSpawn = 0;

	public VariableIntervalTimePattern(String intervalParameter) {
		if (intervalParameter == null) {
			throw new IllegalArgumentException();
		}
		this.intervalParameter = intervalParameter;
	}

	@Override
	public long getNextTime() {
		lastSpawn += ProgramParameters.getProgramParameters().getParameter(
				intervalParameter, Long.class);
		return lastSpawn;
	}

	@Override
	public boolean hasNextTime() {
		return true;
	}

}
