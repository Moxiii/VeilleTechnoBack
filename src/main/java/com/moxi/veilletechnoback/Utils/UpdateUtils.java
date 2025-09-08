package com.moxi.veilletechnoback.Utils;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class UpdateUtils {
public static <T> T mergeNonNull(T original, T update , String ... ignoreFields) {
	if (update == null) return original;
	Set<String> ignored = new HashSet<>(Arrays.asList(ignoreFields));
	Field[] fields = original.getClass().getDeclaredFields();
	for (Field field : fields) {
		field.setAccessible(true);
		if (ignored.contains(field.getName())) continue;
		if (field.isAnnotationPresent(Id.class)) continue;
		String name = field.getName().toLowerCase();
		if (name.equals("createdat") || name.equals("user") || Collection.class.isAssignableFrom(field.getType())) {
			continue;
		}
		try{
			Object newValue = field.get(update);
			if (field.getType().isEnum() || field.getType().equals(LocalDate.class)) {
				field.set(original, newValue);
			}
			else if (isSimple(field.getType())) {
				field.set(original, newValue);
			}
		}catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	return original;
}
private static boolean isSimple(Class<?> clazz) {
	return clazz.isPrimitive()
			|| clazz.equals(String.class)
			|| Number.class.isAssignableFrom(clazz)
			|| clazz.equals(Boolean.class)
			|| clazz.equals(Character.class);
}
}

