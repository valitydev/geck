package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public interface Migrator {
    /**
     * @param serializerSpec specifies input data format and expected output data format
     * */
    <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException;

    String getMigrationType();
}
