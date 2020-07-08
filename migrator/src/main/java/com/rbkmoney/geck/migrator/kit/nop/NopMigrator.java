package com.rbkmoney.geck.migrator.kit.nop;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationPoint;
import com.rbkmoney.geck.migrator.MigrationType;
import com.rbkmoney.geck.migrator.SerializerSpec;
import com.rbkmoney.geck.migrator.kit.AbstractMigrator;


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
