package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.domain.Enums;
import com.rbkmoney.geck.serializer.domain.Status;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RandomValueGeneratorTest {

    RandomValueGenerator randomValueGenerator = new RandomValueGenerator();

    @Test
    public void generateByteValueTest() {
        System.out.println("Generate byte:");
        generateNumberTest(1000, Byte.SIZE, Byte.MIN_VALUE, Byte.MAX_VALUE, false);
    }

    @Test
    public void generateShortValueTest() {
        System.out.println("Generate short:");
        generateNumberTest(10000, Short.SIZE, Short.MIN_VALUE, Short.MAX_VALUE, false);
    }

    @Test
    public void generateCharTest() {
        System.out.println("Generate char:");
        generateNumberTest(10000, Character.SIZE, Character.MIN_VALUE, Character.MAX_VALUE, true);
    }

    @Test
    public void generateTEnumTest() {
        TEnum tEnum = randomValueGenerator.getTEnum(Enums.class);
        Enum.valueOf(Enums.class, tEnum.toString());
    }

    @Test
    public void generateFieldTest() {
        Status status = new Status();
        TFieldIdEnum field = randomValueGenerator.getField(status);
        assertNotEquals(-1, Arrays.binarySearch(status.getFields(), field));
    }

    @Test
    public void generateStringTest() {
        String value = randomValueGenerator.getString(10000);
        for (char charValue : value.toCharArray()) {
            int type = Character.getType(charValue);
            assertTrue(String.format("unrecognized symbol '%s', type: %d", charValue, type),
                    Character.isLetterOrDigit(charValue)
                            || type != Character.SURROGATE
                            || type != Character.CONTROL
            );
        }

    }

    public void generateNumberTest(int count, int bitsSize, int minValue, int maxValue, boolean unsigned) {
        int[] numbers = new int[count];

        int min = maxValue;
        int max = minValue;
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int value;
            if (unsigned) {
                value = randomValueGenerator.randomUnsignedNumber(bitsSize, maxValue);
            } else {
                value = randomValueGenerator.getNumber(bitsSize);
            }

            min = Math.min(min, value);
            max = Math.max(max, value);
            sum += value;

            numbers[i] = value;
            assertTrue(String.format("value %d out of bounds (%d bits, min %d, max %d)", value, bitsSize, minValue, maxValue), value >= minValue && value <= maxValue);
        }

        double average = sum / count;
        double percentDiff = 50 - ((average - min) / (max - min) * 100);
        assertEquals(0.0, percentDiff, 5.0);

        System.out.println(String.format("count %d, min %d, max %d, average %.0f, percentage diff. (+/-) %.2f%%",
                count, min, max, average, percentDiff));
    }

}
