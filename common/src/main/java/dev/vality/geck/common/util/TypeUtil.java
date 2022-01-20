package dev.vality.geck.common.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class TypeUtil {

    private static final DateTimeFormatter FORMATTER = ISO_INSTANT;

    public static LocalDateTime stringToLocalDateTime(String dateTimeStr) throws IllegalArgumentException {
        return stringToLocalDateTime(dateTimeStr, ZoneOffset.UTC);
    }

    public static LocalDateTime stringToLocalDateTime(String dateTimeStr, ZoneId zoneId)
            throws IllegalArgumentException {
        try {
            return LocalDateTime.ofInstant(stringToInstant(dateTimeStr), zoneId);
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

    public static LocalDateTime toLocalDateTime(TemporalAccessor temporalAccessor) {
        return toLocalDateTime(temporalAccessor, ZoneOffset.UTC);
    }

    public static LocalDateTime toLocalDateTime(TemporalAccessor temporalAccessor, ZoneId zoneId) {
        return Optional.ofNullable(temporalAccessor)
                .map(
                        value -> LocalDateTime.ofInstant(
                                Instant.from(value),
                                zoneId
                        )
                ).orElse(null);
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


    public static <T extends Enum<T>> T toEnumField(String name, Class<T> enumType, T defaultValue) {
        try {
            return Optional.ofNullable(name)
                    .map(value -> Enum.valueOf(enumType, name))
                    .orElse(defaultValue);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static <T extends Enum<T>> T toEnumField(String name, Class<T> enumType) {
        return toEnumField(name, enumType, null);
    }

    public static <T extends Enum<T>> List<T> toEnumFields(List<String> names, Class<T> enumType) {
        return Optional.ofNullable(names)
                .map(
                        values -> values.stream()
                                .filter(name -> name != null)
                                .map(name -> toEnumField(name, enumType))
                                .collect(Collectors.toList())
                ).orElse(null);
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
