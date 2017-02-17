package com.rbkmoney.geck.serializer.kit.mock;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.kit.tbase.ThriftType;
import org.apache.thrift.*;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by tolkonepiu on 11/02/2017.
 */
public class MockTBaseProcessor extends com.rbkmoney.geck.serializer.kit.tbase.TBaseProcessor {

    private final MockMode mode;

    private final int maxStringLength;

    private final int maxContainerSize;

    public MockTBaseProcessor() {
        this(MockMode.ALL);
    }

    public MockTBaseProcessor(MockMode mode) {
        this(mode, 15, 10);
    }

    public MockTBaseProcessor(MockMode mode, int maxStringLength, int maxContainerSize) {
        this.mode = mode;
        this.maxStringLength = maxStringLength;
        this.maxContainerSize = maxContainerSize;
    }

    @Override
    protected void processUnsetField(TFieldIdEnum tFieldIdEnum, FieldMetaData fieldMetaData, StructHandler handler) throws IOException {
        if (needProcess(fieldMetaData)) {
            handler.name(tFieldIdEnum.getFieldName());
            processFieldValue(fieldMetaData.valueMetaData, handler);
        }
    }

    @Override
    protected void processUnsetUnion(TUnion tUnion, StructHandler handler) throws IOException {
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = tUnion.getFieldMetaData();
        TFieldIdEnum tFieldIdEnum = RandomUtil.randomField(tUnion);
        FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
        handler.name(tFieldIdEnum.getFieldName());
        processFieldValue(fieldMetaData.valueMetaData, handler);
    }

    private void processFieldValue(FieldValueMetaData valueMetaData, StructHandler handler) throws IOException {
        ThriftType type = ThriftType.findByMetaData(valueMetaData);

        switch (type) {
            case BYTE:
                handler.value(RandomUtil.randomByte());
                break;
            case SHORT:
                handler.value(RandomUtil.randomShort());
                break;
            case INTEGER:
                handler.value(RandomUtil.randomInt());
                break;
            case LONG:
                handler.value(RandomUtil.randomLong());
                break;
            case DOUBLE:
                handler.value(RandomUtil.randomDouble());
                break;
            case BOOLEAN:
                handler.value(RandomUtil.randomBoolean());
                break;
            case STRING:
                handler.value(RandomUtil.randomString(maxStringLength));
                break;
            case STRUCT:
                processStruct((StructMetaData) valueMetaData, handler);
                break;
            case BINARY:
                handler.value(RandomUtil.randomByteArray(maxContainerSize));
                break;
            case LIST:
                processList(((ListMetaData) valueMetaData).getElementMetaData(), handler);
                break;
            case SET:
                processList(((SetMetaData) valueMetaData).getElementMetaData(), handler);
                break;
            case MAP:
                processMap((MapMetaData) valueMetaData, handler);
                break;
            case ENUM:
                Class<? extends TEnum> tEnumClass = ((EnumMetaData) valueMetaData).getEnumClass();
                handler.value(RandomUtil.randomTEnum(tEnumClass).toString());
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

    private void processList(FieldValueMetaData valueMetaData, StructHandler handler) throws IOException {
        int size = RandomUtil.randomInt(maxContainerSize);
        handler.beginList(size);
        for (int i = 0; i < size; i++) {
            processFieldValue(valueMetaData, handler);
        }
        handler.endList();
    }

    private void processMap(MapMetaData mapMetaData, StructHandler handler) throws IOException {
        int size = RandomUtil.randomInt(maxContainerSize);
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
                || (mode == MockMode.RANDOM && RandomUtil.randomBoolean())
                || mode == MockMode.ALL;
    }

}
