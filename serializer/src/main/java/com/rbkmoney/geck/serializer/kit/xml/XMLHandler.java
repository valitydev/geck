package com.rbkmoney.geck.serializer.kit.xml;

import com.rbkmoney.geck.common.stack.ByteStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.EventFlags;
import com.rbkmoney.geck.serializer.kit.StructType;
import static com.rbkmoney.geck.serializer.kit.xml.XMLConstants.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.dom.DOMResult;
import java.io.IOException;
import java.util.Base64;

public class XMLHandler implements StructHandler<DOMResult> {

    private ByteStack stack = new ByteStack();
    private DOMResult result;
    private XMLStreamWriter out;
    private DocumentBuilder documentBuilder;
    private XMLOutputFactory xmlOutputFactory;

    {
        try {
            init();
        } catch (BadFormatException e) {
            throw new RuntimeException(e);//TODO
        }
    }

    private void init() throws BadFormatException {
        try {
            getDocumentBuilder().reset();
            result = new DOMResult(getDocumentBuilder().newDocument());
            out = getXmlOutputFactory().createXMLStreamWriter(result);
            out.writeStartDocument();
            out.writeStartElement(ROOT);
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when init", e);
        }
    }

    private XMLOutputFactory getXmlOutputFactory() {
        if (xmlOutputFactory == null) {
            xmlOutputFactory = XMLOutputFactory.newInstance();
        }
        return xmlOutputFactory;
    }

    private DocumentBuilder getDocumentBuilder() throws BadFormatException {
        if (documentBuilder == null) {
            try {
                documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            } catch (ParserConfigurationException e) {
                throw new BadFormatException("Unknown error when getDocumentBuilder", e);
            }
        }
        return documentBuilder;
    }

    private void writeStartElement(String type) throws BadFormatException {
        try {
            if (!stack.isEmpty()) {
                byte x = stack.peek();
                if (x == EventFlags.startList || x == EventFlags.startSet) {
                    out.writeStartElement(ELEMENT);
                }
            }
            if (type != null) {
                out.writeAttribute(ATTRIBUTE_TYPE, type);
            }
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when writeStartElement", e);
        }
    }

    private void writeValue(String value, String type) throws BadFormatException {
        try {
            writeStartElement(type);
            if (value != null) {
                out.writeCharacters(value.toString());
            }
            out.writeEndElement();
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when writeValue", e);
        }
    }
    private void writeEndElement() throws BadFormatException {
        try {
            out.writeEndElement();
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when writeEndElement", e);
        }
    }

    @Override
    public void beginStruct(int size) throws IOException {
        writeStartElement(StructType.STRUCT.getKey());
        stack.push(EventFlags.startStruct);
    }

    @Override
    public void endStruct() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginList(int size) throws IOException {
        writeStartElement(StructType.LIST.getKey());
        stack.push(EventFlags.startList);
    }

    @Override
    public void endList() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginSet(int size) throws IOException {
        writeStartElement(StructType.SET.getKey());
        stack.push(EventFlags.startSet);
    }

    @Override
    public void endSet() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginMap(int size) throws IOException {
        stack.push(EventFlags.startMap);
        try {
            out.writeAttribute(ATTRIBUTE_TYPE, StructType.MAP.getKey());
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when beginMap", e);
        }
    }

    @Override
    public void endMap() throws IOException {
        stack.pop();
        writeEndElement();
    }

    @Override
    public void beginKey() throws IOException {
        try {
            out.writeStartElement(ELEMENT);
            out.writeAttribute(ATTRIBUTE_TYPE, StructType.MAP_ENTRY.getKey());
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when beginKey", e);
        }
        name(KEY);
    }

    @Override
    public void endKey() throws IOException {
    }

    @Override
    public void beginValue() throws IOException {
        name(VALUE);
    }

    @Override
    public void endValue() throws IOException {
        writeEndElement();
    }

    @Override
    public void name(String name) throws IOException {
        try {
            out.writeStartElement(name);
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when name", e);
        }
    }

    @Override
    public void value(boolean value) throws IOException {
        writeValue(String.valueOf(value), StructType.BOOL.getKey());
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value, StructType.STRING.getKey());
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(String.valueOf(value), StructType.DOUBLE.getKey());
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(String.valueOf(value), StructType.LONG.getKey());
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value), StructType.BYTEARRAY.getKey());
    }

    @Override
    public void nullValue() throws IOException {
        writeValue(null, StructType.NULL.getKey());
    }

    @Override
    public DOMResult getResult() throws IOException {
        try {
            out.writeEndDocument();
            out.flush();
        } catch (XMLStreamException e) {
            throw new BadFormatException("Unknown error when getResult", e);
        }
        DOMResult readyResult = result;
        init();
        return readyResult;
    }
}
