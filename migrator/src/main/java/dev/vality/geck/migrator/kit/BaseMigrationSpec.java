package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.MigrationSpec;

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
