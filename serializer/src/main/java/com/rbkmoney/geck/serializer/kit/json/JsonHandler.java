package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Base64;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
//TODO replace Writer to JsonNode or similar
//TODO pretty out (without type identifiers into staructure)
public class JsonHandler implements StructHandler<Writer> {

    public static final String KEY = "key";
    public static final String VALUE = "value";
    public static final String ESC_SYMBOL = "@";
    private Writer out;
    private JsonGenerator jGenerator;
    private JsonFactory jfactory = new JsonFactory();

    {
        try {
            init();
        } catch (BadFormatException e) {
            throw new RuntimeException(e);//TODO
        }
    }

    private void init() throws BadFormatException {
        try {
            out = new StringWriter();
            jGenerator = jfactory.createGenerator(out);
        } catch (IOException e) {
            throw new BadFormatException("Unknown error when init", e);
        }
    }

    @Override
    public void beginStruct(int size) throws IOException {
        jGenerator.writeStartObject();
    }

    @Override
    public void endStruct() throws IOException {
        jGenerator.writeEndObject();
    }

    @Override
    public void beginList(int size) throws IOException {
        jGenerator.writeStartArray();
        jGenerator.writeString(StructType.LIST.getKey());
    }

    @Override
    public void endList() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginSet(int size) throws IOException {
        jGenerator.writeStartArray();
        jGenerator.writeString(StructType.SET.getKey());
    }

    @Override
    public void endSet() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginMap(int size) throws IOException {
        jGenerator.writeStartArray();
        jGenerator.writeString(StructType.MAP.getKey());
    }

    @Override
    public void endMap() throws IOException {
        jGenerator.writeEndArray();
    }

    @Override
    public void beginKey() throws IOException {
        jGenerator.writeStartObject();
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
        jGenerator.writeEndObject();
    }

    @Override
    public void name(String name) throws IOException {
        jGenerator.writeFieldName(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        jGenerator.writeBoolean(value);
    }

    @Override
    public void value(String value) throws IOException {
        if (value.startsWith(ESC_SYMBOL)) {
            value = ESC_SYMBOL+value;
        }
        jGenerator.writeString(value);
    }

    @Override
    public void value(double value) throws IOException {
        jGenerator.writeNumber(value);
    }

    @Override
    public void value(long value) throws IOException {
        jGenerator.writeNumber(value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        jGenerator.writeString(ESC_SYMBOL+Base64.getEncoder().encodeToString(value));
    }

    @Override
    public void nullValue() throws IOException {
        jGenerator.writeNull();
    }

    @Override
    public Writer getResult() throws IOException {
        jGenerator.close();
        out.flush();
        Writer resultOut = out;
        init();
        return resultOut;
    }
}
