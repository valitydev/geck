package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.test.Kek;
import com.rbkmoney.geck.serializer.test.Status;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class RandomUtilTest {

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
        TEnum tEnum = RandomUtil.randomTEnum(Kek.class);
        Enum.valueOf(Kek.class, tEnum.toString());
    }

    @Test
    public void generateFieldTest() {
        Status status = new Status();
        TFieldIdEnum field = RandomUtil.randomField(status);
        assertNotEquals(-1, Arrays.binarySearch(status.getFields(), field));
    }

    public void generateNumberTest(int count, int bitsSize, int minValue, int maxValue, boolean unsigned) {
        int[] numbers = new int[count];

        int min = maxValue;
        int max = minValue;
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int value;
            if (unsigned) {
                value = RandomUtil.randomUnsignedNumber(bitsSize, maxValue);
            } else {
                value = RandomUtil.randomNumber(bitsSize);
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
