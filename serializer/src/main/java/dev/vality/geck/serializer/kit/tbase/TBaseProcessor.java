package dev.vality.geck.serializer.kit.tbase;

import dev.vality.geck.common.util.BinaryUtil;
import dev.vality.geck.common.util.TBaseUtil;
import dev.vality.geck.common.util.TypeUtil;
import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.StructProcessor;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.TUnion;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

public class TBaseProcessor implements StructProcessor<TBase> {

    private final boolean checkRequiredFields;

    public TBaseProcessor() {
        this(true);
    }

    public TBaseProcessor(boolean checkRequiredFields) {
        this.checkRequiredFields = checkRequiredFields;
    }

    @Override
    public <R> R process(TBase value, StructHandler<R> handler) throws IOException {
        if (value == null) {
            handler.nullValue();
        } else {
            processStruct(value, handler, newIdentitySet());
        }

        return handler.getResult();
    }

    protected void processStruct(TBase value, StructHandler handler) throws IOException {
        processStruct(value, handler, newIdentitySet());
    }

    protected void processStruct(TBase value, StructHandler handler, Set<TBase> structsInProgress) throws IOException {
        if (!structsInProgress.add(value)) {
            throw new IllegalStateException(String.format(
                    "Cyclic reference detected while processing thrift struct '%s'",
                    value.getClass().getName()
            ));
        }
        try {
            TFieldIdEnum[] tFieldIdEnums = value.getFields();
            Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = value.getFieldMetaData();

            int size = TBaseUtil.getSetFieldsCount(value);
            handler.beginStruct(size);

            if (value instanceof TUnion) {
                TUnion union = (TUnion) value;
                if (union.isSet()) {
                    TFieldIdEnum tFieldIdEnum = union.getSetField();
                    handler.name((byte) tFieldIdEnum.getThriftFieldId(), tFieldIdEnum.getFieldName());
                    process(union.getFieldValue(), fieldMetaDataMap.get(tFieldIdEnum).valueMetaData, handler,
                            structsInProgress);
                } else {
                    processUnsetUnion(union, handler);
                }
            } else {
                for (TFieldIdEnum tFieldIdEnum : tFieldIdEnums) {
                    FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
                    if (value.isSet(tFieldIdEnum)) {
                        handler.name((byte) tFieldIdEnum.getThriftFieldId(), tFieldIdEnum.getFieldName());
                        process(value.getFieldValue(tFieldIdEnum), fieldMetaData.valueMetaData, handler,
                                structsInProgress);
                    } else {
                        processUnsetField(tFieldIdEnum, fieldMetaData, handler);
                    }
                }
            }

            handler.endStruct();
        } finally {
            structsInProgress.remove(value);
        }
    }

    protected void processUnsetUnion(TUnion tUnion, StructHandler handler) throws IOException {
        throw new IllegalStateException(String.format("one of fields in union '%s' must be set", tUnion.getClass().getSimpleName()));
    }

    protected void processUnsetField(TFieldIdEnum tFieldIdEnum, FieldMetaData fieldMetaData, StructHandler handler) throws IOException {
        if (checkRequiredFields && fieldMetaData.requirementType == TFieldRequirementType.REQUIRED) {
            throw new IllegalStateException(String.format("field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
        }
    }

    private void process(
            Object object,
            FieldValueMetaData fieldValueMetaData,
            StructHandler handler,
            Set<TBase> structsInProgress) throws IOException {
        if (object == null) {
            handler.nullValue();
        } else if (object instanceof Optional<?> optional) {
            if (optional.isPresent()) {
                Object value = optional.get();
                process(value, fieldValueMetaData, handler, structsInProgress);
            } else {
                handler.nullValue();
            }
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
                case ENUM, OLD_ENUM:
                    handler.value(object.toString());
                    break;
                case BINARY:
                    if (object instanceof byte[]) {
                        handler.value((byte[]) object);
                    } else if (object instanceof ByteBuffer) {
                        handler.value(BinaryUtil.toByteArray((ByteBuffer) object));
                    } else {
                        throw new IllegalStateException(String.format("Unknown binary type, type='%s'", object.getClass().getName()));
                    }
                    break;
                case LIST:
                    List list = TypeUtil.convertType(List.class, object);
                    ListMetaData listMetaData = TypeUtil.convertType(ListMetaData.class, fieldValueMetaData);
                    processList(list, listMetaData, handler, structsInProgress);
                    break;
                case SET:
                    Set set = TypeUtil.convertType(Set.class, object);
                    SetMetaData setMetaData = TypeUtil.convertType(SetMetaData.class, fieldValueMetaData);
                    processSet(set, setMetaData, handler, structsInProgress);
                    break;
                case MAP:
                    processMap((Map) object, (MapMetaData) fieldValueMetaData, handler, structsInProgress);
                    break;
                case STRUCT:
                    processStruct((TBase) object, handler, structsInProgress);
                    break;
                default:
                    throw new IllegalStateException(String.format("Type '%s' not found", type));
            }
        }
    }

    private void processList(
            List list,
            ListMetaData listMetaData,
            StructHandler handler,
            Set<TBase> structsInProgress) throws IOException {
        handler.beginList(list.size());
        processCollection(list, listMetaData.getElementMetaData(), handler, structsInProgress);
        handler.endList();
    }

    private void processSet(
            Set set,
            SetMetaData setMetaData,
            StructHandler handler,
            Set<TBase> structsInProgress) throws IOException {
        handler.beginSet(set.size());
        processCollection(set, setMetaData.getElementMetaData(), handler, structsInProgress);
        handler.endSet();
    }

    private void processCollection(
            Collection collection,
            FieldValueMetaData valueMetaData,
            StructHandler handler,
            Set<TBase> structsInProgress) throws IOException {
        for (Object object : collection) {
            process(object, valueMetaData, handler, structsInProgress);
        }
    }

    private void processMap(
            Map objectMap,
            MapMetaData metaData,
            StructHandler handler,
            Set<TBase> structsInProgress) throws IOException {
        handler.beginMap(objectMap.size());
        for (Map.Entry entry : (Set<Map.Entry>) objectMap.entrySet()) {
            handler.beginKey();
            process(entry.getKey(), metaData.getKeyMetaData(), handler, structsInProgress);
            handler.endKey();
            handler.beginValue();
            process(entry.getValue(), metaData.getValueMetaData(), handler, structsInProgress);
            handler.endValue();
        }
        handler.endMap();
    }

    private static <T> Set<T> newIdentitySet() {
        return Collections.newSetFromMap(new IdentityHashMap<>());
    }

}
