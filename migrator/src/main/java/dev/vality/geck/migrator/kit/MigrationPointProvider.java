package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.MigrationPoint;
import dev.vality.geck.migrator.ThriftSpec;

import java.util.Collection;
import java.util.Map;

public interface MigrationPointProvider {
    /**
     * Get all thrift version transitions known to this provider. If provider knows about specific thrift version but doesn't have migration spec,
     * this version must be included too.
     * */
    Collection<ThriftSpec> getSpecs() throws MigrationException;

    /**
     * Get all Migration Specs known to this provider. If some thrift version doesn't need migration, it is acceptable to return stub migration for this version transition.
     * Be aware that full MigrationSpec extraction can be time consuming.
     * @return Map, containing ThriftSpec (atomic thrift version transition) as a key and MigrationPoint as a value.
     * @throws MigrationException if any error occurs
     * */
    Map<ThriftSpec, MigrationPoint> getSpecMapping() throws MigrationException;

    /**
     * Get Migration Spec matching at least thrift version transition for this thrift spec.
     * Returns only exact ThriftSpec match.
     * @return MigrationPoint - if matching thrift spec was found; null - otherwise
     * @throws MigrationException if any error occurs
     * */
    MigrationPoint getMappedSpec(ThriftSpec thriftSpec) throws MigrationException;
}
