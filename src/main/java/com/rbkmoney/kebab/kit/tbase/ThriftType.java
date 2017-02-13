package com.rbkmoney.kebab.kit.tbase;

import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.TType;

/**
 * Created by tolkonepiu on 12/01/2017.
 */
public enum ThriftType {

    NULL(-1),
    BOOLEAN(TType.BOOL),
    BYTE(TType.BYTE),
    DOUBLE(TType.DOUBLE),
    SHORT(TType.I16),
    INTEGER(TType.I32),
    LONG(TType.I64),
    STRING(TType.STRING),
    ENUM(TType.ENUM),
    LIST(TType.LIST),
    SET(TType.SET),
    MAP(TType.MAP),
    STRUCT(TType.STRUCT),
    BINARY(21);

    int code;

    ThriftType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ThriftType findByCode(int code) {
        for (ThriftType type : values()) {
            if (type.getCode() == code) {
                return type;
            }
        }
        throw new IllegalArgumentException(String.format("Thrift type not found by code '%d'", code));
    }

    public static ThriftType findByMetaData(FieldValueMetaData valueMetaData) {
        if (valueMetaData.isBinary()) {
            return ThriftType.BINARY;
        }
        return ThriftType.findByCode(valueMetaData.getType());
    }

}
