package com.rbkmoney.geck.migrator;

import java.util.List;

public interface MigrationStore {
    List<MigrationPoint> getMigrations(ThriftSpec thriftSpec) throws MigrationException;
    int getLastVersion() throws MigrationException;
}
