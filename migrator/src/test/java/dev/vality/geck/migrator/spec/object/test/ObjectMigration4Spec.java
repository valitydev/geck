package dev.vality.geck.migrator.spec.object.test;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.ThriftDef;
import dev.vality.geck.migrator.ThriftSpec;
import dev.vality.geck.migrator.kit.object.ObjectSpec;

import java.util.Map;

public class ObjectMigration4Spec implements ObjectSpec {
    @Override
    public Object apply(Object in) throws MigrationException {
        if(in instanceof Map) {
            ((Map) in).put("v5_field", "v5");
        }
        return in;
    }

    @Override
    public ThriftSpec getThriftSpec() {
        return new ThriftSpec(new ThriftDef(4), new ThriftDef(5));
    }
}
