package com.rbkmoney.kebab.serializer;

import com.rbkmoney.kebab.Serializer;
import com.rbkmoney.kebab.StructWriter;
import com.rbkmoney.kebab.ThriftType;
import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;
import org.apache.thrift.TFieldRequirementType;
import org.apache.thrift.meta_data.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by tolkonepiu on 27/01/2017.
 */
public class TBaseSerializer implements Serializer<TBase> {

    @Override
    public void write(StructWriter out, TBase value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }

        writeStruct(out, value);
    }

    private void writeStruct(StructWriter out, TBase value) throws IOException {
        out.beginStruct();

        TFieldIdEnum[] tFieldIdEnums = value.getFields();
        Map<TFieldIdEnum, FieldMetaData> fieldMetaDataMap = value.getFieldMetaData();

        for (TFieldIdEnum tFieldIdEnum : tFieldIdEnums) {
            FieldMetaData fieldMetaData = fieldMetaDataMap.get(tFieldIdEnum);
            if (value.isSet(tFieldIdEnum)) {
                out.name(tFieldIdEnum.getFieldName());
                write(out, value.getFieldValue(tFieldIdEnum), fieldMetaData.valueMetaData);
            } else if (fieldMetaData.requirementType == TFieldRequirementType.REQUIRED) {
                throw new IllegalStateException(String.format("Field '%s' is required and must not be null", tFieldIdEnum.getFieldName()));
            }
        }
        out.endStruct();
    }

    private void write(StructWriter out, Object object, FieldValueMetaData fieldValueMetaData) throws IOException {
        if (object == null) {
            out.nullValue();
            return;
        }

        ThriftType type = ThriftType.findByCode(fieldValueMetaData.getType());
        boolean isBinary = fieldValueMetaData.isBinary();

        if (isBinary) {
            out.value((byte[]) object);
        } else {
            switch (type) {
                case BOOLEAN:
                    out.value((boolean) object);
                    break;
                case STRING:
                    out.value((String) object);
                    break;
                case BYTE:
                    out.value((byte) object);
                    break;
                case SHORT:
                    out.value((short) object);
                    break;
                case INTEGER:
                    out.value((int) object);
                    break;
                case LONG:
                    out.value((long) object);
                    break;
                case DOUBLE:
                    out.value((double) object);
                    break;
                case ENUM:
                    out.value(object.toString());
                    break;
                case LIST:
                    writeList(out, (List) object, (ListMetaData) fieldValueMetaData);
                    break;
                case SET:
                    writeSet(out, (Set) object, (SetMetaData) fieldValueMetaData);
                    break;
                case MAP:
                    writeMap(out, (Map) object, (MapMetaData) fieldValueMetaData);
                    break;
                case STRUCT:
                    writeStruct(out, (TBase) object);
                    break;
                default:
                    throw new IllegalStateException(String.format("Type '%s' not found", type));
            }
        }
    }

    private void writeSet(StructWriter out, Set objectSet, SetMetaData metaData) throws IOException {
        out.beginList(objectSet.size());
        for (Object object : objectSet) {
            write(out, object, metaData.getElementMetaData());
        }
        out.endList();
    }

    private void writeList(StructWriter out, List objectList, ListMetaData metaData) throws IOException {
        out.beginList(objectList.size());
        for (Object object : objectList) {
            write(out, object, metaData.getElementMetaData());
        }
        out.endList();
    }


    private void writeMap(StructWriter out, Map objectMap, MapMetaData metaData) throws IOException {
        out.beginMap(objectMap.size());
        for (Map.Entry entry : (Set<Map.Entry>) objectMap.entrySet()) {
            out.beginKey();
            write(out, entry.getKey(), metaData.getKeyMetaData());
            out.endKey();
            out.beginValue();
            write(out, entry.getValue(), metaData.getValueMetaData());
            out.endValue();
        }
        out.endMap();
    }


}
