package dev.vality.geck.serializer.domain;

import org.apache.thrift.TBase;
import org.apache.thrift.TException;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TType;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class RecursiveStruct implements TBase<RecursiveStruct, RecursiveStruct._Fields> {

    private static final Map<_Fields, FieldMetaData> META_DATA_MAP;

    static {
        Map<_Fields, FieldMetaData> metaData = new EnumMap<>(_Fields.class);
        metaData.put(_Fields.NEXT, new FieldMetaData(
                "next",
                TFieldRequirementType.REQUIRED,
                new StructMetaData(TType.STRUCT, RecursiveStruct.class)
        ));
        META_DATA_MAP = Collections.unmodifiableMap(metaData);
        FieldMetaData.addStructMetaDataMap(RecursiveStruct.class, META_DATA_MAP);
    }

    private RecursiveStruct next;

    @Override
    public void clear() {
        next = null;
    }

    @Override
    public RecursiveStruct deepCopy() {
        RecursiveStruct copy = new RecursiveStruct();
        copy.next = next;
        return copy;
    }

    public RecursiveStruct setNext(RecursiveStruct next) {
        this.next = next;
        return this;
    }

    public RecursiveStruct getNext() {
        return next;
    }

    @Override
    public void setFieldValue(_Fields field, Object value) {
        if (field == _Fields.NEXT) {
            next = (RecursiveStruct) value;
            return;
        }
        throw new IllegalArgumentException("Unknown field: " + field);
    }

    @Override
    public Object getFieldValue(_Fields field) {
        if (field == _Fields.NEXT) {
            return next;
        }
        throw new IllegalArgumentException("Unknown field: " + field);
    }

    @Override
    public boolean isSet(_Fields field) {
        return field == _Fields.NEXT && next != null;
    }

    @Override
    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    @Override
    public int compareTo(RecursiveStruct other) {
        return 0;
    }

    @Override
    public void read(TProtocol iprot) throws TException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void write(TProtocol oprot) throws TException {
        throw new UnsupportedOperationException();
    }

    @Override
    public _Fields[] getFields() {
        return _Fields.values();
    }

    @Override
    public Map<_Fields, FieldMetaData> getFieldMetaData() {
        return META_DATA_MAP;
    }

    public enum _Fields implements TFieldIdEnum {
        NEXT((short) 1, "next");

        private final short thriftId;
        private final String fieldName;

        _Fields(short thriftId, String fieldName) {
            this.thriftId = thriftId;
            this.fieldName = fieldName;
        }

        @Override
        public short getThriftFieldId() {
            return thriftId;
        }

        @Override
        public String getFieldName() {
            return fieldName;
        }

        public static _Fields findByThriftId(int fieldId) {
            return fieldId == 1 ? NEXT : null;
        }
    }
}
