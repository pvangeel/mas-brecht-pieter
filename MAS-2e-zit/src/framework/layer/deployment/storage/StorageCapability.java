package framework.layer.deployment.storage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import framework.events.EventBroker;
import framework.events.deployment.StorageCapabilityCreatedEvent;
import framework.layer.deployment.devices.Capability;
import framework.layer.deployment.devices.InactiveCapabilityException;

/**
 * StorageCapability on a Device. Data can be stored, retrieved and deleted.
 * There is a maximum capacity
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public class StorageCapability extends Capability {

	private final Set<Data> storedData = new HashSet<Data>();
	private final long capacity;

	public StorageCapability(long capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;
		EventBroker.getEventBroker().notifyAll(new StorageCapabilityCreatedEvent(this));
	}

	/**
	 * Store the given data in the storageCapability
	 */
	public void storeData(Data data) throws StorageCapacityExceededExeption,
			InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		if (data == null) {
			throw new IllegalArgumentException();
		}
		if (!canStoreData(data)) {
			throw new StorageCapacityExceededExeption();
		}
		storedData.add(data);
	}

	/**
	 * Store the given collection of data in the storageCapability. When adding
	 * a large collection of data, this method is much more efficient than
	 * calling addStore(Data) on each of them.
	 */
	public void storeData(Collection<? extends Data> dataCollection) throws InactiveCapabilityException,
			StorageCapacityExceededExeption {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		if (dataCollection == null) {
			throw new IllegalArgumentException();
		}
		if (!canStoreData(dataCollection)) {
			throw new StorageCapacityExceededExeption();
		}
		storedData.addAll(dataCollection);
	}

	/**
	 * @return true if this storageCapability can store this Data object
	 */
	public boolean canStoreData(Data data) throws InactiveCapabilityException {
		return getUsedStorage() + data.getStorageSize() <= getCapacity();
	}

	/**
	 * @return true if this storageCapability can store all the Data objects in
	 *         the given collection
	 */
	public boolean canStoreData(Collection<? extends Data> dataCollection) throws InactiveCapabilityException {
		long dataCollectionSize = 0;
		for (Data d : dataCollection) {
			dataCollectionSize += d.getStorageSize();
		}
		return getUsedStorage() + dataCollectionSize <= getCapacity();
	}

	/**
	 * Delete the given Data object from the storage capability. If the given
	 * Data object is not in storage, nothing happens
	 */
	public void removeData(Data data) throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		if (data == null) {
			throw new IllegalArgumentException();
		}
		storedData.remove(data);
	}

	/**
	 * Completely empties the data storage
	 */
	public void clearStorage() throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		storedData.clear();
	}

	/**
	 * @return The (unmodifiable) collection of the Data objects stored in this
	 *         storageCapability
	 */
	public Collection<Data> getStoredData() throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		return Collections.unmodifiableCollection(storedData);
	}

	public long getCapacity() {
		return capacity;
	}

	public long getUsedStorage() throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		long storedDataSize = 0;
		for (Data data : storedData) {
			storedDataSize += data.getStorageSize();
		}
		return storedDataSize;
	}

	public static class StorageCapacityExceededExeption extends Exception {
	}

	@Override
	protected void onActiveChanged(boolean activated) {
		// do nothing
	}

	public void removeAll(Collection<Data> data) throws InactiveCapabilityException {
		if (!isActivated()) {
			throw new InactiveCapabilityException();
		}
		if (data == null) {
			throw new IllegalArgumentException();
		}
		storedData.removeAll(data);
	}
}
