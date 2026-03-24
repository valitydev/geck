package dev.vality.geck.serializer.kit.mock;

import dev.vality.geck.serializer.domain.BinaryTest;
import dev.vality.geck.serializer.domain.TestObject;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.ThriftType;
import org.apache.thrift.TException;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.protocol.TField;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TStruct;
import org.apache.thrift.protocol.TType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MockTBaseProcessorTest {

    @Test
    public void requiredFieldsOnlyTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.REQUIRED_ONLY).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(checkFields(testObject, true));
        assertFalse(checkFields(testObject, false));
    }

    @Test
    public void binaryTest() throws IOException {
        BinaryTest binaryTest = new BinaryTest();
        binaryTest = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(binaryTest, new TBaseHandler<>(BinaryTest.class));
        assertNotNull(binaryTest.getData());
        assertNotNull(binaryTest.getDataInList());
        binaryTest.getDataInList().stream().forEach(Assert::assertNotNull);
        assertNotNull(binaryTest.getDataInSet());
        binaryTest.getDataInSet().stream().forEach(Assert::assertNotNull);
        assertNotNull(binaryTest.getDataInMap());
        binaryTest.getDataInMap().keySet().stream().forEach(Assert::assertNotNull);
        binaryTest.getDataInMap().values().stream().forEach(Assert::assertNotNull);
    }

    @Test
    public void fieldHandlerTest() throws IOException {
        String testValue = "KEK";

        MockTBaseProcessor processor = new MockTBaseProcessor();
        processor.addFieldHandler(handler -> handler.value(testValue), "description", "another_string");
        TestObject testObject = new TestObject();
        testObject = processor.process(testObject, new TBaseHandler<>(TestObject.class));
        assertEquals(testValue, testObject.getDescription());
        assertEquals(testValue, testObject.getAnotherString());
    }

    @Test
    public void allFieldsTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.ALL).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(checkFields(testObject, false));
    }

    @Test
    public void recursiveSchemaShouldFailFastInsteadOfInfiniteMockGeneration() {
        assertThatThrownBy(() -> new MockTBaseProcessor(MockMode.ALL)
                .process(new RecursiveStruct(), new TBaseHandler<>(RecursiveStruct.class)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Recursive thrift type detected");
    }


    public boolean checkFields(TBase tBase, boolean requiredOnly) {
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

    public static class RecursiveStruct implements TBase<RecursiveStruct, RecursiveStruct._Fields> {
        private static final TStruct STRUCT_DESC = new TStruct("RecursiveStruct");
        private static final TField NEXT_FIELD_DESC = new TField("next", TType.STRUCT, (short) 1);
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

        public RecursiveStruct next;

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

        @Override
        public void setFieldValue(_Fields field, Object value) {
            if (field == _Fields.NEXT) {
                next = (RecursiveStruct) value;
            }
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
        public boolean equals(Object other) {
            return this == other;
        }

        @Override
        public int hashCode() {
            return System.identityHashCode(this);
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
        public String toString() {
            return STRUCT_DESC.name;
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

}
