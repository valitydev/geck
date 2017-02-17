package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.ByteStack;
import com.rbkmoney.geck.serializer.ObjectStack;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.ObjectUtil;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.*;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;

/**
 * Created by tolkonepiu on 08/02/2017.
 */
public class TBaseHandler<R extends TBase> implements StructHandler<R> {

    private final Class<R> parentClass;

    private ByteStack stateStack = new ByteStack();
    private ObjectStack elementStack = new ObjectStack();
    private ObjectStack<TFieldIdEnum> fieldStack = new ObjectStack<>();
    private ObjectStack<FieldValueMetaData> valueMetaDataStack = new ObjectStack<>();

    private R result;

    public TBaseHandler(Class<R> parentClass) {
        this.parentClass = parentClass;
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
                TBase child = ObjectUtil.convertType(StructMetaData.class, valueMetaData)
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

        TBase tBase = ObjectUtil.convertType(TBase.class, elementStack.peek());
        checkRequiredFields(tBase);

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

    @Override
    public void beginList(int size) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case LIST:
                List list = new ArrayList(size);
                addElement(startList, list, valueMetaData);
                break;
            case SET:
                Set set = new HashSet(size);
                addElement(startList, set, valueMetaData);
                break;
            default:
                throw new BadFormatException(String.format("value expected '%s', actual collection", type));
        }
    }

    private FieldValueMetaData getChildValueMetaData() throws IOException {
        checkState(pointName, startList, startMapKey, startMapValue);
        FieldValueMetaData valueMetaData = valueMetaDataStack.peek();
        switch (stateStack.peek()) {
            case pointName:
                TBase tBase = ObjectUtil.convertType(TBase.class, elementStack.peek());
                return TBaseUtil.getValueMetaData(fieldStack.peek(), tBase);
            case startList:
                CollectionMetaData collectionMetaData = ObjectUtil.convertType(CollectionMetaData.class, valueMetaData);
                return collectionMetaData.getElementMetaData();
            case startMapKey:
                MapMetaData mapMetaData = ObjectUtil.convertType(MapMetaData.class, valueMetaData);
                return mapMetaData.getKeyMetaData();
            case startMapValue:
                mapMetaData = ObjectUtil.convertType(MapMetaData.class, valueMetaData);
                return mapMetaData.getValueMetaData();
            default:
                throw new BadFormatException(String.format("unknown state %d", stateStack.peek()));
        }
    }

    @Override
    public void endList() throws IOException {
        checkState(startList);
        stateStack.pop();
        valueMetaDataStack.pop();
        saveValue(elementStack.pop(), elementStack.peek());
    }

    @Override
    public void beginMap(int size) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        checkType(valueMetaData, ThriftType.MAP);

        Map map = new HashMap(size);
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
            throw new BadFormatException(String.format("incorrect type of value: expected '%s', actual '%s'", expectedType, actualType));
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
        Map map = ObjectUtil.convertType(Map.class, elementStack.peek());
        map.put(key, value);
    }

    @Override
    public void name(String name) throws IOException {
        Objects.requireNonNull(name, "name must not be null");

        checkState(startStruct);
        TBase tBase = (TBase) elementStack.peek();

        fieldStack.push(TBaseUtil.getField(name, tBase));
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

        switch (type) {
            case STRING:
                value(value, ThriftType.STRING);
                break;
            case ENUM:
                Class enumClass = ((EnumMetaData) valueMetaData).getEnumClass();
                value(Enum.valueOf(enumClass, value), ThriftType.ENUM);
                break;
        }
    }

    @Override
    public void value(double value) throws IOException {
        value(value, ThriftType.DOUBLE);
    }

    @Override
    public void value(byte[] value) throws IOException {
        value(value, ThriftType.BINARY);
    }

    @Override
    public void value(long value) throws IOException {
        FieldValueMetaData valueMetaData = getChildValueMetaData();
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case BYTE:
                byte byteValue = ObjectUtil.toByteExact(value);
                value(byteValue, valueMetaData, ThriftType.BYTE);
                break;
            case SHORT:
                short shortValue = ObjectUtil.toShortExact(value);
                value(shortValue, valueMetaData, ThriftType.SHORT);
                break;
            case INTEGER:
                int intValue = ObjectUtil.toIntExact(value);
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
                TBase tBase = ObjectUtil.convertType(TBase.class, parent);
                tBase.setFieldValue(fieldStack.pop(), value);
                stateStack.pop();
                break;
            case startList:
                ObjectUtil.convertType(Collection.class, parent).add(value);
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
        return result;
    }

}
