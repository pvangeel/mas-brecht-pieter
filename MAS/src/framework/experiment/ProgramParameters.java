package framework.experiment;

import java.util.HashMap;

/**
 * 
 * A class for storage and retrieval of environment parameters, using their
 * names. The Environment offers a central place where parameters used
 * throughout the system can be set.
 * 
 * @author Bart Tuts and Jelle Van Gompel
 * 
 */
public class ProgramParameters {

	private final HashMap<String, Object> parameters;

	private ProgramParameters(HashMap<String, Object> parameters) {
		if (parameters == null) {
			throw new IllegalArgumentException();
		}
		this.parameters = parameters;
	}

	public <T> T getParameter(String name, Class<T> c) {
		if (name == null || c == null) {
			throw new IllegalArgumentException();
		}
		if (!parameters.containsKey(name)) {
			throw new IllegalArgumentException(
					"Could not find parameter with name: " + name);
		}
		Object parameter = parameters.get(name);
		if (!c.isInstance(parameter)) {
			throw new IllegalArgumentException("Cannot cast parameter " + name
					+ " of type " + parameter.getClass() + " to type " + c);
		}
		return c.cast(parameter);
	}

	private static ProgramParameters environment;

	public static ProgramParameters getProgramParameters() {
		if (environment == null) {
			throw new IllegalStateException("environment not initialized");
		}
		return environment;
	}

	public static void initialize(HashMap<String, Object> parameters) {
		if (environment != null) {
			throw new IllegalStateException("environment already initialized");
		}
		environment = new ProgramParameters(parameters);
	}

	public static void reset() {
		if (environment == null) {
			throw new IllegalStateException("environment not initialized");
		}
		environment = null;
	}

	public void setParameter(String key, Object value) {
		if (key == null || value == null) {
			throw new IllegalArgumentException();
		}
		parameters.put(key, value);
	}

}
