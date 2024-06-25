package dev.vality.geck.serializer.kit.tbase;

import org.apache.thrift.meta_data.EnumMetaData;
import org.apache.thrift.meta_data.FieldValueMetaData;
import org.apache.thrift.protocol.TType;

public enum ThriftType {

    BOOLEAN(TType.BOOL, 17),
    BYTE(TType.BYTE, 17),
    DOUBLE(TType.DOUBLE, 17),
    SHORT(TType.I16, 17),
    INTEGER(TType.I32, 17),
    LONG(TType.I64, 17),
    STRING(TType.STRING, 17),
    ENUM(TType.ENUM, 17),
    OLD_ENUM((byte) 16, 9),
    UUID(TType.UUID, 17),
    LIST(TType.LIST, 17),
    SET(TType.SET, 17),
    MAP(TType.MAP, 17),
    STRUCT(TType.STRUCT, 17),
    BINARY(21, 17);

    private final int code;
    private final int ignoredThriftVersion;

    ThriftType(int code, int ignoredThriftVersion) {
        this.code = code;
        this.ignoredThriftVersion = ignoredThriftVersion;
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
        if (valueMetaData instanceof EnumMetaData) {
            if (valueMetaData.getType() == ThriftType.ENUM.getCode()) {
                return ThriftType.ENUM;
            }
            if (valueMetaData.getType() == ThriftType.OLD_ENUM.getCode()) {
                return ThriftType.OLD_ENUM;
            }
        }
        return ThriftType.findByCode(valueMetaData.getType());
    }

}
