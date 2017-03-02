package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 02.03.17.
 */
public interface MigrationManager {
    <I, O> O migrate(I src, ThriftSpec thriftSpec, SerializerSpec<I, O> serializerSpec) throws MigrationException;
    <T> T migrate(T src, ThriftDef thriftDef, SerializerDef<T> serializerDef) throws MigrationException;
}
