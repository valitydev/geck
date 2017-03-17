package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.ThriftType;
import com.rbkmoney.geck.serializer.test.TestObject;
import junit.framework.TestCase;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.FieldMetaData;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by tolkonepiu on 12/02/2017.
 */
public class MockTBaseProcessorTest {

    @Test
    public void requiredFieldsOnlyTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.REQUIRED_ONLY).process(testObject, new TBaseHandler<>(TestObject.class));
        TestCase.assertTrue(checkFields(testObject, true));
        assertFalse(checkFields(testObject, false));
    }

    @Test
    public void allFieldsTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.ALL).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(checkFields(testObject, false));
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

}
