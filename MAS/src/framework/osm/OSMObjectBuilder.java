package framework.osm;

import java.util.HashMap;

public abstract class OSMObjectBuilder<X extends OSMObject> {

	private final OSMObjectPool objectPool;
	private X osmObject;

	public OSMObjectBuilder(OSMObjectPool objectPool) {
		if (objectPool == null) {
			throw new IllegalArgumentException();
		}
		this.objectPool = objectPool;
	}

	protected OSMObjectPool getObjectPool() {
		return objectPool;
	}

	protected void setOsmObject(X osmObject) {
		if (osmObject == null) {
			throw new IllegalArgumentException();
		}
		if (this.osmObject != null) {
			throw new IllegalStateException();
		}
		this.osmObject = osmObject;
	}

	public X getOsmObject() {
		if (osmObject == null) {
			throw new IllegalStateException();
		}
		return osmObject;
	}

	public abstract void initiate(HashMap<String, String> arguments);

	public abstract void handle(String name, HashMap<String, String> arguments);

	public void terminate() {
		getOsmObject().onCreationDone();
		getObjectPool().addObject(getOsmObject());
	}

	protected int extractInt(String key, HashMap<String, String> arguments) {
		return Integer.valueOf(getValue(key, arguments));
	}

	protected double extractDouble(String key, HashMap<String, String> arguments) {
		return Double.valueOf(getValue(key, arguments));
	}

	protected String extractString(String key, HashMap<String, String> arguments) {
		return getValue(key, arguments);
	}

	protected boolean extractBoolean(String key, HashMap<String, String> arguments) {
		String value = getValue(key, arguments);
		if (value.equals("yes") || value.equals("true") || value.equals("1")) {
			return true;
		} else {
			//TODO undefinded?
			return false;
		}
	}

	private String getValue(String key, HashMap<String, String> arguments) {
		if (key == null || arguments == null) {
			throw new IllegalArgumentException();
		}
		if (!arguments.containsKey(key)) {
			throw new IllegalArgumentException();
		}
		String value = arguments.get(key);
		if (value == null) {
			throw new IllegalArgumentException();
		}
		return value;
	}
}
