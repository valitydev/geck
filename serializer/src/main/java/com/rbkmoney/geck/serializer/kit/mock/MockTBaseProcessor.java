package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.common.util.TypeUtil;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor;
import com.rbkmoney.geck.serializer.kit.tbase.ThriftType;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tolkonepiu on 11/02/2017.
 */
public class MockTBaseProcessor extends TBaseProcessor {

    private final MockMode mode;

    private final int maxStringLength;

    private final int maxContainerSize;

    private ValueGenerator valueGenerator;

    private Map<String, FieldHandler> fieldHandlers = new HashMap<>();

    public MockTBaseProcessor() {
        this(MockMode.ALL);
    }

    public MockTBaseProcessor(MockMode mode) {
        this(mode, 15, 10);
    }

    public MockTBaseProcessor(MockMode mode, ValueGenerator valueGenerator) {
        this(mode, 15, 10, valueGenerator);
    }

    public MockTBaseProcessor(MockMode mode, int maxStringLength, int maxContainerSize) {
        this(mode, maxStringLength, maxContainerSize, new RandomValueGenerator());
    }

    public MockTBaseProcessor(MockMode mode, int maxStringLength, int maxContainerSize, ValueGenerator valueGenerator) {
        this.mode = mode;
        this.maxStringLength = maxStringLength;
        this.maxContainerSize = maxContainerSize;
        this.valueGenerator = valueGenerator;
    }

    public void addFieldHandler(FieldHandler handler, String... fieldNames) {
        for (String fieldName : fieldNames) {
            fieldHandlers.put(fieldName, handler);
        }
    }

    @Override
    protected void processUnsetField(TFieldIdEnum tFieldIdEnum, FieldMetaData fieldMetaData, StructHandler handler) throws IOException {
        if (needProcess(fieldMetaData)) {
            handler.name(tFieldIdEnum.getFieldName());
            if (fieldHandlers.containsKey(fieldMetaData.fieldName)) {
                fieldHandlers.get(tFieldIdEnum.getFieldName()).handle(handler);
            } else {
                processFieldValue(fieldMetaData.valueMetaData, handler);
            }
        }
    }

    @Override
    protected void processUnsetUnion(TUnion tUnion, StructHandler handler) throws IOException {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tUnion.getFieldMetaData();
        TFieldIdEnum tFieldIdEnum = valueGenerator.getField(tUnion);
        FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
        handler.name(tFieldIdEnum.getFieldName());
        if (fieldHandlers.containsKey(fieldMetaData.fieldName)) {
            fieldHandlers.get(tFieldIdEnum.getFieldName()).handle(handler);
        } else {
            processFieldValue(fieldMetaData.valueMetaData, handler);
        }
    }

    protected void processFieldValue(FieldValueMetaData valueMetaData, StructHandler handler) throws IOException {
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case BYTE:
                handler.value(valueGenerator.getByte());
                break;
            case SHORT:
                handler.value(valueGenerator.getShort());
                break;
            case INTEGER:
                handler.value(valueGenerator.getInt());
                break;
            case LONG:
                handler.value(valueGenerator.getLong());
                break;
            case DOUBLE:
                handler.value(valueGenerator.getDouble());
                break;
            case BOOLEAN:
                handler.value(valueGenerator.getBoolean());
                break;
            case STRING:
                handler.value(valueGenerator.getString(maxStringLength));
                break;
            case STRUCT:
                processStruct((StructMetaData) valueMetaData, handler);
                break;
            case BINARY:
                handler.value(valueGenerator.getByteArray(maxContainerSize));
                break;
            case LIST:
                ListMetaData listMetaData = TypeUtil.convertType(ListMetaData.class, valueMetaData);
                processList(listMetaData, handler);
                break;
            case SET:
                SetMetaData setMetaData = TypeUtil.convertType(SetMetaData.class, valueMetaData);
                processSet(setMetaData, handler);
                break;
            case MAP:
                MapMetaData mapMetaData = TypeUtil.convertType(MapMetaData.class, valueMetaData);
                processMap(mapMetaData, handler);
                break;
            case ENUM:
                Class<? extends TEnum> tEnumClass = ((EnumMetaData) valueMetaData).getEnumClass();
                handler.value(valueGenerator.getTEnum(tEnumClass).toString());
                break;
            default:
                throw new IllegalStateException(String.format("Type '%s' not found", type));
        }
    }

    private void processStruct(StructMetaData structMetaData, StructHandler handler) throws IOException {
        try {
            TBase tBase = structMetaData.getStructClass().newInstance();
            super.processStruct(tBase, handler);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new IOException(ex);
        }
    }

    private void processList(ListMetaData listMetaData, StructHandler handler) throws IOException {
        FieldValueMetaData valueMetaData = listMetaData.getElementMetaData();
        int size = valueGenerator.getInt(maxContainerSize);
        handler.beginList(size);
        processCollection(size, valueMetaData, handler);
        handler.endList();
    }

    private void processSet(SetMetaData setMetaData, StructHandler handler) throws IOException {
        FieldValueMetaData valueMetaData = setMetaData.getElementMetaData();
        int size = valueGenerator.getInt(maxContainerSize);
        handler.beginSet(size);
        processCollection(size, valueMetaData, handler);
        handler.endSet();
    }

    private void processCollection(int size, FieldValueMetaData valueMetaData, StructHandler handler) throws IOException {
        for (int i = 0; i < size; i++) {
            processFieldValue(valueMetaData, handler);
        }
    }

    private void processMap(MapMetaData mapMetaData, StructHandler handler) throws IOException {
        int size = valueGenerator.getInt(maxContainerSize);
        handler.beginMap(size);
        for (int i = 0; i < size; i++) {
            handler.beginKey();
            processFieldValue(mapMetaData.getKeyMetaData(), handler);
            handler.endKey();
            handler.beginValue();
            processFieldValue(mapMetaData.getValueMetaData(), handler);
            handler.endValue();
        }
        handler.endMap();
    }

    private boolean needProcess(FieldMetaData fieldMetaData) {
        return fieldMetaData.requirementType == TFieldRequirementType.REQUIRED
                || (mode == MockMode.RANDOM && valueGenerator.getBoolean())
                || mode == MockMode.ALL;
    }

}
