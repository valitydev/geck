package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.Chainr;
import com.rbkmoney.geck.migrator.*;
import com.rbkmoney.geck.migrator.kit.AbstractMigrator;

public class JoltMigrator extends AbstractMigrator {
    public static final SerializerDef<Object> SERIALIZER_DEF = new SerializerDef<>(MigrationType.JOLT.getKey());

    @Override
    public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
        Object inData = serialize(data, serializerSpec.getInDef(), SERIALIZER_DEF, mPoint.getThriftSpec());
        Chainr chainr = (Chainr) mPoint.getMigrationSpec().getSpec();
        Object outData = chainr.transform(inData);
        return serialize(outData, SERIALIZER_DEF, serializerSpec.getOutDef(), mPoint.getThriftSpec());
    }

    @Override
    public String getMigrationType() {
        return MigrationType.JOLT.getKey();
    }
}
