package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public class MigrationPoint {
    private final int id;
    private final ThriftSpec thriftSpec;
    private final MigrationType migrationType;
    private final MigrationSpec migrationSpec;

    public MigrationPoint(int id, ThriftSpec thriftSpec, MigrationType migrationType, MigrationSpec migrationSpec) {
        this.id = id;
        this.thriftSpec = thriftSpec;
        this.migrationType = migrationType;
        this.migrationSpec = migrationSpec;
    }

    public int getId() {
        return id;
    }

    public ThriftSpec getThriftSpec() {
        return thriftSpec;
    }

    public MigrationType getMigrationType() {
        return migrationType;
    }

    public MigrationSpec getMigrationSpec() {
        return migrationSpec;
    }


}
