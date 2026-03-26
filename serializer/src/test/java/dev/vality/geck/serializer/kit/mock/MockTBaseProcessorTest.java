package dev.vality.geck.serializer.kit.mock;

import dev.vality.geck.serializer.domain.BinaryTest;
import dev.vality.geck.serializer.domain.RecursiveStruct;
import dev.vality.geck.serializer.domain.TestObject;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.ThriftType;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.FieldMetaData;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MockTBaseProcessorTest {

    @Test
    public void requiredFieldsOnlyTest() throws IOException {
        TestObject source = new TestObject();
        TestObject result = new MockTBaseProcessor(MockMode.REQUIRED_ONLY).process(source, new TBaseHandler<>(TestObject.class));
        assertTrue(hasExpectedFields(result, true));
        assertFalse(hasExpectedFields(result, false));
    }

    @Test
    public void binaryTest() throws IOException {
        BinaryTest source = new BinaryTest();
        BinaryTest result = new MockTBaseProcessor(MockMode.REQUIRED_ONLY)
                .process(source, new TBaseHandler<>(BinaryTest.class));
        assertNotNull(result.getData());
        assertNotNull(result.getDataInList());
        result.getDataInList().forEach(Assert::assertNotNull);
        assertNotNull(result.getDataInSet());
        result.getDataInSet().forEach(Assert::assertNotNull);
        assertNotNull(result.getDataInMap());
        result.getDataInMap().keySet().forEach(Assert::assertNotNull);
        result.getDataInMap().values().forEach(Assert::assertNotNull);
    }

    @Test
    public void fieldHandlerTest() throws IOException {
        String testValue = "KEK";

        MockTBaseProcessor processor = new MockTBaseProcessor();
        processor.addFieldHandler(handler -> handler.value(testValue), "description", "another_string");
        TestObject source = new TestObject();
        TestObject result = processor.process(source, new TBaseHandler<>(TestObject.class));
        assertEquals(testValue, result.getDescription());
        assertEquals(testValue, result.getAnotherString());
    }

    @Test
    public void allFieldsTest() throws IOException {
        TestObject source = new TestObject();
        TestObject result = new MockTBaseProcessor(MockMode.ALL).process(source, new TBaseHandler<>(TestObject.class));
        assertTrue(hasExpectedFields(result, false));
    }

    @Test
    public void recursiveSchemaShouldFailFastInsteadOfInfiniteMockGeneration() {
        assertThatThrownBy(() -> new MockTBaseProcessor(MockMode.ALL)
                .process(new RecursiveStruct(), new TBaseHandler<>(RecursiveStruct.class)))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Recursive thrift type detected");
    }

    private static boolean hasExpectedFields(TBase tBase, boolean requiredOnly) {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tBase.getFieldMetaData();
        boolean fieldsAreSet = true;
        if (tBase instanceof TUnion) {
            TUnion tUnion = (TUnion) tBase;
            fieldsAreSet &= tUnion.isSet();
            FieldMetaData fieldMetaData = fieldMetaDataMap.get(tUnion.getSetField());
            if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                fieldsAreSet &= hasExpectedFields((TBase) tUnion.getFieldValue(), requiredOnly);
            }
        } else {
            for (TFieldIdEnum tFieldIdEnum : tBase.getFields()) {
                FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
                if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED
                        || !requiredOnly) {
                    fieldsAreSet &= tBase.isSet(tFieldIdEnum);
                    if (ThriftType.findByMetaData(fieldMetaData.valueMetaData) == ThriftType.STRUCT) {
                        fieldsAreSet &= hasExpectedFields((TBase) tBase.getFieldValue(tFieldIdEnum), requiredOnly);
                    }
                }
            }

        }
        return fieldsAreSet;
    }
}
