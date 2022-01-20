package dev.vality.geck.serializer.kit;

import java.util.HashMap;
import java.util.Map;

public enum StructType {
    MAP("map"),
    SET("set"),
    LIST("list"),
    STRUCT("struct"),
    BOOL("bool"),
    STRING("string"),
    DOUBLE("double"),
    LONG("long"),
    NULL("null"),
    BYTEARRAY("bytearray"),
    MAP_ENTRY("map_entry"),
    OTHER("");
    private static class Holder {
        static Map<String, StructType> MAP = new HashMap<>();
    }

    private String key;

    public static StructType valueOfKey(String code) {
        return Holder.MAP.get(code);
    }

    StructType(String key) {
        this.key = key;
        Holder.MAP.put(key, this);
    }

    public String getKey() {
        return key;
    }
}
