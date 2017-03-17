package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.ThriftDef;

/**
 * Created by vpankrashkin on 16.03.17.
 */
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
