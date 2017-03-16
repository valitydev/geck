package com.rbkmoney.geck.serializer.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;

import java.util.Random;

/**
 * Created by inalarsanukaev on 03.03.17.
 */
public class RandomValueGenerator implements ValueGenerator {

    public int randomNumber(int bitSize) {
        return randomNumber(bitSize, new Random());
    }

    public int randomNumber(int bitSize, Random random) {
        int value = random.nextInt();
        for (int n = Integer.SIZE / bitSize; --n > 0; value >>= bitSize) ;
        return value;
    }

    public int randomUnsignedNumber(int bitsSize, int maxValue) {
        return randomUnsignedNumber(bitsSize, maxValue, new Random());
    }

    private int randomUnsignedNumber(int bitsSize, int maxValue, Random random) {
        return randomNumber(bitsSize, random) & maxValue;
    }

    @Override
    public byte getByte() {
        return (byte) randomNumber(Byte.SIZE);
    }

    @Override
    public short getShort() {
        return (short) randomNumber(Short.SIZE);
    }

    public int getNumber(int bitSize) {
        return randomNumber(bitSize, new Random());
    }

    @Override
    public int getInt() {
        return new Random().nextInt();
    }

    @Override
    public int getInt(int bound) {
        return new Random().nextInt(bound);
    }

    @Override
    public long getLong() {
        return new Random().nextLong();
    }

    @Override
    public double getDouble() {
        return new Random().nextDouble();
    }

    @Override
    public boolean getBoolean() {
        return new Random().nextBoolean();
    }

    @Override
    public byte[] getByteArray(int maxSize) {
        Random random = new Random();
        int size = random.nextInt(maxSize);
        byte[] byteArray = new byte[size];
        random.nextBytes(byteArray);
        return byteArray;
    }

    @Override
    public String getString(int maxLength) {
        int start = ' ';
        int end = 'z' + 1;
        int gap = end - start;

        Random random = new Random();
        int size = random.nextInt(maxLength);
        char[] value = new char[size];
        for (int i = 0; i < size; i++) {
            value[i] = (char) (random.nextInt(gap) + start);
        }
        return new String(value);
    }

    @Override
    public TEnum getTEnum(Class<? extends TEnum> enumClass) {
        if (enumClass.isEnum()) {
            TEnum[] enums = enumClass.getEnumConstants();
            int element = new Random().nextInt(enums.length);
            return enums[element];
        }
        return null;
    }

    @Override
    public TFieldIdEnum getField(TBase tBase) {
        TFieldIdEnum[] fields = tBase.getFields();
        int element = new Random().nextInt(fields.length);
        return fields[element];
    }
}
