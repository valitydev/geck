package dev.vality.geck.migrator.spec.object;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.ThriftDef;
import dev.vality.geck.migrator.ThriftSpec;
import dev.vality.geck.migrator.kit.object.ObjectSpec;

import java.util.Map;

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
