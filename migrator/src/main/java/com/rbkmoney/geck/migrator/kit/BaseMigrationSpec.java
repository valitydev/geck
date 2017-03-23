package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.MigrationSpec;

/**
 * Created by vpankrashkin on 22.03.17.
 */
public class BaseMigrationSpec<T> implements MigrationSpec<T> {
    private final T spec;
    private final String type;

    public BaseMigrationSpec(T spec, String type) {
        this.spec = spec;
        this.type = type;
    }

    @Override
    public T getSpec() {
        return spec;
    }

    @Override
    public String getType() {
        return type;
    }
}
