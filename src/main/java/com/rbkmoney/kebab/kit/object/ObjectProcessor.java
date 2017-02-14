package com.rbkmoney.kebab.kit.object;

import com.rbkmoney.kebab.StructHandler;
import com.rbkmoney.kebab.StructProcessor;
import com.rbkmoney.kebab.exception.BadFormatException;
import com.rbkmoney.kebab.kit.ObjectUtil;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.rbkmoney.kebab.kit.object.ObjectHandlerConstants.*;

/**
 * Created by vpankrashkin on 10.02.17.
 */
public class ObjectProcessor implements StructProcessor<Object> {
    @Override
    public <R> R process(Object value, StructHandler<R> handler) throws IOException {
        processStart(value, handler);
        return handler.getResult();
    }

    private void processStart(Object value, StructHandler handler) throws IOException {
        processStruct(ObjectUtil.convertType(Map.class, value), handler);
    }

    private void processStruct(Map map, StructHandler handler) throws IOException {
        handler.beginStruct(map.size());
        Set<Map.Entry> entries = map.entrySet();
        for (Map.Entry entry: entries) {
            String name = String.valueOf(entry.getKey());
            boolean isMap = isMap(name);
            name = unescapeName(name, isMap);
            handler.name(name);
            if (isMap) {
                processMap(ObjectUtil.convertType(List.class, entry.getValue()), true, handler);
            } else {
                processValue(entry.getValue(), handler, false);
            }
        }
        handler.endStruct();
    }

    private void processMap(List mapList, boolean named, StructHandler handler) throws IOException {
        handler.beginMap(mapList.size());
        Iterator it = mapList.iterator();
        if (!named) {
            assert it.hasNext();
            Object mapSign = it.next();
            assert isMap(String.valueOf(mapSign));
        }
        for (Object entry; it.hasNext();) {
            entry = it.next();
            Map mapEntry = ObjectUtil.convertType(Map.class, entry);

            handler.beginKey();
            processValue(mapEntry.get(MAP_KEY), handler, true);
            handler.endKey();
            handler.beginValue();
            processValue(mapEntry.get(MAP_VALUE), handler, true);
            handler.endValue();
        }
        handler.endMap();
    }

    private void processList(List list, StructHandler handler) throws IOException {
        handler.beginList(list.size());
        for (Object val: list) {
            processValue(val, handler, true);
        }
        handler.endList();
    }

    private void processValue(Object value, StructHandler handler, boolean checkMap) throws IOException {
        if (value instanceof Map) {
            processStruct((Map) value, handler);
        } else if (value instanceof List) {
            List list = (List) value;
            if (checkMap && list.size() > 0 && isMap(String.valueOf(list.get(0)))) {
                processMap(list, false, handler);
            } else {
                processList(list, handler);
            }
        } else if (value instanceof String) {
            handler.value((String) value);
        } else if (value instanceof Number) {
            if (value instanceof Double || value instanceof Float) {
                handler.value(((Number)value).doubleValue());
            } else {
                handler.value(((Number)value).longValue());
            }
        } else if (value instanceof Boolean) {
            handler.value(((Boolean)value).booleanValue());
        } else if (value instanceof byte[]) {
            handler.value((byte[]) value);
        } else if (value == null) {
            handler.nullValue();
        } else {
            throw new BadFormatException("Unrecognised value:"+value);
        }
    }


    private boolean isMap(String name) {
        if (name != null && name.endsWith(MAP_MARK)) {
            if (name.length() == MAP_MARK.length()) {
                return true;
            } else {
                boolean escaped = false;
                for (int i = name.length() - MAP_MARK.length() - 1; i >= 0; --i) {
                   if (name.charAt(i) == ESCAPE_CHAR) {
                       escaped ^= true;
                   } else {
                       break;
                   }
                }
                return !escaped;
            }
        }
        return false;
    }

    private String unescapeName(String name, boolean isMap) {
        int i = 0;
        int usedLength = name.length() - (isMap ? MAP_MARK.length() : 0);
        for (; i < usedLength; ++i) {
            if (name.charAt(i) == ESCAPE_CHAR) {
                break;
            }
        }

        if (i < usedLength) {
            StringBuilder sb = new StringBuilder(usedLength);
            sb.append(name, 0, i);
            boolean escaped = false;
            for (;i < usedLength; ++i) {
                char c = name.charAt(i);
                if (c == ESCAPE_CHAR) {
                    if ((escaped ^= true)) {
                        sb.append(ESCAPE_CHAR);
                    }
                } else {
                    escaped = false;
                    sb.append(c);
                }
            }
            return sb.toString();
        } else {
            return isMap ? name.substring(0, usedLength) : name;
        }
    }
}
