package framework.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ReflectionUtils {

	public static List<Field> getAllFields(Class<?> c) {
		List<Field> fields = new ArrayList<Field>();
		if (c == null) {
			return fields;
		}
		fields.addAll(getAllFields(c.getSuperclass()));
		for (Field field : c.getDeclaredFields()) {
			fields.add(field);
		}
		return fields;

	}

	public static Field getField(Class<?> c, String fieldName) {
		for (Field field : getAllFields(c)) {
			if (field.getName().equals(fieldName)) {
				field.setAccessible(true);
				return field;
			}
		}
		throw new IllegalArgumentException();
	}
}
