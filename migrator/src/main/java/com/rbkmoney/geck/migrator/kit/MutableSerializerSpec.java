package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.SerializerDef;
import com.rbkmoney.geck.migrator.SerializerSpec;

import java.util.Objects;

public class MutableSerializerSpec<I, O> implements SerializerSpec<I, O> {
    private SerializerDef<I> inDef;
    private SerializerDef<O> outDef;

    public MutableSerializerSpec() {
    }

    public MutableSerializerSpec(SerializerDef<I> inDef, SerializerDef<O> outDef) {
        this.inDef = inDef;
        this.outDef = outDef;
    }

    public SerializerDef<I> getInDef() {
        return inDef;
    }

    public SerializerDef<O> getOutDef() {
        return outDef;
    }

    public void setInDef(SerializerDef<I> inDef) {
        this.inDef = inDef;
    }

    public void setOutDef(SerializerDef<O> outDef) {
        this.outDef = outDef;
    }

    @Override
    public String toString() {
        return "SerializerSpec{" +
                "inDef=" + inDef +
                ", outDef=" + outDef +
                '}';
    }
}
