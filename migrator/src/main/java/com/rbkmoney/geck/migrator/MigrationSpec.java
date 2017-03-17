package com.rbkmoney.geck.migrator;

/**
 * Created by vpankrashkin on 01.03.17.
 */
public interface MigrationSpec<T> {
    T getSpec();
    String getType();
}
