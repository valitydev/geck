package com.rbkmoney.geck.migrator;


public interface MigrationManager {
    <I, O> O migrate(I src, ThriftSpec thriftSpec, SerializerSpec<I, O> serializerSpec) throws MigrationException;
    <T> T migrate(T src, ThriftDef thriftDef, SerializerDef<T> serializerDef) throws MigrationException;
}
