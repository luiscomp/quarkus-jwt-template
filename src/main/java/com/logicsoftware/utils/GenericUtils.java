package com.logicsoftware.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GenericUtils {

    public static List<Field> getAllFields(Class<?> clazz) {
        List<Field> fields = new ArrayList<>(Arrays.asList(clazz.getDeclaredFields()));
        if (clazz.getSuperclass() != null) {
            fields.addAll(getAllFields(clazz.getSuperclass()));
        }
        return fields;
    }

    public static boolean fieldHasNonNullProperty(Object value) {
        for (Field field : GenericUtils.getAllFields(value.getClass())) {
            try {
                field.setAccessible(true);
                Object fieldValue = field.get(value);
                if (Objects.nonNull(fieldValue)) {
                    return true;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}
