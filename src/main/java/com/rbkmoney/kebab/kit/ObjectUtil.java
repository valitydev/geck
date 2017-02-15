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

    public static byte toByteExact(long value) {
        if ((byte) value != value) {
            throw new IllegalArgumentException("byte overflow");
        }
        return (byte) value;
    }

    public static short toShortExact(long value) {
        if ((short) value != value) {
            throw new IllegalArgumentException("short overflow");
        }
        return (short) value;
    }

    public static int toIntExact(long value) {
        if ((int) value != value) {
            throw new IllegalArgumentException("integer overflow");
        }
        return (int) value;
    }
}
