package dev.vality.geck.serializer.kit.tbase;

import dev.vality.geck.serializer.domain.*;
import dev.vality.geck.serializer.handler.HandlerStub;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.StructHandler;
import org.junit.Assert;
import org.junit.Test;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.FieldMetaData;
import org.apache.thrift.meta_data.StructMetaData;
import org.apache.thrift.protocol.TType;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.List;

import static dev.vality.geck.serializer.GeckTestUtil.getTestObject;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TBaseProcessorTest {

    @Test
    public void tBaseTest() throws IOException {
        TestObject testObject1 = getTestObject();
        TestObject testObject2 = new TBaseProcessor().process(testObject1, new TBaseHandler<>(TestObject.class));
        Assert.assertEquals(testObject1, testObject2);
    }

    @Test
    public void thriftSetTest() throws IOException {
        SetTest setTest1 = new MockTBaseProcessor().process(new SetTest(), new TBaseHandler<>(SetTest.class));
        SetTest setTest2 = new TBaseProcessor().process(setTest1, new TBaseHandler<>(SetTest.class));

        Assert.assertEquals(setTest1, setTest2);
    }

    @Test
    public void thriftMapTest() throws IOException {
        MapTest mapTest1 = new MockTBaseProcessor().process(new MapTest(), new TBaseHandler<>(MapTest.class));
        MapTest mapTest2 = new TBaseProcessor().process(mapTest1, new TBaseHandler<>(MapTest.class));

        Assert.assertEquals(mapTest1, mapTest2);
    }

    @Test
    public void tUnionProcessorTest() throws IOException {
        TBaseProcessor tBaseProcessor = new TBaseProcessor();

        TUnionTest tUnionTest = new TUnionTest();
        tUnionTest.setStatus(new Status());

        //unset tUnion
        assertThatThrownBy(() -> tBaseProcessor.process(tUnionTest, new HandlerStub()))
                .hasMessage("one of fields in union 'Status' must be set");

        tUnionTest.setStatus(Status.unknown(new Unknown()));

        //unset required field
        assertThatThrownBy(() -> tBaseProcessor.process(tUnionTest, new HandlerStub()))
                .hasMessage("field 'description' is required and must not be null");

    }

    @Test
    public void binaryUnknownTypeDataTest() throws IOException {
        BinaryTest binaryTest = new BinaryTest();
        List byteArrayInList = new ArrayList<>();

        byteArrayInList.add(5);

        binaryTest.setFieldValue(BinaryTest._Fields.DATA_IN_LIST, byteArrayInList);
        assertThatThrownBy(() -> new TBaseProcessor(false)
                .process(binaryTest, new TBaseHandler(BinaryTest.class, TBaseHandler.Mode.PREFER_NAME, false)))
                .hasMessage("Unknown binary type, type='java.lang.Integer'");
    }

    @Test
    public void binaryByteBufferShouldRespectPositionAndLimit() throws IOException {
        BinaryTest binaryTest = new BinaryTest();
        ByteBuffer buffer = ByteBuffer.wrap(new byte[]{9, 1, 2, 8});
        buffer.position(1);
        buffer.limit(3);

        binaryTest.setData(buffer);
        binaryTest.setDataInList(Collections.emptyList());
        binaryTest.setDataInSet(Collections.emptySet());
        binaryTest.setDataInMap(Collections.emptyMap());

        BinaryCapturingHandler handler = new BinaryCapturingHandler();
        new TBaseProcessor().process(binaryTest, handler);

        Assert.assertArrayEquals(new byte[]{1, 2}, handler.binaryValue);
    }

    @Test
    public void cyclicThriftGraphShouldFailFastInsteadOfStackOverflow() {
        TBase first = Mockito.mock(TBase.class);
        TBase second = Mockito.mock(TBase.class);
        TFieldIdEnum firstField = mockStructField((short) 1, "next");
        TFieldIdEnum secondField = mockStructField((short) 1, "next");

        Mockito.when(first.getFields()).thenReturn(new TFieldIdEnum[]{firstField});
        Mockito.when(first.getFieldMetaData()).thenReturn(singleStructFieldMeta(firstField, "next"));
        Mockito.when(first.isSet(firstField)).thenReturn(true);
        Mockito.when(first.getFieldValue(firstField)).thenReturn(second);

        Mockito.when(second.getFields()).thenReturn(new TFieldIdEnum[]{secondField});
        Mockito.when(second.getFieldMetaData()).thenReturn(singleStructFieldMeta(secondField, "next"));
        Mockito.when(second.isSet(secondField)).thenReturn(true);
        Mockito.when(second.getFieldValue(secondField)).thenReturn(first);

        assertThatThrownBy(() -> new TBaseProcessor().process(first, new HandlerStub()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Cyclic reference detected");
    }

    private static TFieldIdEnum mockStructField(short id, String name) {
        TFieldIdEnum field = Mockito.mock(TFieldIdEnum.class);
        Mockito.when(field.getThriftFieldId()).thenReturn(id);
        Mockito.when(field.getFieldName()).thenReturn(name);
        return field;
    }

    private static Map<TFieldIdEnum, FieldMetaData> singleStructFieldMeta(TFieldIdEnum field, String fieldName) {
        return Collections.singletonMap(
                field,
                new FieldMetaData(
                        fieldName,
                        TFieldRequirementType.DEFAULT,
                        new StructMetaData(TType.STRUCT, TestObject.class)
                )
        );
    }

    private static class BinaryCapturingHandler implements StructHandler<byte[]> {
        private byte[] binaryValue;

        @Override
        public void beginStruct(int size) {
        }

        @Override
        public void endStruct() {
        }

        @Override
        public void beginList(int size) {
        }

        @Override
        public void endList() {
        }

        @Override
        public void beginSet(int size) {
        }

        @Override
        public void endSet() {
        }

        @Override
        public void beginMap(int size) {
        }

        @Override
        public void endMap() {
        }

        @Override
        public void beginKey() {
        }

        @Override
        public void endKey() {
        }

        @Override
        public void beginValue() {
        }

        @Override
        public void endValue() {
        }

        @Override
        public void name(String name) {
        }

        @Override
        public void value(boolean value) {
        }

        @Override
        public void value(String value) {
        }

        @Override
        public void value(double value) {
        }

        @Override
        public void value(long value) {
        }

        @Override
        public void value(byte[] value) {
            if (binaryValue == null) {
                binaryValue = value;
            }
        }

        @Override
        public void nullValue() {
        }

        @Override
        public byte[] getResult() {
            return binaryValue;
        }
    }

}
