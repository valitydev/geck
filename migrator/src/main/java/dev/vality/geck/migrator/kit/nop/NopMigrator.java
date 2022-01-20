package dev.vality.geck.migrator.kit.nop;

import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.MigrationPoint;
import dev.vality.geck.migrator.MigrationType;
import dev.vality.geck.migrator.SerializerSpec;
import dev.vality.geck.migrator.kit.AbstractMigrator;

public class NopMigrator extends AbstractMigrator {

    @Override
    public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
        return serialize(data, serializerSpec, mPoint.getThriftSpec());
    }

    @Override
    public String getMigrationType() {
        return MigrationType.NOP.getKey();
    }
}
