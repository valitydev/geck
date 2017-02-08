package com.rbkmoney.kebab.handler;

import com.rbkmoney.kebab.ByteStack;
import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.ThriftType;
import com.rbkmoney.kebab.exception.BadFormatException;

import java.io.IOException;
import java.io.Writer;
import java.util.Base64;
import java.util.Objects;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class JsonStructHandler implements StructHandler<String> {

    static final byte EMPTY_STRUCT = 1;

    static final byte NONEMPTY_STRUCT = 2;

    static final byte EMPTY_LIST = 3;

    static final byte NONEMPTY_LIST = 4;

    static final byte EMPTY_MAP = 5;

    static final byte NONEMPTY_MAP = 6;

    static final byte JSON_NAME = 7;

    static final byte EMPTY_DOCUMENT = 8;

    static final byte NONEMPTY_DOCUMENT = 9;

    private final Writer out;

    private final boolean pretty;

    private ByteStack stack = new ByteStack();

    {
        stack.push(EMPTY_DOCUMENT);
    }

    public JsonStructHandler(Writer out) {
        this(out, false);
    }

    public JsonStructHandler(Writer out, boolean pretty) {
        Objects.requireNonNull(out, "Writer must not be null");
        this.out = out;
        this.pretty = pretty;
    }

    private void writeBeforeValue(ThriftType type) throws IOException {
        writeBegin(EMPTY_STRUCT, '{');
        name("type");
        beforeValue();
        writeString(type.toString());
        name("value");

    }

    private void writeAfterValue() throws IOException {
        writeEnd(EMPTY_STRUCT, NONEMPTY_STRUCT, '}');
    }

    private void newline() throws IOException {
        if (pretty) {
            out.write("\n");
            for (int i = 0; i < stack.size(); i++) {
                out.write(' ');
            }
        }
    }

    private void beforeValue() throws IOException {
        switch (stack.peek()) {
            case EMPTY_LIST:
                stack.pop();
                stack.push(NONEMPTY_LIST);
                newline();
                break;
            case EMPTY_MAP:
                stack.pop();
                stack.push(NONEMPTY_MAP);
                newline();
                break;
            case NONEMPTY_LIST:
                out.append(',');
                newline();
                break;
            case NONEMPTY_MAP:
                out.append(',');
                newline();
                break;
            case EMPTY_DOCUMENT:
                stack.pop();
                stack.push(NONEMPTY_DOCUMENT);
                break;
            case JSON_NAME:
                out.append(':');
                stack.pop();
                stack.push(NONEMPTY_STRUCT);
                break;
        }
    }

    private void writeString(String value) throws IOException {
        out.write('"');
        out.write(value);
        out.write('"');
    }

    private void writeBegin(byte empty, char symbol) throws IOException {
        beforeValue();
        stack.push(empty);
        out.write(symbol);
    }

    private void writeEnd(byte empty, byte nonEmpty, char symbol) throws IOException {
        byte element = stack.peek();
        if (element != empty && element != nonEmpty) {
            throw new BadFormatException();
        }

        if (element == nonEmpty) {
            newline();
        }
        stack.pop();
        out.write(symbol);
    }

    private void writeValue(String value, ThriftType type) throws IOException {
        writeBeforeValue(type);
        beforeValue();
        if (type == ThriftType.BINARY || type == ThriftType.STRING) {
            writeString(value);
        } else {
            out.write(value);
        }
        writeAfterValue();
    }

    @Override
    public void beginStruct(int size) throws IOException {
        writeBeforeValue(ThriftType.STRUCT);
        writeBegin(EMPTY_STRUCT, '{');
    }

    @Override
    public void endStruct() throws IOException {
        writeEnd(EMPTY_STRUCT, NONEMPTY_STRUCT, '}');
        writeAfterValue();
    }

    @Override
    public void beginList(int size) throws IOException {
        writeBeforeValue(ThriftType.LIST);
        writeBegin(EMPTY_LIST, '[');
    }

    @Override
    public void endList() throws IOException {
        writeEnd(EMPTY_LIST, NONEMPTY_LIST, ']');
        writeAfterValue();
    }

    @Override
    public void beginMap(int size) throws IOException {
        writeBeforeValue(ThriftType.MAP);
        writeBegin(EMPTY_MAP, '[');
    }

    @Override
    public void endMap() throws IOException {
        writeEnd(EMPTY_MAP, NONEMPTY_MAP, ']');
        writeAfterValue();
    }

    @Override
    public void beginKey() throws IOException {
        writeBegin(EMPTY_STRUCT, '{');
        name("key");
    }

    @Override
    public void endKey() throws IOException {
        if (stack.peek() == JSON_NAME) {
            throw new BadFormatException();
        }
    }

    @Override
    public void beginValue() throws IOException {
        name("value");
    }

    @Override
    public void endValue() throws IOException {
        writeEnd(EMPTY_STRUCT, NONEMPTY_STRUCT, '}');
    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name, "JSON name must not be null");

        if (stack.peek() == NONEMPTY_STRUCT) {
            out.write(',');
        }
        newline();
        stack.pop();
        stack.push(JSON_NAME);

        writeString(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        writeValue(value ? "true" : "false", ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value, ThriftType.STRING);
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(Double.toString(value), ThriftType.DOUBLE);
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(Long.toString(value), ThriftType.LONG);
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value), ThriftType.BINARY);
    }

    @Override
    public void nullValue() throws IOException {
        writeValue("null", ThriftType.NULL);
    }

    @Override
    public String getResult() throws IOException {
        out.close();

        if (stack.size() > 1 || stack.size() == 1 && stack.peek() != NONEMPTY_DOCUMENT) {
            throw new BadFormatException();
        }

        return out.toString();
    }

}
