package com.rbkmoney.geck.serializer.kit.damsel;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted;
import com.rbkmoney.geck.serializer.GeckUtil;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

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
        InvoicePaymentStarted invoice = GeckUtil.getInvoicePaymentStarted();
        byte[] serializedData = new TBaseProcessor().process(invoice, MsgPackHandler.newBufferedInstance(true));
        byte[] doubleSerialized = MsgPackProcessor.newBinaryInstance().process(serializedData, MsgPackHandler.newBufferedInstance(true));
        Assert.assertArrayEquals(serializedData, doubleSerialized);
    }

    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckUtil.getInvoicePaymentStarted();
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
        InvoicePaymentStarted invoice1 = GeckUtil.getInvoicePaymentStarted();
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
    public void test() throws IOException {
        com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted invoice_v136 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v136.payment_processing.InvoicePaymentStarted.class));
        String json_v136 = new TBaseProcessor().process(invoice_v136, new JsonHandler()).toString();
        System.out.println(json_v136);
        Object inputJSON_v136 = JsonUtils.jsonToObject(json_v136);

        List chainrSpecJSON = JsonUtils.jsonToList(this.getClass().getResourceAsStream( "/spec_invoice.json" ));
        Chainr chainr = Chainr.fromSpec( chainrSpecJSON );

        Object transformedOutput = chainr.transform(inputJSON_v136);
        String transformedInvoice = JsonUtils.toJsonString( transformedOutput );
        System.out.println(transformedInvoice);

        com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted invoice_v133 =
                new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted(), new TBaseHandler<>(com.rbkmoney.damsel_v133.payment_processing.InvoicePaymentStarted.class));
        String json_v133 = new TBaseProcessor().process(invoice_v133, new JsonHandler()).toString();
        Object inputJSON_v133 = JsonUtils.jsonToObject(json_v133);
        System.out.println(json_v133);

        Assert.assertEquals(inputJSON_v133,transformedOutput);
    }

    /**
     * Создает сет на основе мэпы, рекурсивно, с учетом вложенности
     * TODO надо сделать для массивов корректную обработку
     * @param map
     * @param set
     */
    private void fillSetFromLinkedHashMap(LinkedHashMap map, Set set){
        for (Object k1 : map.keySet()) {
            Object o1 = map.get(k1);
           // System.out.println(o1.getClass());
            if (o1 instanceof LinkedHashMap) {
                Set dest = new HashSet();
                fillSetFromLinkedHashMap((LinkedHashMap)o1, dest);
                addAll((String)k1, set, dest);
            } else if (o1 instanceof ArrayList){
                //TODO надо делать
                set.add(k1);
            } else {
                set.add(k1);
            }
        }
    }

    private void addAll(String k1, Set source, Set dest){
        for (Object o : dest) {
            source.add(k1 + "."+o);
        }
    }

}
