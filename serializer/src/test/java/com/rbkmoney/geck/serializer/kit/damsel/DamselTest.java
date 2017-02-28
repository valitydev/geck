package com.rbkmoney.geck.serializer.kit.damsel;

import com.rbkmoney.damsel.domain.Invoice;
import com.rbkmoney.damsel.payment_processing.Event;
import com.rbkmoney.geck.serializer.GeckUtil;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.Unknown;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by inalarsanukaev on 22.02.17.
 */
public class DamselTest {
    @Test
    public void jsonInvoiceTest() throws JSONException, IOException {
        Invoice invoice = GeckUtil.getInvoice();
        String json = new TBaseProcessor().process(invoice, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }
    @Test
    public void jsonEventTest() throws JSONException, IOException {
        Event event = GeckUtil.getEvent();
        String json = new TBaseProcessor().process(event, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }
    @Test
    public void testInvoiceMsgPack() throws IOException {
        Invoice invoice = GeckUtil.getInvoice();
        byte[] serializedData = new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }
    @Test
    public void testEventMsgPack() throws IOException {
        Event event = GeckUtil.getEvent();
        byte[] serializedData = new TBaseProcessor().process(event, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testTransform() throws IOException {
        Event event1 = GeckUtil.getEvent();
        Event event2 =
                new TBaseProcessor().process(event1,
                        new TBaseHandler<>(Event.class));

        Assert.assertEquals(event1, event2);
    }

    @Test
    public void testUnknownTransform() throws IOException {
        Unknown invoice1 = GeckUtil.getUnknown();
        Unknown invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new TBaseProcessor().process(invoice1, MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(Unknown.class));

        String invoiceS1 = invoice1.toString();
        String invoiceS2 = invoice2.toString();
        System.out.println(invoiceS1);
        System.out.println(invoiceS2);
        //короче тут задница
        for (int i = 0; i < invoiceS1.length(); ++i) {
            if (invoiceS1.charAt(i) != invoiceS2.charAt(i)) {
                throw new RuntimeException(i + " " + invoiceS1.charAt(i) + " " + invoiceS2.charAt(i));
            }
        }
        Assert.assertEquals(invoice1, invoice2);
        Assert.assertEquals(invoiceS1, invoiceS2);
    }

    @Test
    public void testInvoiceBackTransform() throws IOException {
        Invoice invoice1 = GeckUtil.getInvoice();
        Invoice invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new TBaseProcessor().process(invoice1, MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(Invoice.class));

        String invoiceS1 = invoice1.toString();
        String invoiceS2 = invoice2.toString();
        System.out.println(invoiceS1);
        System.out.println(invoiceS2);
        //короче тут задница
        for (int i = 0; i < invoiceS1.length(); ++i) {
            if (invoiceS1.charAt(i) != invoiceS2.charAt(i)) {
                throw new RuntimeException(i + " " + invoiceS1.charAt(i) + " " + invoiceS2.charAt(i));
            }
         }
        Assert.assertEquals(invoice1, invoice2);
        Assert.assertEquals(invoiceS1, invoiceS2);
    }

    @Test
    public void testEventTransform() throws IOException {
        Event event1 = GeckUtil.getEvent();
        Event event2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new TBaseProcessor().process(event1, MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(Event.class));

        String invoiceS1 = event1.toString();
        String invoiceS2 = event2.toString();
        System.out.println(invoiceS1);
        System.out.println(invoiceS2);
        //короче тут задница
        for (int i = 0; i < invoiceS1.length(); ++i) {
            if (invoiceS1.charAt(i) != invoiceS2.charAt(i)) {
                throw new RuntimeException(i + " " + invoiceS1.charAt(i) + " " + invoiceS2.charAt(i));
            }
        }
        Assert.assertEquals(event1, event2);
        Assert.assertEquals(invoiceS1, invoiceS2);
    }
}
