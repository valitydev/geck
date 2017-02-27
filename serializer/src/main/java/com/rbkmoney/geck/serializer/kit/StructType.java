package com.rbkmoney.geck.serializer.kit;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpankrashkin on 22.02.17.
 */
public enum StructType {
    MAP("map"),
    SET("set"),
    LIST("list"),
    STRUCT("struct"),
    STRING("string"),
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
