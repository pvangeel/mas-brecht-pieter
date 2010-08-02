package framework.utils;

import java.util.HashMap;

/**
 * 
 * A centralized ID generator to guarantee that within each type of entity, Id's are unique
 * 
 * @author Bart Tuts and Jelle Van Gompel
 *
 */

public class IdGenerator {

	private HashMap<Class<?>, Integer> idMappings = new HashMap<Class<?>, Integer>();

	private static IdGenerator idGenerator;

	public static IdGenerator getIdGenerator() {
		if (idGenerator == null) {
			idGenerator = new IdGenerator();
		}
		return idGenerator;
	}

	public int getNextId(Class<?> c) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		int next;
		if (idMappings.containsKey(c)) {
			next = idMappings.get(c).intValue() + 1;
		} else {
			next = 1;
		}
		idMappings.put(c, next);
		return next;
	}

	public void updateId(Class<?> c, int id) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		int newId;
		if (idMappings.containsKey(c)) {
			int current = idMappings.get(c).intValue();
			newId = (current > id) ? current : id;
		} else {
			newId = id;
		}
		idMappings.put(c, newId);
	}
}
