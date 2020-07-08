package com.rbkmoney.geck.migrator;


public class MigrationPoint {
    private final long id;
    private final ThriftSpec thriftSpec;
    private final SerializerDef serializerDef;
    private final MigrationSpec migrationSpec;

    public MigrationPoint(int id, ThriftSpec thriftSpec, SerializerDef serializerDef, MigrationSpec migrationSpec) {
        this.id = id;
        this.thriftSpec = thriftSpec;
        this.serializerDef = serializerDef;
        this.migrationSpec = migrationSpec;
    }

    public long getId() {
        return id;
    }

    public ThriftSpec getThriftSpec() {
        return thriftSpec;
    }

    public SerializerDef getSerializerDef() {
        return serializerDef;
    }

    public String getMigrationType() {
        return migrationSpec.getType();
    }

    public MigrationSpec getMigrationSpec() {
        return migrationSpec;
    }
}
