package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.ThriftDef;

public class MutableThriftDef extends ThriftDef {
    private int version;
    private String type;
    public MutableThriftDef(int version) {
        super(0);
        this.version = version;
    }

    public MutableThriftDef(String type, int version) {
        super(0, null);
        this.type = type;
        this.version = version;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setType(String type) {
        this.type = type;
    }
}
