package dev.vality.geck.serializer.kit.object;

import dev.vality.geck.serializer.exception.BadFormatException;
import dev.vality.geck.common.stack.ByteStack;
import dev.vality.geck.common.stack.ObjectStack;
import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.kit.StructType;
import dev.vality.geck.serializer.kit.EventFlags;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class ObjectHandler implements StructHandler<Object> {
    private final ByteStack state = new ByteStack(EventFlags.nop);
    private final ObjectStack context = new ObjectStack(null);
    private Object result;

    @Override
    public void beginStruct(int size) throws IOException {
        retain(EventFlags.startStruct, new  LinkedHashMap((int) (size * 1.5)));
    }

    @Override
    public void endStruct() throws IOException {
        checkState(EventFlags.startStruct, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginList(int size) throws IOException {
        retain(EventFlags.startList, new ArrayList<>(size));
    }

    @Override
    public void endList() throws IOException {
        checkState(EventFlags.startList, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginSet(int size) throws IOException {
        retain(EventFlags.startSet, new HashSet((int) (size * 1.5)));
    }

    @Override
    public void endSet() throws IOException {
        checkState(EventFlags.startSet, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginMap(int size) throws IOException {
        ArrayList value = new ArrayList();
        if (state.peek() == EventFlags.pointName) {
            context.push(injectType((String) context.pop(), StructType.MAP));
        } else {
            value.add(ObjectHandlerConstants.MAP_MARK);
        }
        retain(EventFlags.startMap, value);
    }

    @Override
    public void endMap() throws IOException {
        checkState(EventFlags.startMap, state.pop());
        addValue(context.pop());
    }

    @Override
    public void beginKey() throws IOException {
        checkState(EventFlags.startMap, state.peek());
        retain(EventFlags.startMapKey, null);
    }

    @Override
    public void endKey() throws IOException {
        checkState(EventFlags.startMapKey, state.peek());
        state.push(EventFlags.endMapKey);
    }

    @Override
    public void beginValue() throws IOException {
        checkState(EventFlags.endMapKey, state.pop());
        retain(EventFlags.startMapValue, null);
    }

    @Override
    public void endValue() throws IOException {
        checkState(EventFlags.startMapValue, state.pop());
        Object value = context.pop();
        checkState(EventFlags.startMapKey, state.pop());
        Object key = context.pop();
        checkState(EventFlags.startMap, state.peek());
        Map entryStruct = new LinkedHashMap();
        entryStruct.put(ObjectHandlerConstants.MAP_KEY, key);
        entryStruct.put(ObjectHandlerConstants.MAP_VALUE, value);
        addValue(entryStruct);
    }

    @Override
    public void name(String name) throws IOException {
        checkState(EventFlags.startStruct, state.peek());
        if (name.length() == 0) {
            throw new BadFormatException("Name cannot be empty");
        }
        retain(EventFlags.pointName, name);
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
        checkState(EventFlags.nop, state.peek());
        checkState(EventFlags.nop, context.size() == 0 ? EventFlags.nop : EventFlags.pointValue);
        Object readyResult = result;
        result = null;
        return readyResult;
    }

    private void addValue(Object value) throws BadFormatException {
        switch (state.peek()) {
            case EventFlags.nop:
                result = value;
                break;
            case EventFlags.pointName:
                state.pop();
                String name = (String) context.pop();
                ((Map) context.peek()).put(name, value);
                break;
            case EventFlags.startList:
                ((List)context.peek()).add(value);
                break;
            case EventFlags.startSet:
                ((Set)context.peek()).add(value);
                break;
            case EventFlags.startMapKey:
                context.pop();//remove default key val
                context.push(value);
                break;
            case EventFlags.startMapValue:
                context.pop();//remove default value
                context.push(value);
                break;
            case EventFlags.startMap:
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
        return name + ObjectHandlerConstants.TYPE_DELIMITER + type.getKey();
    }

    private String escapeString(String string) {
        int i = 0;
        for (; i < string.length(); ++i) {
            char c = string.charAt(i);
            if (c == ObjectHandlerConstants.ESCAPE_CHAR || c == ObjectHandlerConstants.TYPE_DELIMITER) {
                break;
            }
        }
        if (i < string.length()) {
            StringBuilder sb = new StringBuilder(string.length() + 1);
            sb.append(string, 0, i);
            for (; i < string.length(); ++i) {
                char c = string.charAt(i);
                if (c == ObjectHandlerConstants.ESCAPE_CHAR || c == ObjectHandlerConstants.TYPE_DELIMITER) {
                    sb.append(ObjectHandlerConstants.ESCAPE_CHAR);
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
