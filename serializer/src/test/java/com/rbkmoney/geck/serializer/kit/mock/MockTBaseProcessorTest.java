package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseUtil;
import com.rbkmoney.geck.serializer.test.TestObject;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

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
        TestCase.assertTrue(TBaseUtil.checkFields(testObject, true));
        assertFalse(TBaseUtil.checkFields(testObject, false));
    }

    @Test
    public void allFieldsTest() throws IOException {
        TestObject testObject = new TestObject();
        testObject = new MockTBaseProcessor(MockMode.ALL).process(testObject, new TBaseHandler<>(TestObject.class));
        assertTrue(TBaseUtil.checkFields(testObject, false));
    }

}
