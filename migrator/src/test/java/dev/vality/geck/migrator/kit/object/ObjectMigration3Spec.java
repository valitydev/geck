package dev.vality.geck.migrator.kit.object;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.ThriftDef;
import dev.vality.geck.migrator.ThriftSpec;

import java.util.Map;

public class ObjectMigration3Spec implements ObjectSpec {
    @Override
    public Object apply(Object in) throws MigrationException {
        if(in instanceof Map) {
            ((Map) in).put("v4_field", "v4");
        }
        return in;
    }

    @Override
    public ThriftSpec getThriftSpec() {
        return new ThriftSpec(new ThriftDef(3), new ThriftDef(4));
    }
}
