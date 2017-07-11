package com.rbkmoney.geck.common.util;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;

import java.util.Map;
import java.util.Objects;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseUtil {

    public static TFieldIdEnum getField(String name, TBase tBase) {
        for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
            if (tFieldIdEnum.getFieldName().equals(name)) {
                return tFieldIdEnum;
            }
        }
        return null;
    }

    public static TFieldIdEnum getFieldById(short id, TBase tBase) {
        for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
            if (tFieldIdEnum.getThriftFieldId() == id) {
                return tFieldIdEnum;
            }
        }
        return null;
    }

    public static FieldMetaData getMetaData(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tBase.getFieldMetaData();
        return fieldMetaDataMap.get(tFieldIdEnum);
    }

    public static FieldValueMetaData getValueMetaData(TFieldIdEnum tFieldIdEnum, TBase tBase) {
        return getMetaData(tFieldIdEnum, tBase).valueMetaData;
    }

    public static int getSetFieldsCount(TBase value) {
        int size = 0;
        for (TFieldIdEnum tFieldIdEnum : value.getFields()) {
            if (value.isSet(tFieldIdEnum)) {
                size++;
            }
        }
        return size;
    }

    public static <T extends Enum<T>> T unionFieldToEnum(TUnion union, Class<T> enumType) {
        Objects.requireNonNull(union, "Union must be set");
        return Enum.valueOf(enumType, union.getSetField().getFieldName());
    }

}
