package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public interface Migrator<T> {
    T migrate(T data, MigrationPoint mPoint) throws MigrationException;
}
