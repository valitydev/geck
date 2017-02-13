package com.rbkmoney.kebab.kit.tbase;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;

import java.util.Map;

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
        throw new IllegalStateException(String.format("Field '%s' not found", name));
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

    public static boolean checkFields(TBase tBase, boolean requiredOnly) {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tBase.getFieldMetaData();
        boolean check = true;
        if (tBase instanceof TUnion) {
            TUnion tUnion = (TUnion) tBase;
            check &= tUnion.isSet();
            FieldMetaData fieldMetaData = fieldMetaDataMap.get(tUnion.getSetField());
            if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                check &= checkFields((TBase) tUnion.getFieldValue(), requiredOnly);
            }
        } else {
            for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
                FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
                if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED
                        || !requiredOnly) {
                    check &= tBase.isSet(tFieldIdEnum);
                    if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                        check &= checkFields((TBase) tBase.getFieldValue(tFieldIdEnum), requiredOnly);
                    }
                }
            }

        }
        return check;
    }

}
