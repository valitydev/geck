package com.rbkmoney.geck.migrator;

import java.util.Objects;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public class ThriftDef {
    private final String type;
    private final int version;

    public ThriftDef(int version) {
        this(null, version);
    }

    public ThriftDef(String type, int version) {
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
        if (!(o instanceof ThriftDef)) return false;
        ThriftDef thriftDef = (ThriftDef) o;
        return version == thriftDef.version &&
                Objects.equals(type, thriftDef.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version);
    }

    @Override
    public String toString() {
        return "ThriftDef{" +
                "type='" + type + '\'' +
                ", version=" + version +
                '}';
    }
}
