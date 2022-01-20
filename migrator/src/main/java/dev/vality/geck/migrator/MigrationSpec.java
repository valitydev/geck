package dev.vality.geck.migrator;

public interface MigrationSpec<T> {
    T getSpec();
    String getType();
}
