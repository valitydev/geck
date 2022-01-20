package dev.vality.geck.serializer.kit.object;

import dev.vality.geck.common.util.TypeUtil;
import dev.vality.geck.serializer.StructHandler;
import dev.vality.geck.serializer.StructProcessor;
import dev.vality.geck.serializer.exception.BadFormatException;
import dev.vality.geck.serializer.kit.StructType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static dev.vality.geck.serializer.kit.object.ObjectHandlerConstants.TYPE_DELIMITER;

public class ObjectProcessor implements StructProcessor<Object> {
    @Override
    public <R> R process(Object value, StructHandler<R> handler) throws IOException {
        processStart(value, handler);
        return handler.getResult();
    }

    private void processStart(Object value, StructHandler handler) throws IOException {
        processStruct(TypeUtil.convertType(Map.class, value), handler);
    }

    private void processStruct(Map map, StructHandler handler) throws IOException {
        handler.beginStruct(map.size());
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry entry : entries) {
            String name = String.valueOf(entry.getKey());
            StructType type = getType(name);
            name = clearType(name);
            handler.name(name);
            processValue(entry.getValue(), type, true, handler);
        }
        handler.endStruct();
    }


    private void processMap(List mapList, boolean named, StructHandler handler) throws IOException {
        handler.beginMap(mapList.size());
        Iterator it = mapList.iterator();
        if (!named) {
            assert it.hasNext();
            Object mapSign = it.next();
            assert getType(String.valueOf(mapSign)) == StructType.MAP;
        }
        for (Object entry; it.hasNext(); ) {
            entry = it.next();
            Map mapEntry = TypeUtil.convertType(Map.class, entry);
            handler.beginKey();
            processValue(mapEntry.get(ObjectHandlerConstants.MAP_KEY), StructType.OTHER, false, handler);
            handler.endKey();
            handler.beginValue();
            processValue(mapEntry.get(ObjectHandlerConstants.MAP_VALUE), StructType.OTHER, false, handler);
            handler.endValue();
        }
        handler.endMap();
    }

    private void processList(List list, StructHandler handler) throws IOException {
        handler.beginList(list.size());
        for (Object val : list) {
            processValue(val, StructType.OTHER, false, handler);
        }
        handler.endList();
    }

    private void processSet(Collection set, boolean named, StructHandler handler) throws IOException {
        handler.beginSet(set.size());
        for (Object val : set) {
            processValue(val, StructType.OTHER, false, handler);
        }
        handler.endSet();
    }

    private void processValue(Object value, StructType type, boolean named, StructHandler handler) throws IOException {
        switch (type) {
            case MAP:
                processMap(TypeUtil.convertType(List.class, value), named, handler);
                break;
            case SET:
                processSet(TypeUtil.convertType(Collection.class, value), named, handler);
            case OTHER:
                if (value instanceof Map) {
                    processStruct((Map) value, handler);
                } else if (value instanceof Set) {
                    processSet((Collection) value, named, handler);
                } else if (value instanceof List) {
                    List list = (List) value;
                    if (named) {
                        processList(list, handler);
                    } else {
                        if (list.size() > 0) {
                            StructType lType = getType(String.valueOf(list.get(0)));
                            switch (lType) {
                                case OTHER:
                                    processList(list, handler);
                                    break;
                                default:
                                    processValue(list, lType, named, handler);
                            }
                        } else {
                            processList(list, handler);
                        }
                    }
                } else if (value instanceof String) {
                    handler.value(unescapeString((String) value));
                } else if (value instanceof Number) {
                    if (value instanceof Double || value instanceof Float) {
                        handler.value(((Number) value).doubleValue());
                    } else {
                        handler.value(((Number) value).longValue());
                    }
                } else if (value instanceof Boolean) {
                    handler.value(((Boolean) value).booleanValue());
                } else if (value instanceof ByteBuffer) {
                    handler.value(((ByteBuffer) value).array());
                } else if (value == null) {
                    handler.nullValue();
                } else {
                    throw new BadFormatException("Unrecognised value:" + value);
                }
                break;
            default:
                throw new BadFormatException("Unrecognised type:" + type);
        }
    }


    private StructType getType(String name) {
        int idx = name.indexOf(TYPE_DELIMITER);
        if (idx < 0) {
            return StructType.OTHER;
        } else {
            return StructType.valueOfKey(name.substring(idx + 1, name.length()));
        }
    }

    private String clearType(String name) {
        int idx = name.indexOf(TYPE_DELIMITER);
        if (idx < 0) {
            return name;
        } else {
            return name.substring(0, idx);
        }
    }

    private String unescapeString(String name) {
        int i = 0;
        for (; i < name.length(); ++i) {
            if (name.charAt(i) == ObjectHandlerConstants.ESCAPE_CHAR) {
                break;
            }
        }

        if (i < name.length()) {
            StringBuilder sb = new StringBuilder(name.length());
            sb.append(name, 0, i);
            boolean escaped = false;
            for (; i < name.length(); ++i) {
                char c = name.charAt(i);
                if (escaped) {
                    sb.append(c);
                    escaped = false;
                } else if (c == ObjectHandlerConstants.ESCAPE_CHAR) {
                    escaped = true;
                } else {
                    escaped = false;
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return name;
        }
    }
}
