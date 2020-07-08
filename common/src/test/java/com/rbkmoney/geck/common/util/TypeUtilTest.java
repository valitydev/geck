package com.rbkmoney.geck.common.util;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;

public class TypeUtilTest {

    String dateTime = "2017-06-19T10:21:09.539909Z";
    LocalDateTime localDateTime = LocalDateTime.of(
            2017, 6, 19, 10, 21, 9, 539909 * 1000
    );

    @Test
    public void stringToLocalDateTimeTest() {
        assertEquals(localDateTime, TypeUtil.stringToLocalDateTime(dateTime));
    }


    @Test
    public void stringToLocalDateTimeWithNonUTCOffsetTest() {
        assertEquals(localDateTime.plusHours(17).plusMinutes(55),
                TypeUtil.stringToLocalDateTime(dateTime, ZoneOffset.of("+17:55")));
    }

    @Test
    public void stringToInstantTest() {
        assertEquals(localDateTime.toInstant(ZoneOffset.UTC), TypeUtil.stringToInstant(dateTime));
    }

    @Test
    public void localDateTimeToStringTest() {
        assertEquals(TypeUtil.temporalToString(localDateTime), dateTime);
    }

    @Test(expected = IllegalArgumentException.class)
    public void byteOverflowTest() {
        TypeUtil.toByteExact(Byte.MAX_VALUE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shortOverflowTest() {
        TypeUtil.toShortExact(Short.MAX_VALUE + 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void intOverflowTest() {
        TypeUtil.toIntExact((long) Integer.MAX_VALUE + 1);
    }

}
