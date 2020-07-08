package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationPoint;
import com.rbkmoney.geck.migrator.ThriftSpec;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleMigrationPointProvider implements MigrationPointProvider {
    private final Map<ThriftSpec, MigrationPoint> pointMap;

    public SimpleMigrationPointProvider(Collection<MigrationPoint> migrationPoints) {
        pointMap = Collections.unmodifiableMap(
                migrationPoints.stream().collect(Collectors.toMap(mp -> mp.getThriftSpec(), Function.identity()))
        );
    }

    @Override
    public Collection<ThriftSpec> getSpecs() throws MigrationException {
        return pointMap.keySet();
    }

    @Override
    public Map<ThriftSpec, MigrationPoint> getSpecMapping() throws MigrationException {
        return pointMap;
    }

    @Override
    public MigrationPoint getMappedSpec(ThriftSpec thriftSpec) throws MigrationException {
        return pointMap.get(thriftSpec);
    }
}
