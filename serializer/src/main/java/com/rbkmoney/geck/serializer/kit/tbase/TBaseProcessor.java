package com.rbkmoney.geck.serializer.kit.tbase;

import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.kit.ObjectUtil;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class TBaseProcessor implements StructProcessor<TBase> {

    @Override
    public <R> R process(TBase value, StructHandler<R> handler) throws IOException {
        if (value == null) {
            handler.nullValue();
        } else {
            processStruct(value, handler);
        }

        return handler.getResult();
    }

    protected void processStruct(TBase value, StructHandler handler) throws IOException {
        TFieldIdEnum[] tFieldIdEnums = value.getFields();
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = value.getFieldMetaData();

        int size = TBaseUtil.getSetFieldsCount(value);
        handler.beginStruct(size);

        if (value instanceof TUnion) {
            TUnion union = (TUnion) value;
            if (union.isSet()) {
                TFieldIdEnum tFieldIdEnum = union.getSetField();
                handler.name(tFieldIdEnum.getFieldName());
                process(union.getFieldValue(), fieldMetaDataMap.get(tFieldIdEnum).valueMetaData, handler);
            } else {
                processUnsetUnion(union, handler);
            }
        } else {
            for (TFieldIdEnum tFieldIdEnum : tFieldIdEnums) {
                FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
                if (value.isSet(tFieldIdEnum)) {
                    handler.name(tFieldIdEnum.getFieldName());
                    process(value.getFieldValue(tFieldIdEnum), fieldMetaData.valueMetaData, handler);
                } else {
                    processUnsetField(tFieldIdEnum, fieldMetaData, handler);
                }
            }
        }

        handler.endStruct();
    }

    protected void processUnsetUnion(TUnion tUnion, StructHandler handler) throws IOException {
        throw new IllegalStateException(String.format("one of fields in union '%s' must be set", tUnion.getClass().getSimpleName()));
    }

    protected void processUnsetField(TFieldIdEnum tFieldIdEnum, FieldMetaData fieldMetaData, StructHandler handler) throws IOException {
        if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED) {
            throw new IllegalStateException(String.format("field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
        }
    }

    private void process(Object object, FieldValueMetaData fieldValueMetaData, StructHandler handler) throws IOException {
        if (object == null) {
            handler.nullValue();
        } else {
            ThriftType type = ThriftType.findByMetaData(fieldValueMetaData);

            switch (type) {
                case BOOLEAN:
                    handler.value((boolean) object);
                    break;
                case STRING:
                    handler.value((String) object);
                    break;
                case BYTE:
                    handler.value((byte) object);
                    break;
                case SHORT:
                    handler.value((short) object);
                    break;
                case INTEGER:
                    handler.value((int) object);
                    break;
                case LONG:
                    handler.value((long) object);
                    break;
                case DOUBLE:
                    handler.value((double) object);
                    break;
                case ENUM:
                    handler.value(object.toString());
                    break;
                case BINARY:
                    handler.value((byte[]) object);
                    break;
                case LIST:
                    List list = ObjectUtil.convertType(List.class, object);
                    ListMetaData listMetaData = ObjectUtil.convertType(ListMetaData.class, fieldValueMetaData);
                    processList(list, listMetaData, handler);
                    break;
                case SET:
                    Set set = ObjectUtil.convertType(Set.class, object);
                    SetMetaData setMetaData = ObjectUtil.convertType(SetMetaData.class, fieldValueMetaData);
                    processSet(set, setMetaData, handler);
                    break;
                case MAP:
                    processMap((Map) object, (MapMetaData) fieldValueMetaData, handler);
                    break;
                case STRUCT:
                    processStruct((TBase) object, handler);
                    break;
                default:
                    throw new IllegalStateException(String.format("Type '%s' not found", type));
            }
        }
    }

    private void processList(List list, ListMetaData listMetaData, StructHandler handler) throws IOException {
        handler.beginList(list.size());
        processCollection(list, listMetaData.getElementMetaData(), handler);
        handler.endList();
    }

    private void processSet(Set set, SetMetaData setMetaData, StructHandler handler) throws IOException {
        handler.beginSet(set.size());
        processCollection(set, setMetaData.getElementMetaData(), handler);
        handler.endSet();
    }

    private void processCollection(Collection collection, FieldValueMetaData valueMetaData, StructHandler handler) throws IOException {
        for (Object object : collection) {
            process(object, valueMetaData, handler);
        }
    }

    private void processMap(Map objectMap, MapMetaData metaData, StructHandler handler) throws IOException {
        handler.beginMap(objectMap.size());
        for (Map.Entry entry : (Set<Map.Entry>) objectMap.entrySet()) {
            handler.beginKey();
            process(entry.getKey(), metaData.getKeyMetaData(), handler);
            handler.endKey();
            handler.beginValue();
            process(entry.getValue(), metaData.getValueMetaData(), handler);
            handler.endValue();
        }
        handler.endMap();
    }


}
