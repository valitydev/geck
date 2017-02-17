package com.rbkmoney.geck.serializer.kit.object;

import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;

import java.io.IOException;
import java.util.*;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;
import static com.rbkmoney.geck.serializer.kit.object.ObjectHandlerConstants.ESCAPE_CHAR;
import static com.rbkmoney.geck.serializer.kit.object.ObjectHandlerConstants.MAP_MARK;

/**
 * Created by vpankrashkin on 09.02.17.
 */
public class ObjectHandler implements StructHandler<Object> {
    private final ByteStack state = new ByteStack(nop);
    private final ObjectStack context = new ObjectStack(null);
    private Object result;

    @Override
    public void beginStruct(int size) throws IOException {
        internValue(new  LinkedHashMap((int) (size * 1.25)), startStruct, false);
    }

    @Override
    public void endStruct() throws IOException {
        checkState(startStruct, state.pop());
        context.pop();
    }

    @Override
    public void beginList(int size) throws IOException {
        internValue(new ArrayList(size), startList, false);
    }

    @Override
    public void endList() throws IOException {
        checkState(startList, state.pop());
        context.pop();
    }

    @Override
    public void beginMap(int size) throws IOException {
        internValue(new ArrayList<>(), startMap, true);
    }

    @Override
    public void endMap() throws IOException {
        checkState(startMap, state.pop());
        context.pop();
    }

    @Override
    public void beginKey() throws IOException {
        checkState(startMap, state.peek());
        state.push(startMapKey);
        context.push(null);
    }

    @Override
    public void endKey() throws IOException {
        checkState(startMapKey, state.peek());
        state.push(endMapKey);
    }

    @Override
    public void beginValue() throws IOException {
        checkState(endMapKey, state.pop());
        state.push(startMapValue);
        context.push(null);
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
        ((List) context.peek()).add(entryStruct);
    }

    @Override
    public void name(String name) throws IOException {
        checkState(startStruct, state.peek());
        if (name.length() == 0) {
            throw new BadFormatException("Name cannot be empty");
        }
        state.push(pointName);
        context.push(name);
    }

    @Override
    public void value(boolean value) throws IOException {
        internValue(value, nop, false);
    }

    @Override
    public void value(String value) throws IOException {
        internValue(value, nop, false);
    }

    @Override
    public void value(double value) throws IOException {
        internValue(value, nop, false);
    }

    @Override
    public void value(long value) throws IOException {
        internValue(value, nop, false);
    }

    @Override
    public void value(byte[] value) throws IOException {
        internValue(value, nop, false);
    }

    @Override
    public void nullValue() throws IOException {
        internValue(null, nop, false);
    }

    @Override
    public Object getResult() throws IOException {
        checkState(nop, state.peek());
        return result;
    }

    private void internValue(Object value, byte newState, boolean isMap) throws BadFormatException {
        if (isMap && state.peek() != pointName) {
            ((List) value).add(MAP_MARK);
        }

        switch (state.peek()) {
            case nop:
                result = value;
                break;
            case pointName:
                state.pop();
                String name = (String) context.pop();
                name = escapeString(name, isMap);
                ((Map) context.peek()).put(name, value);
                break;
            case startList:
                ((List)context.peek()).add(value);
                break;
            case startMapKey:
                context.pop();//remove default key val
                context.push(value);
                break;
            case startMapValue:
                context.pop();//remove default value
                context.push(value);
                break;
            default:
                throw new BadFormatException(String.format("Wrong type in intern value stack, actual: %d", state.peek()));
        }
        if (newState != nop) {
            state.push(newState);
            context.push(value);
        }
    }

    private String escapeString(String name, boolean isMap) {
        int i = 0;
        for (; i < name.length(); ++i) {
            if (name.charAt(i) == ESCAPE_CHAR) {
                break;
            }
        }
        if (i < name.length()) {
            StringBuilder sb = new StringBuilder(name.length() + (isMap ? 5 : 1));
            sb.append(name, 0, i);
            for (; i < name.length(); ++i) {
                char c = name.charAt(i);
                if (c == ESCAPE_CHAR) {
                    sb.append(ESCAPE_CHAR);
                }
                sb.append(c);
            }
            if (isMap) {
                sb.append(ObjectHandlerConstants.MAP_MARK);
            }
            return sb.toString();
        } else {
            return isMap ? name + ObjectHandlerConstants.MAP_MARK : name;
        }
    }

    private void checkState(byte expectedState, byte actualState) throws BadFormatException {
        if (actualState != expectedState) {
            throw new BadFormatException(String.format("Wrong type in stack, expected: %d, actual: %d", expectedState, actualState));
        }
    }
}
