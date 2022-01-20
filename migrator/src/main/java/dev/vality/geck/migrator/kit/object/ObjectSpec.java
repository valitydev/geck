package dev.vality.geck.migrator.kit.object;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.ThriftSpec;

public interface ObjectSpec {
    Object apply(Object in) throws MigrationException;
    ThriftSpec getThriftSpec();
}
