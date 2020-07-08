package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.common.stack.ByteStack;
import com.rbkmoney.geck.common.stack.ObjectStack;
import com.rbkmoney.geck.common.util.TBaseUtil;
import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;


public class TBaseHandler<R extends TBase> implements StructHandler<R> {

    private final Class<R> parentClass;
    private final Mode mode;
    private final boolean checkRequiredFields;

    private ByteStack stateStack = new ByteStack();
    private ObjectStack elementStack = new ObjectStack();
    private ObjectStack<TFieldIdEnum> fieldStack = new ObjectStack<>();
    private ObjectStack<FieldValueMetaData> valueMetaDataStack = new ObjectStack<>();

    private R result;

    public TBaseHandler(Class<R> parentClass) {
        this(parentClass, Mode.PREFER_ID, true);
    }

    public TBaseHandler(Class<R> parentClass, Mode mode) {
        this(parentClass, mode, true);
    }

    public TBaseHandler(Class<R> parentClass, Mode mode, boolean checkRequiredFields) {
        Objects.requireNonNull(parentClass, "parent class must be set");
        Objects.requireNonNull(mode, "mode must be set");

        this.parentClass = parentClass;
        this.mode = mode;
        this.checkRequiredFields = checkRequiredFields;
    }

    @Override
    public void beginStruct(int size) throws IOException {

        try {
            if (stateStack.isEmpty()) {
                TBase tBase = parentClass.newInstance();
                addElement(startStruct, tBase);
            } else {
                FieldValueMetaData valueMetaData = getChildValueMetaData();
                checkType(valueMetaData, ThriftType.STRUCT);
                TBase child = TypeUtil.convertType(StructMetaData.class, valueMetaData)
                        .getStructClass().newInstance();
                addElement(startStruct, child, valueMetaData);
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IOException(ex);
        }
    }

    private void addElement(byte state, Object value) {
        addElement(state, value, null);
    }

    private void addElement(byte state, Object value, FieldValueMetaData fieldValueMetaData) {
        stateStack.push(state);
        elementStack.push(value);
        valueMetaDataStack.push(fieldValueMetaData);
    }

    @Override
    public void endStruct() throws IOException {
        checkState(startStruct);

        TBase tBase = TypeUtil.convertType(TBase.class, elementStack.peek());
        if (checkRequiredFields) {
            checkRequiredFields(tBase);
        }

        stateStack.pop();
        elementStack.pop();
        valueMetaDataStack.pop();

        if (!stateStack.isEmpty()) {
            saveValue(tBase, elementStack.peek());
        }

        result = (R) tBase;
    }

    private void checkRequiredFields(TBase tBase) throws BadFormatException {
        if (tBase instanceof TUnion) {
            TUnion tUnion = (TUnion) tBase;
            if (!tUnion.isSet()) {
                throw new BadFormatException(String.format("one of fields in union '%s' must be set", tUnion.getClass().getSimpleName()));
            }
        } else {
            Map<TFieldIdEnum, FieldMetaData> fieldValueMetaDataMap = tBase.getFieldMetaData();
            for (TFieldIdEnum field : tBase.getFields()) {
                FieldMetaData metaData = fieldValueMetaDataMap.get(field);
                if (metaData.requirementType == TFieldRequirementType.REQUIRED && !tBase.isSet(field)) {
                    throw new BadFormatException(String.format("field '%s' is required and must not be null", field.getFieldName()));
                }
            }
        }
    }

    private FieldValueMetaData getChildValueMetaData() throws IOException {
        checkState(pointName, startList, startSet, startMapKey, startMapValue);
        FieldValueMetaData valueMetaData = valueMetaDataStack.peek();
        switch (stateStack.peek()) {
            case pointName:
                TBase tBase = TypeUtil.convertType(TBase.class, elementStack.peek());
                return TBaseUtil.getValueMetaData(fieldStack.peek(), tBase);
            case startList:
            case startSet:
                CollectionMetaData collectionMetaData = TypeUtil.convertType(CollectionMetaData.class, valueMetaData);
                return collectionMetaData.getElementMetaData();
            case startMapKey:
                MapMetaData mapMetaData = TypeUtil.convertType(MapMetaData.class, valueMetaData);
                return mapMetaData.getKeyMetaData();
            case startMapValue:
                mapMetaData = TypeUtil.convertType(MapMetaData.class, valueMetaData);
                return mapMetaData.getValueMetaData();
            default:
                throw new BadFormatException(String.format("unknown state %d", stateStack.peek()));
        }
    }

    @Override
    public void beginList(int size) throws IOException {
        startCollection(startList, ThriftType.LIST, new ArrayList(size));
    }

    @Override
    public void endList() throws IOException {
        endCollection(startList);
    }

    @Override
    public void beginSet(int size) throws IOException {
        startCollection(startSet, ThriftType.SET, new HashSet(size));
    }

    @Override
    public void endSet() throws IOException {
        endCollection(startSet);
    }

    private void startCollection(byte state, ThriftType actualType, Collection collection) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        checkType(valueMetaData, actualType);
        addElement(state, collection, valueMetaData);
    }

    private void endCollection(byte state) throws IOException {
        checkState(state);
        stateStack.pop();
        valueMetaDataStack.pop();
        saveValue(elementStack.pop(), elementStack.peek());
    }

    @Override
    public void beginMap(int size) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        checkType(valueMetaData, ThriftType.MAP);

