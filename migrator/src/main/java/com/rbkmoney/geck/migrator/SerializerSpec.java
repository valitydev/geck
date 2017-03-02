package com.rbkmoney.geck.migrator;

import java.util.Objects;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public class SerializerSpec<I, O> {
    private final SerializerDef<I> inDef;
    private final SerializerDef<O> outDef;

    public SerializerSpec(SerializerDef<I> inDef, SerializerDef<O> outDef) {
        this.inDef = inDef;
        this.outDef = outDef;
    }

    public SerializerDef<I> getInDef() {
        return inDef;
    }

    public SerializerDef<O> getOutDef() {
        return outDef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SerializerSpec)) return false;
        SerializerSpec that = (SerializerSpec) o;
        return Objects.equals(inDef, that.inDef) &&
                Objects.equals(outDef, that.outDef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inDef, outDef);
    }

    @Override
    public String toString() {
        return "SerializerSpec{" +
                "inDef=" + inDef +
                ", outDef=" + outDef +
                '}';
    }
}
