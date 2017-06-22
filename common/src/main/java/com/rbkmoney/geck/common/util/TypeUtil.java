package com.rbkmoney.geck.common.util;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

/**
 * Created by vpankrashkin on 14.02.17.
 */
public class TypeUtil {

    private static final DateTimeFormatter FORMATTER = ISO_INSTANT;

    public static LocalDateTime stringToLocalDateTime(String dateTimeStr) throws IllegalArgumentException {
        return stringToLocalDateTime(dateTimeStr, ZoneOffset.UTC);
    }

    public static LocalDateTime stringToLocalDateTime(String dateTimeStr, ZoneOffset zoneOffset) throws IllegalArgumentException {
        try {
            return LocalDateTime.ofInstant(stringToInstant(dateTimeStr), zoneOffset);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Failed to convert in LocalDateTime: " + dateTimeStr, e);
        }
    }

    public static Instant stringToInstant(String dateTimeStr) throws IllegalArgumentException {
        try {
            return Instant.from(stringToTemporal(dateTimeStr));
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Failed to convert in Instant: " + dateTimeStr, e);
        }
    }

    public static TemporalAccessor stringToTemporal(String dateTimeStr) throws IllegalArgumentException {
        try {
            return FORMATTER.parse(dateTimeStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse: " + dateTimeStr, e);
        }
    }

    public static String temporalToString(LocalDateTime localDateTime) throws IllegalArgumentException {
        return temporalToString(localDateTime.toInstant(ZoneOffset.UTC));
    }

    public static String temporalToString(LocalDateTime localDateTime, ZoneOffset zoneOffset) throws IllegalArgumentException {
        return temporalToString(localDateTime.toInstant(zoneOffset));
    }

    public static String temporalToString(TemporalAccessor temporalAccessor) throws IllegalArgumentException {
        try {
            return FORMATTER.format(temporalAccessor);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to format:" + temporalAccessor, e);
        }
    }

    public static <T> T convertType(Class<T> tClass, Object val) throws IllegalArgumentException {
        if (tClass.isAssignableFrom(val.getClass())) {
            return (T) val;
        } else {
            throw new IllegalArgumentException(String.format("Wrong type: %s, expected: %s", val.getClass().getName(), tClass.getName()));
        }
    }

    public static byte toByteExact(long value) throws IllegalArgumentException {
        if ((byte) value != value) {
            throw new IllegalArgumentException("byte overflow");
        }
        return (byte) value;
    }

    public static short toShortExact(long value) throws IllegalArgumentException {
        if ((short) value != value) {
            throw new IllegalArgumentException("short overflow");
        }
        return (short) value;
    }

    public static int toIntExact(long value) throws IllegalArgumentException {
        if ((int) value != value) {
            throw new IllegalArgumentException("integer overflow");
        }
        return (int) value;
    }
}
