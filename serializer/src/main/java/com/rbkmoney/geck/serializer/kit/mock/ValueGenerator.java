package com.rbkmoney.geck.serializer.kit.mock;

import org.apache.thrift.TBase;
import org.apache.thrift.TEnum;
import org.apache.thrift.TFieldIdEnum;

/**
 * Created by inalarsanukaev on 03.03.17.
 */
public interface ValueGenerator {
    byte getByte();

    short getShort();

    int getInt();

    int getInt(int bound);

    long getLong();

    double getDouble();

    boolean getBoolean();

    byte[] getByteArray(int maxSize);

    String getString(int maxLength);

    TEnum getTEnum(Class<? extends TEnum> enumClass);

    TFieldIdEnum getField(TBase tBase);

}
