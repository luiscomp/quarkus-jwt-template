package com.logicsoftware.utils.enums;

public class EnumUtils {
    public static String enumToString(Enum<?>[] enumValues) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < enumValues.length; i++) {
            sb.append(enumValues[i].name());
            if (i < enumValues.length - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}
