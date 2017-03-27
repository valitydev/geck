package com.rbkmoney.geck.migrator.spec.object;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.ThriftDef;
import com.rbkmoney.geck.migrator.ThriftSpec;
import com.rbkmoney.geck.migrator.kit.object.ObjectSpec;

import java.util.Map;

/**
 * Created by vpankrashkin on 24.03.17.
 */
public class ObjectMigration0Spec implements ObjectSpec {
    @Override
    public Object apply(Object in) throws MigrationException {
        if(in instanceof Map) {
            ((Map) in).put("v1_field", "v1");
        }
        return in;
    }

    @Override
    public ThriftSpec getThriftSpec() {
        return new ThriftSpec(new ThriftDef(0), new ThriftDef(1));
    }
}