        Map map = new LinkedHashMap(size);
        addElement(startMap, map, valueMetaData);
    }

    @Override
    public void endMap() throws IOException {
        checkState(startMap);
        stateStack.pop();
        valueMetaDataStack.pop();
        saveValue(elementStack.pop(), elementStack.peek());
    }

    private void checkState(byte... requiredStates) throws BadFormatException {
        if (stateStack.isEmpty()) {
            throw new BadFormatException("state not found");
        }

        byte state = stateStack.peek();
        for (byte requireState : requiredStates) {
            if (state == requireState) {
                return;
            }
        }
        throw new BadFormatException(String.format("incorrect state %d, expected states: %s", state, Arrays.toString(requiredStates)));
    }

    private void checkType(FieldValueMetaData valueMetaData, ThriftType actualType) throws BadFormatException {
        ThriftType expectedType = ThriftType.findByMetaData(valueMetaData);
        if (expectedType != actualType) {
            throw new BadFormatException(String.format("incorrect type of value: expected '%s', actual '%s' (last field: '%s', last state: %s)",
                    expectedType, actualType, fieldStack.peek(), stateStack.peek()));
        }
    }

    @Override
    public void beginKey() throws IOException {
        checkState(startMap);
        stateStack.push(startMapKey);
    }

    @Override
    public void endKey() throws IOException {
        checkState(endMapKey);
    }

    @Override
    public void beginValue() throws IOException {
        checkState(endMapKey);
        stateStack.push(startMapValue);
    }

    @Override
    public void endValue() throws IOException {
        checkState(endMapValue);
        Object value = elementStack.pop();
        stateStack.pop();

        checkState(endMapKey);
        Object key = elementStack.pop();
        stateStack.pop();

        checkState(startMap);
        Map map = TypeUtil.convertType(Map.class, elementStack.peek());
        map.put(key, value);
    }

    @Override
    public void name(String name) throws IOException {
        name(DEFAULT_FIELD_ID, name);
    }

    @Override
    public void name(byte id, String name) throws IOException {
        Objects.requireNonNull(name, "name must not be null");

        checkState(startStruct);
        TBase tBase = (TBase) elementStack.peek();

        TFieldIdEnum tFieldIdEnum;
        if (mode == Mode.ONLY_ID || mode == Mode.PREFER_ID) {
            tFieldIdEnum = TBaseUtil.getFieldById(id, tBase);

            if (tFieldIdEnum == null) {
                if (mode == Mode.ONLY_ID) {
                    throw new IllegalArgumentException(
                            String.format("Field with id '%d' not found", id)
                    );
                } else {
                    tFieldIdEnum = TBaseUtil.getField(name, tBase);
                }
            }
        } else {
            tFieldIdEnum = TBaseUtil.getField(name, tBase);
            if (tFieldIdEnum == null) {
                tFieldIdEnum = TBaseUtil.getFieldById(id, tBase);
            }
        }

        if (tFieldIdEnum == null) {
            throw new IllegalArgumentException(
                    String.format("Field '%s' not found", name)
            );
        }

        fieldStack.push(tFieldIdEnum);
        stateStack.push(pointName);
    }

    @Override
    public void value(boolean value) throws IOException {
        value(value, ThriftType.BOOLEAN);
    }

    @Override
    public void value(String value) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        if (type == ThriftType.ENUM) {
            Class enumClass = ((EnumMetaData) valueMetaData).getEnumClass();
            value(Enum.valueOf(enumClass, value), ThriftType.ENUM);
        } else {
            value(value, ThriftType.STRING);

        }
    }

    @Override
    public void value(double value) throws IOException {
        value(value, ThriftType.DOUBLE);
    }

    @Override
    public void value(byte[] value) throws IOException {
        value(ByteBuffer.wrap(value), ThriftType.BINARY);
    }

    @Override
    public void value(long value) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case BYTE:
                byte byteValue = TypeUtil.toByteExact(value);
                value(byteValue, valueMetaData, ThriftType.BYTE);
                break;
            case SHORT:
                short shortValue = TypeUtil.toShortExact(value);
                value(shortValue, valueMetaData, ThriftType.SHORT);
                break;
            case INTEGER:
                int intValue = TypeUtil.toIntExact(value);
                value(intValue, valueMetaData, ThriftType.INTEGER);
                break;
            default:
                value(value, valueMetaData, ThriftType.LONG);
        }

    }

    private void value(Object value, ThriftType actualType) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        value(value, valueMetaData, actualType);
    }

    private void value(Object value, FieldValueMetaData valueMetaData, ThriftType actualType) throws IOException {
        checkType(valueMetaData, actualType);
        saveValue(value, elementStack.peek());
    }

    private void saveValue(Object value, Object parent) throws IOException {
        switch (stateStack.peek()) {
            case pointName:
                TBase tBase = TypeUtil.convertType(TBase.class, parent);
                tBase.setFieldValue(fieldStack.pop(), value);
                stateStack.pop();
                break;
            case startList:
            case startSet:
                TypeUtil.convertType(Collection.class, parent).add(value);
                break;
            case startMapKey:
                elementStack.push(value);
                stateStack.pop();
                stateStack.push(endMapKey);
                break;
            case startMapValue:
                elementStack.push(value);
                stateStack.pop();
                stateStack.push(endMapValue);
                break;
            default:
                throw new BadFormatException(String.format("cannot save value, unexpected state %d", stateStack.peek()));
        }
    }

    @Override
    public void nullValue() throws IOException {
        if (!stateStack.isEmpty()) {
            saveValue(null, elementStack.peek());
        }
    }

    @Override
    public R getResult() throws IOException {
        if (result == null) {
            throw new BadFormatException("result is null");
        }

        if (!(stateStack.isEmpty()
                && fieldStack.isEmpty()
                && elementStack.isEmpty()
                && valueMetaDataStack.isEmpty())) {
            throw new BadFormatException("stack is not empty");
        }
        R tBase = result;
        result = null;
        return tBase;
    }

    public enum Mode {
        ONLY_ID,
        PREFER_ID,
        PREFER_NAME
    }

}
