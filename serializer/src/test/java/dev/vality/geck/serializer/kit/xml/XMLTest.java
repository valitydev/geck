package dev.vality.geck.serializer.kit.xml;

import com.rbkmoney.damsel.v130.payment_processing.InvoicePaymentStarted;
import dev.vality.geck.serializer.GeckTestUtil;
import dev.vality.geck.serializer.exception.BadFormatException;
import dev.vality.geck.serializer.handler.HandlerStub;
import dev.vality.geck.serializer.kit.StructType;
import dev.vality.geck.serializer.kit.mock.FixedValueGenerator;
import dev.vality.geck.serializer.kit.mock.MockMode;
import dev.vality.geck.serializer.kit.mock.MockTBaseProcessor;
import dev.vality.geck.serializer.kit.tbase.TBaseHandler;
import dev.vality.geck.serializer.kit.tbase.TBaseProcessor;
import dev.vality.geck.serializer.domain.TestObject;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @Test
    public void unknownNodeTypeShouldFailFast() throws Exception {
        Document document = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .newDocument();
        Element root = document.createElement(XMLConstants.ROOT);
        root.setAttribute(XMLConstants.ATTRIBUTE_TYPE, StructType.STRUCT.getKey());
        document.appendChild(root);
        Element child = document.createElement("field");
        child.setAttribute(XMLConstants.ATTRIBUTE_TYPE, "unsupported");
        root.appendChild(child);

        DOMResult domResult = new DOMResult(document);

        assertThatThrownBy(() -> new XMLProcessor().process(domResult, new HandlerStub()))
                .isInstanceOf(BadFormatException.class)
                .hasMessageContaining("Attribute 'type' must not be null");
    }
}
