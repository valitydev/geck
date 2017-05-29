package com.rbkmoney.geck.serializer.kit.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.rbkmoney.geck.serializer.StructHandler;
import com.rbkmoney.geck.serializer.StructProcessor;
import com.rbkmoney.geck.serializer.exception.BadFormatException;
import com.rbkmoney.geck.serializer.kit.StructType;

import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by inalarsanukaev on 16.03.17.
 */
public class JsonProcessor implements StructProcessor<JsonNode> {

    @Override
    public <R> R process(JsonNode value, StructHandler<R> handler) throws IOException {
        processNode(value, null, handler);
        return handler.getResult();
    }

    private void processNode(JsonNode jsonNode, String name, StructHandler handler) throws IOException {
        if (jsonNode != null) {
            if (name != null) {
                handler.name(name);
            }
            if (jsonNode.isNull()) {
                handler.nullValue();
            } else if (jsonNode.isBoolean()) {
                handler.value(jsonNode.booleanValue());
            } else if (jsonNode.isLong() || jsonNode.isInt()) {
                handler.value(jsonNode.longValue());
            } else if (jsonNode.isDouble()) {
                handler.value(jsonNode.doubleValue());
            } else if (jsonNode.isTextual()) {
                String value = jsonNode.textValue();
                if (value.startsWith(JsonHandler.ESC_SYMBOL)) {
                    String data = value.substring(1);
                    if (data.startsWith(JsonHandler.ESC_SYMBOL)) {
                        handler.value(data);
                    } else {
                        try {
                            handler.value(Base64.getDecoder().decode(data));
                        } catch (IllegalArgumentException e) {
                            throw new BadFormatException("Error when decode base64 field " + (name == null ? "" : name), e);
                        }
                    }
                } else {
                    handler.value(value);
                }
            } else if (jsonNode.isObject()) {
                handler.beginStruct(jsonNode.size());
                processChildStructNodes(jsonNode.fields(), handler);
                handler.endStruct();
            } else if (jsonNode.isArray()) {
                Iterator<JsonNode> elements = jsonNode.elements();
                if (!elements.hasNext()) {
                    throw new BadFormatException("Incorrect structure of array. First element should exist!");
                }
                String arrCode = elements.next().textValue();
                StructType arrType = StructType.valueOfKey(arrCode);
                int size = jsonNode.size() - 1;
                switch (arrType) {
                    case LIST:
                        handler.beginList(size);
                        processChildArrNodes(elements, handler);
                        handler.endList();
                        break;
                    case SET:
                        handler.beginSet(size);
                        processChildArrNodes(elements, handler);
                        handler.endSet();
                        break;
                    case MAP:
                        handler.beginMap(size);
                        while (elements.hasNext()) {
                            JsonNode mapEntry = elements.next();
                            handler.beginKey();
                            processNode(mapEntry.get(JsonHandler.KEY), null, handler);
                            handler.endKey();
                            handler.beginValue();
                            processNode(mapEntry.get(JsonHandler.VALUE), null, handler);
                            handler.endValue();
                        }
                        handler.endMap();
                        break;
                    default:
                        new BadFormatException("Unknown type of node: " + arrType + ". Must be on of them : " + StructType.LIST + ", " + StructType.SET + ", " + StructType.MAP);
                }
            }
        }
    }

    private void processChildArrNodes(Iterator<JsonNode> elements, StructHandler handler) throws IOException {
        while (elements.hasNext()) {
            processNode(elements.next(), null, handler);
        }
    }

    private void processChildStructNodes(Iterator<Map.Entry<String, JsonNode>> fields, StructHandler handler) throws IOException {
        while (fields.hasNext()) {
            Map.Entry<String, JsonNode> next = fields.next();
            processNode(next.getValue(), next.getKey(), handler);
        }
    }
}
