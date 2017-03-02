package com.rbkmoney.geck.migrator;

import java.util.Objects;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public class SerializerDef<T> {
    private final String type;
    private final int version;

    public SerializerDef(String type, int version) {
        this.type = type;
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public int getVersion() {
        return version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializerDef)) return false;
        SerializerDef that = (SerializerDef) o;
        return version == that.version &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version);
    }

    @Override
    public String toString() {
        return "SerializerDef{" +
                "type='" + type + '\'' +
                ", version=" + version +
                '}';
    }
}
