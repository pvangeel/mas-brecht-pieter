package framework.utils;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

/**
 * Determines the size of an object in bytes when it is serialized. This
 * should not be used for anything other than optimization testing since it
 * can be memory and processor intensive.
 * 
 */

public class ObjectMeter {


	public static double getObjectSize(Object object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			oos.close();
			baos.close();
			double size = bytes.length;
			return size;
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalStateException();
		}
	}

}
