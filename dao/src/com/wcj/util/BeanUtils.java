package com.wcj.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class BeanUtils {
	public static Map<String, Object> toMap(Object bean) {
		try {
			Map<String, Object> result = new HashMap<String, Object>();
			for (Field field : bean.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				result.put(field.getName(), field.get(bean));
			}
			return result;
		} catch (Exception e) {
			throw new IllegalArgumentException("bean to map error.", e);
		}
	}
	
	public static Field getField(Object bean, String fieldName) {
		try {
			Field field = bean.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field;
		} catch (Exception e) {
			throw new IllegalArgumentException("get bean=" + bean + " fieldName=" + fieldName + " error.", e);
		}
	}
	
	public static Object getFieldValue(Object bean, String fieldName) {
		try {
			return getField(bean, fieldName).get(bean);
		} catch (Exception e) {
			throw new IllegalArgumentException("get bean=" + bean + " fieldName=" + fieldName + " value error.", e);
		}
	}
}
