package com.rbkmoney.kebab;

import org.apache.thrift.protocol.TType;

import java.util.Arrays;

/**
 * Created by tolkonepiu on 12/01/2017.
 */
public enum ThriftType {

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
    STRUCT(TType.STRUCT);

    int code;

    ThriftType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static ThriftType findByCode(int code) {
        return Arrays.stream(values())
                .filter(t -> t.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
