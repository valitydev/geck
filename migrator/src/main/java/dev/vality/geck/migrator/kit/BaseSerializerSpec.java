package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.SerializerDef;
import dev.vality.geck.migrator.SerializerSpec;

import java.util.Objects;

public class BaseSerializerSpec<I, O> implements SerializerSpec<I, O> {
    private final SerializerDef<I> inDef;
    private final SerializerDef<O> outDef;

    public BaseSerializerSpec(SerializerDef<I> inDef, SerializerDef<O> outDef) {
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
        if (!(o instanceof BaseSerializerSpec)) return false;
        BaseSerializerSpec that = (BaseSerializerSpec) o;
        return Objects.equals(inDef, that.inDef)
                && Objects.equals(outDef, that.outDef);
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
