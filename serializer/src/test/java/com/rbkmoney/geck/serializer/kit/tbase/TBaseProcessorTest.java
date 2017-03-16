package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.test.*;
import com.rbkmoney.geck.serializer.handler.HandlerStub;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static com.rbkmoney.geck.serializer.GeckTestUtil.getTestObject;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
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

}
