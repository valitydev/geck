package com.rbkmoney.geck.migrator;

public interface MigrationSpec<T> {
    T getSpec();
    String getType();
}
