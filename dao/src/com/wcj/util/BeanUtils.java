package com.wcj.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class BeanUtils
{
    public static Map<String, Object> toMap(Object bean)
    {
        try
        {
            Map<String, Object> result = new HashMap<String, Object>();
            for (Field field : bean.getClass().getDeclaredFields())
            {
                field.setAccessible(true);
                result.put(field.getName(), field.get(bean));
            }
            return result;
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException("bean to map error.", e);
        }
    }
}
