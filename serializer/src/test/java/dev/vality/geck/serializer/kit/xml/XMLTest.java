package dev.vality.geck.serializer.kit.xml;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import dev.vality.geck.serializer.GeckTestUtil;
import dev.vality.geck.serializer.kit.mock.FixedValueGenerator;
import dev.vality.geck.serializer.kit.mock.MockMode;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import dev.vality.geck.serializer.domain.TestObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class XMLTest {
    @Test
    public void testInvoiceBackTransform1() throws IOException {
        InvoicePaymentStarted invoice1 = GeckTestUtil.getInvoicePaymentStarted();
        InvoicePaymentStarted invoice2 =
                new XMLProcessor().process(
                        new TBaseProcessor().process(invoice1, new XMLHandler()),
                        new TBaseHandler<>(InvoicePaymentStarted.class));
        Assert.assertEquals(invoice1, invoice2);
    }
    @Test
    public void xmlKebabTest() throws Exception {
        TestObject invoice = new MockTBaseProcessor(MockMode.ALL, new FixedValueGenerator()).process(new TestObject(), new TBaseHandler<>(TestObject.class));
        XMLHandler handler = new XMLHandler();
        String xml = new TBaseProcessor().process(invoice, handler).toString();
        //test re-use handler
        new TBaseProcessor().process(invoice, handler);
        System.out.println(xml);
    }
}
