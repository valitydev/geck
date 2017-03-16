package com.rbkmoney.geck.serializer.kit.damsel;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.damsel_v133.domain.Invoice;
import com.rbkmoney.damsel_v133.payment_processing.*;
import com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckTestUtil;
import com.rbkmoney.geck.serializer.kit.json.JsonHandler;
import com.rbkmoney.geck.serializer.kit.mock.FixedValueGenerator;
import com.rbkmoney.geck.serializer.kit.mock.MockMode;
import com.rbkmoney.geck.serializer.kit.mock.MockTBaseProcessor;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackHandler;
import com.rbkmoney.geck.serializer.kit.msgpack.MsgPackProcessor;
import com.rbkmoney.geck.serializer.kit.object.ObjectHandler;
import com.rbkmoney.geck.serializer.kit.object.ObjectProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.test.MapListTest;
import com.rbkmoney.geck.serializer.test.Unknown;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by inalarsanukaev on 22.02.17.
 */
public class DamselTest {
    @Test
    public void jsonInvoiceTest() throws JSONException, IOException {
        //com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted invoice = new MockTBaseProcessor().process(new com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted.class));
        com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted invoice = new MockTBaseProcessor().process(new com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted.class));
        String json = new TBaseProcessor().process(invoice, new JsonHandler()).toString();
        System.out.println(json);
        new JSONObject(json);
    }

    @Test
    public void testInvoiceMsgPack() throws IOException {
        InvoicePaymentStarted invoice = GeckTestUtil.getInvoicePaymentStarted();
        byte[] serializedData = new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new ObjectProcessor().process(
                        MsgPackProcessor.newBinaryInstance().process(
                                new TBaseProcessor().process(
                                        invoice1,
                                        MsgPackHandler.newBufferedInstance(true)),
                                new ObjectHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void testInvoiceBackTransform2() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                MsgPackProcessor.newBinaryInstance().process(
                        new ObjectProcessor().process(
                                new TBaseProcessor().process(
                                        invoice1,
                                        new ObjectHandler()),
                                MsgPackHandler.newBufferedInstance(true)),
                        new TBaseHandler<>(InvoicePaymentStarted.class)
                );
        Assert.assertEquals(invoice1, invoice2);
    }

    @Test
    public void pathMatchingTest() throws IOException {
        List<com.rbkmoney.damsel_v133.payment_processing.Event> eventV133List = new ArrayList<>();

        //generated 10 "invoice created" events
        addV133InvoiceEvents(eventV133List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_created(new InvoiceCreated(new Invoice()))));

        //generated 10 "invoice payment started" events
        addV133InvoiceEvents(eventV133List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_payment_event(InvoicePaymentEvent.invoice_payment_started(new com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted()))));

        //generated 10 "invoice status changed" events
        addV133InvoiceEvents(eventV133List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_status_changed(new InvoiceStatusChanged())));

        //generated 10 "invoice payment status changed" events
        addV133InvoiceEvents(eventV133List, 10, EventPayload.invoice_event(InvoiceEvent.invoice_payment_event(InvoicePaymentEvent.invoice_payment_status_changed(new com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStatusChanged()))));


        for (com.rbkmoney.damsel_v133.payment_processing.Event event133Thrift : eventV133List) {
            Object event133Jolt = new TBaseProcessor().process(event133Thrift, new ObjectHandler());
            List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream("/spec_event.json"));
            Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
            Object event136Jolt = chainr.transform(event133Jolt);
            try {
                new ObjectProcessor().process(event136Jolt, new TBaseHandler<>(com.rbkmoney.damsel_v136.payment_processing.Event.class));
            } catch (IOException ex) {
                System.out.println("v133:\n" + JsonUtils.toPrettyJsonString(event133Jolt));
                System.out.println("v136:\n" + JsonUtils.toPrettyJsonString(event136Jolt));
                throw ex;
            }
        }
    }

    private void addV133InvoiceEvents(List<com.rbkmoney.damsel_v133.payment_processing.Event> events, int count, EventPayload eventPayload) throws IOException {
        for (int i = 0; i < count; i++) {
            com.rbkmoney.damsel_v133.payment_processing.Event event133 = new com.rbkmoney.damsel_v133.payment_processing.Event();
            event133.setPayload(eventPayload);

            events.add(new MockTBaseProcessor(MockMode.ALL)
                    .process(event133,
                            new TBaseHandler<>(com.rbkmoney.damsel_v133.payment_processing.Event.class)));
        }
    }

    @Test
    public void test() throws IOException {
        com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted invoice_v136 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted.class));
        String json_v136 = new TBaseProcessor().process(invoice_v136, new JsonHandler()).toString();
        System.out.println(json_v136);
        Object inputJSON_v136 = JsonUtils.jsonToObject(json_v136);

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream("/spec_invoice.json"));
        Chainr chainr = Chainr.fromSpec(chainrSpecJSON);

        Object transformedOutput = chainr.transform(inputJSON_v136);
        String transformedInvoice = JsonUtils.toJsonString(transformedOutput);
        System.out.println(transformedInvoice);

        com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted invoice_v133 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted.class));
        String json_v133 = new TBaseProcessor().process(invoice_v133, new JsonHandler()).toString();
        Object inputJSON_v133 = JsonUtils.jsonToObject(json_v133);
        System.out.println(json_v133);

        Assert.assertEquals(inputJSON_v133, transformedOutput);
    }

}
