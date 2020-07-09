package com.rbkmoney.geck.serializer.kit.json;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckTestUtil;
import com.rbkmoney.geck.serializer.kit.mock.FixedValueGenerator;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.domain.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;


public class JsonTest {

    @Test
    public void testInvoiceBackTransform() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new JsonProcessor().process(
                        new TBaseProcessor().process(invoice1, new JsonHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new JsonProcessor().process(
                                new TBaseProcessor().process(
                                        invoice1,
                                        new JsonHandler()),
                                MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void jsonKebabTest() throws Exception {
        TestObject testObject = new MockTBaseProcessor().process(new TestObject(), new TBaseHandler<>( TestObject.class));
        JsonHandler handler = new JsonHandler();
        String json1 = new TBaseProcessor().process(testObject, handler).toString();
        System.out.println(json1);
        //test re-use handler
        String json2 = new TBaseProcessor().process(testObject, handler).toString();
        System.out.println(json2);
        Assert.assertEquals(json1, json2);
    }

    @Test
    public void testPretty() throws IOException {
        TestObject testObject = new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new TestObject(), new TBaseHandler<>( TestObject.class));
        JsonHandler handler = JsonHandler.newPrettyJsonInstance();
        String json1 = new TBaseProcessor().process(testObject, handler).toString();
        System.out.println(json1);
    }
}
