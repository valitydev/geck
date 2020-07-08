package com.rbkmoney.geck.migrator;

import java.util.Objects;


public class ThriftSpec {
    private final ThriftDef inDef;
    private final ThriftDef outDef;

    public ThriftSpec(ThriftDef inDef, ThriftDef outDef) {
        this.inDef = inDef;
        this.outDef = outDef;
    }

    public ThriftDef getInDef() {
        return inDef;
    }

    public ThriftDef getOutDef() {
        return outDef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ThriftSpec)) return false;
        ThriftSpec that = (ThriftSpec) o;
        return Objects.equals(inDef, that.inDef) &&
                Objects.equals(outDef, that.outDef);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inDef, outDef);
    }

    @Override
    public String toString() {
        return "ThriftSpec{" +
                "inDef=" + inDef +
                ", outDef=" + outDef +
                '}';
    }
}
