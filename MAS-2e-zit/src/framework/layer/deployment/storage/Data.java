package framework.layer.deployment.storage;

import framework.utils.IdGenerator;

/**
 * Data that can be stored in a Device's data storage
 * 
 * @author Bart Tuts, Jelle Van Gompel
 * 
 */
public abstract class Data {

	private final int id = IdGenerator.getIdGenerator().getNextId(Data.class);

	public int getId() {
		return id;
	}
	
	/**
	 * Return the size of this data package in bytes
	 */
	public abstract long getStorageSize();

}
