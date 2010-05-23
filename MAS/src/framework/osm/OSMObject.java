package framework.osm;

public abstract class OSMObject {

	private final int id;
	private boolean used;

	public OSMObject(int id) {
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}

	public boolean isUsed() {
		return used;
	}

	public abstract void executeSpecificAddOperations();

	public abstract void executeSpecificRemoveOperations();

	public abstract void onCreationDone();
}
