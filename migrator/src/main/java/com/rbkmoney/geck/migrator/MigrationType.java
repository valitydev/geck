package com.rbkmoney.geck.migrator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public enum MigrationType {
    JOLT("JOLT"),
    JOBJECT("JOBJECT"),
    TS_JAVA("TS_JAVA"),
    NOP("NOP");

    private static class Holder {
        static Map<String, MigrationType> MAP = new HashMap<>();
    }

    private String key;

    public static MigrationType valueOfKey(String key) {
        return Holder.MAP.get(key);
    }

    MigrationType(String key) {
        this.key = key;
        Holder.MAP.put(key, this);
    }

    public String getKey() {
        return key;
    }
}
