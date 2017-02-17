package com.rbkmoney.geck.serializer.kit.json;

import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import gnu.trove.map.hash.THashMap;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class JsonHandler implements StructHandler<Writer> {

    private final Map<Character, String> replaceCharsMap = new THashMap<>();

    {
        for (int i = 0; i < '\u00A0'; i++) {
            if (Character.getType((char) i) == Character.CONTROL) {
                replaceCharsMap.put((char) i, String.format("\\u%04x", i));
            }
        }

        replaceCharsMap.put('"', "\\\"");
        replaceCharsMap.put('\n', "\\n");
        replaceCharsMap.put('\r', "\\r");
        replaceCharsMap.put('\b', "\\b");
        replaceCharsMap.put('\f', "\\f");
        replaceCharsMap.put('\t', "\\t");
        replaceCharsMap.put('\\', "\\\\");
    }

    static final byte EMPTY_STRUCT = 1;

    static final byte NONEMPTY_STRUCT = 2;

    static final byte EMPTY_LIST = 3;

    static final byte NONEMPTY_LIST = 4;

    static final byte EMPTY_SET = 5;

    static final byte NONEMPTY_SET = 6;

    static final byte EMPTY_MAP = 7;

    static final byte NONEMPTY_MAP = 8;

    static final byte JSON_NAME = 9;

    private final Writer out;

    private final boolean pretty;

    private ByteStack stack = new ByteStack();

    public JsonHandler() {
        this(new StringWriter());
    }

    public JsonHandler(Writer out) {
        this(out, false);
    }

    public JsonHandler(Writer out, boolean pretty) {
        Objects.requireNonNull(out, "Writer must not be null");
        this.out = out;
        this.pretty = pretty;
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
        if (stack.isEmpty()) {
            return;
        }
        switch (stack.peek()) {
            case EMPTY_LIST:
                stack.pop();
                stack.push(NONEMPTY_LIST);
                newline();
                break;
            case EMPTY_SET:
                stack.pop();
                stack.push(NONEMPTY_SET);
                newline();
                break;
            case EMPTY_MAP:
                stack.pop();
                stack.push(NONEMPTY_MAP);
                newline();
                break;
            case NONEMPTY_LIST:
            case NONEMPTY_SET:
            case NONEMPTY_MAP:
                out.append(',');
                newline();
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
        int length = value.length();
        for (int i = 0; i < length; i++) {
            char charValue = value.charAt(i);
            if (replaceCharsMap.containsKey(charValue)) {
                out.write(replaceCharsMap.get(charValue));
            } else {
                out.write(charValue);
            }
        }

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

    private void writeValue(String value) throws IOException {
        writeValue(value, false);
    }

    private void writeValue(String value, boolean asString) throws IOException {
        beforeValue();
        if (asString) {
            writeString(value);
        } else {
            out.write(value);
        }
    }

    @Override
    public void beginStruct(int size) throws IOException {
        writeBegin(EMPTY_STRUCT, '{');
    }

    @Override
    public void endStruct() throws IOException {
        writeEnd(EMPTY_STRUCT, NONEMPTY_STRUCT, '}');
    }

    @Override
    public void beginList(int size) throws IOException {
        writeBegin(EMPTY_LIST, '[');
    }

    @Override
    public void endList() throws IOException {
        writeEnd(EMPTY_LIST, NONEMPTY_LIST, ']');
    }

    @Override
    public void beginSet(int size) throws IOException {
        writeBegin(EMPTY_SET, '[');
    }

    @Override
    public void endSet() throws IOException {
        writeEnd(EMPTY_SET, NONEMPTY_SET, ']');
    }

    @Override
    public void beginMap(int size) throws IOException {
        writeBegin(EMPTY_MAP, '[');
    }

    @Override
    public void endMap() throws IOException {
        writeEnd(EMPTY_MAP, NONEMPTY_MAP, ']');
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
        writeValue(value ? "true" : "false");
    }

    @Override
    public void value(String value) throws IOException {
        writeValue(value, true);
    }

    @Override
    public void value(double value) throws IOException {
        writeValue(Double.toString(value));
    }

    @Override
    public void value(long value) throws IOException {
        writeValue(Long.toString(value));
    }

    @Override
    public void value(byte[] value) throws IOException {
        writeValue(Base64.getEncoder().encodeToString(value), true);
    }

    @Override
    public void nullValue() throws IOException {
        writeValue("null");
    }

    @Override
    public Writer getResult() throws IOException {
        out.close();

        if (!stack.isEmpty()) {
            throw new BadFormatException("stack is not empty");
        }

        return out;
    }

}
