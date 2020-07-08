package com.rbkmoney.geck.migrator;

import java.util.Objects;


public class SerializerDef<T> {
    private final String type;

    public SerializerDef(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializerDef)) return false;
        SerializerDef<?> that = (SerializerDef<?>) o;
        return Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return "SerializerDef{" +
                "type='" + type + '\'' +
                '}';
    }
}
