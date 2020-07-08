package com.rbkmoney.geck.serializer.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;


public class FixedValueGenerator implements ValueGenerator {
    @Override
    public byte getByte() {
        return 0;
    }

    @Override
    public short getShort() {
        return 1;
    }

    @Override
    public int getInt() {
        return 3;
    }

    @Override
    public int getInt(int bound) {
        return 4;
    }

    @Override
    public long getLong() {
        return 5;
    }

    @Override
    public double getDouble() {
        return 6;
    }

    @Override
    public boolean getBoolean() {
        return false;
    }

    @Override
    public byte[] getByteArray(int maxSize) {
        return new byte[maxSize];
    }

    @Override
    public String getString(int maxLength) {
        return "kek";
    }

    @Override
    public TEnum getTEnum(Class<? extends TEnum> enumClass) {
        if (enumClass.isEnum()) {
            TEnum[] enums = enumClass.getEnumConstants();
            int element = enums.length;
            return enums[element-1];
        }
        return null;
    }

    @Override
    public TFieldIdEnum getField(TBase tBase) {
        TFieldIdEnum[] fields = tBase.getFields();
        int element = fields.length;
        return fields[element-1];
    }
}
