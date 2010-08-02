package gui.objects;

public class GUIStorageCapability extends GUICapability {

	private long storageCapacity;

	public GUIStorageCapability(int id, long storageCapacity) {
		super(id);
		if (storageCapacity < 0) {
			throw new IllegalArgumentException();
		}
		this.storageCapacity = storageCapacity;
	}
	
	public long getStorageCapacity() {
		return storageCapacity;
	}

}
