package com.rbkmoney.kebab.kit;

import com.rbkmoney.kebab.exception.BadFormatException;

/**
 * Created by vpankrashkin on 14.02.17.
 */
public class ObjectUtil {
    public static <T> T convertType(Class<T> tClass, Object val) throws BadFormatException {
        if (tClass.isAssignableFrom(val.getClass())) {
            return (T) val;
        } else {
            throw new BadFormatException(String.format("Wrong type: %s, expected: %s", val.getClass().getName(), tClass.getName()));
        }
    }
}
