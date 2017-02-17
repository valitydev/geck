package com.rbkmoney.geck.serializer.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;

import java.util.Random;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class RandomUtil {

    public static byte randomByte() {
        return (byte) randomNumber(Byte.SIZE);
    }

    public static short randomShort() {
        return (short) randomNumber(Short.SIZE);
    }

    public static int randomUnsignedNumber(int bitsSize, int maxValue) {
        return randomUnsignedNumber(bitsSize, maxValue, new Random());
    }

    public static int randomUnsignedNumber(int bitsSize, int maxValue, Random random) {
        return randomNumber(bitsSize, random) & maxValue;
    }

    public static int randomNumber(int bitSize) {
        return randomNumber(bitSize, new Random());
    }

    public static int randomNumber(int bitSize, Random random) {
        int value = random.nextInt();
        for (int n = Integer.SIZE / bitSize; --n > 0; value >>= bitSize) ;
        return value;
    }

    public static int randomInt() {
        return new Random().nextInt();
    }

    public static int randomInt(int bound) {
        return new Random().nextInt(bound);
    }

    public static long randomLong() {
        return new Random().nextLong();
    }

    public static double randomDouble() {
        return new Random().nextDouble();
    }

    public static boolean randomBoolean() {
        return new Random().nextBoolean();
    }

    public static byte[] randomByteArray(int maxSize) {
        Random random = new Random();
        int size = random.nextInt(maxSize);
        byte[] byteArray = new byte[size];
        random.nextBytes(byteArray);
        return byteArray;
    }

    public static String randomString(int maxLength) {
        Random random = new Random();
        int size = random.nextInt(maxLength);
        char[] value = new char[size];
        for (int i = 0; i < size; i++) {
            value[i] = (char) randomUnsignedNumber(Character.SIZE, Character.MAX_VALUE, random);
        }
        return new String(value);
    }

    public static TEnum randomTEnum(Class<? extends TEnum> enumClass) {
        if (enumClass.isEnum()) {
            TEnum[] enums = enumClass.getEnumConstants();
            int element = new Random().nextInt(enums.length);
            return enums[element];
        }
        return null;
    }

    public static TFieldIdEnum randomField(TBase tBase) {
        TFieldIdEnum[] fields = tBase.getFields();
        int element = new Random().nextInt(fields.length);
        return fields[element];
    }

}
