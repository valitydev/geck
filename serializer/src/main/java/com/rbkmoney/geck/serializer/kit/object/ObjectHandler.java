package com.rbkmoney.geck.serializer.kit.object;

import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.common.stack.ByteStack;
import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;
import static com.rbkmoney.geck.serializer.kit.object.ObjectHandlerConstants.*;

/**
 * Created by vpankrashkin on 09.02.17.
 */
public class ObjectHandler implements StructHandler<Object> {
    private final ByteStack state = new ByteStack(nop);
    private final ObjectStack context = new ObjectStack(null);
    private Object result;

    @Override
    public void beginStruct(int size) throws IOException {
        retain(startStruct, new  LinkedHashMap((int) (size * 1.5)));
    }

    @Override
    public void endStruct() throws IOException {
        checkState(startStruct, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginList(int size) throws IOException {
        retain(startList, new ArrayList<>(size));
    }

    @Override
    public void endList() throws IOException {
        checkState(startList, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginSet(int size) throws IOException {
        retain(startSet, new HashSet((int) (size * 1.5)));
    }

    @Override
    public void endSet() throws IOException {
        checkState(startSet, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginMap(int size) throws IOException {
        ArrayList value = new ArrayList();
        if (state.peek() == pointName) {
            context.push(injectType((String) context.pop(), StructType.MAP));
        } else {
            value.add(MAP_MARK);
        }
        retain(startMap, value);
    }

    @Override
    public void endMap() throws IOException {
        checkState(startMap, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginKey() throws IOException {
        checkState(startMap, state.peek());
        retain(startMapKey, null);
    }

    @Override
    public void endKey() throws IOException {
        checkState(startMapKey, state.peek());
        state.push(endMapKey);
    }

    @Override
    public void beginValue() throws IOException {
        checkState(endMapKey, state.pop());
        retain(startMapValue, null);
    }

    @Override
    public void endValue() throws IOException {
        checkState(startMapValue, state.pop());
        Object value = context.pop();
        checkState(startMapKey, state.pop());
        Object key = context.pop();
        checkState(startMap, state.peek());
        Map entryStruct = new LinkedHashMap();
        entryStruct.put(ObjectHandlerConstants.MAP_KEY, key);
        entryStruct.put(ObjectHandlerConstants.MAP_VALUE, value);
        addValue(entryStruct);
    }

    @Override
    public void name(String name) throws IOException {
        checkState(startStruct, state.peek());
        if (name.length() == 0) {
            throw new BadFormatException("Name cannot be empty");
        }
        retain(pointName, name);
    }

    @Override
    public void value(boolean value) throws IOException {
        addValue(value);
    }

    @Override
    public void value(String value) throws IOException {
        addValue(escapeString(value));
    }

    @Override
    public void value(double value) throws IOException {
        addValue(value);
    }

    @Override
    public void value(long value) throws IOException {
        addValue(value);
    }

    @Override
    public void value(byte[] value) throws IOException {
        addValue(ByteBuffer.wrap(value));
    }

    @Override
    public void nullValue() throws IOException {
        addValue(null);
    }

    @Override
    public Object getResult() throws IOException {
        checkState(nop, state.peek());
        return result;
    }

    private void addValue(Object value) throws BadFormatException {
        switch (state.peek()) {
            case nop:
                result = value;
                break;
            case pointName:
                state.pop();
                String name = (String) context.pop();
                ((Map) context.peek()).put(name, value);
                break;
            case startList:
                ((List)context.peek()).add(value);
                break;
            case startSet:
                ((Set)context.peek()).add(value);
                break;
            case startMapKey:
                context.pop();//remove default key val
                context.push(value);
                break;
            case startMapValue:
                context.pop();//remove default value
                context.push(value);
                break;
            case startMap:
                ((List)context.peek()).add(value);
                break;
            default:
                throw new BadFormatException(String.format("Wrong type in intern value stack, actual: %d", state.peek()));
        }
    }

    private void retain(byte stateVal, Object contextVal) {
        state.push(stateVal);
        context.push(contextVal);
    }

    private String injectType(String name, StructType type) {
        return name + TYPE_DELIMITER + type.getKey();
    }

    private String escapeString(String string) {
        int i = 0;
        for (; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == ESCAPE_CHAR || c == TYPE_DELIMITER) {
                break;
            }
        }
        if (i < string.length()) {
            StringBuilder sb = new StringBuilder(string.length() + 1);
            sb.append(string, 0, i);
            for (; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (c == ESCAPE_CHAR || c == TYPE_DELIMITER) {
                    sb.append(ESCAPE_CHAR);
                }
                    sb.append(c);
            }
            return sb.toString();
        } else {
            return string;
        }
    }

    private void checkState(byte expectedState, byte actualState) throws BadFormatException {
        if (actualState != expectedState) {
            throw new BadFormatException(String.format("Wrong type in stack, expected: %d, actual: %d", expectedState, actualState));
        }
    }
}
