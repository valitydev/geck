package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationPoint;
import com.rbkmoney.geck.migrator.MigrationSpec;
import com.rbkmoney.geck.migrator.ThriftSpec;

import java.util.List;
import java.util.Map;

/**
 * Created by vpankrashkin on 06.03.17.
 *
 */
public interface MigrationPointProvider {
    /**
     * Get all thrift version transitions known to this provider. If provider knows about specific thrift version but doesn't have migration spec,
     * this version must be included too.
     * */
    List<ThriftSpec> getSpecs() throws MigrationException;

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
