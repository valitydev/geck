package dev.vality.geck.migrator.kit.object;

import dev.vality.geck.common.util.TypeUtil;
import dev.vality.geck.migrator.*;
import dev.vality.geck.migrator.kit.AbstractMigrator;

public class ObjectMigrator extends AbstractMigrator {
    public static final SerializerDef<Object> SERIALIZER_DEF = new SerializerDef<>(MigrationType.JOBJECT.getKey());
    @Override
    public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
        Object inData = serialize(data, serializerSpec.getInDef(), SERIALIZER_DEF, mPoint.getThriftSpec());
        ObjectSpec mSpec = TypeUtil.convertType(ObjectSpec.class, mPoint.getMigrationSpec().getSpec());
        Object outData = mSpec.apply(inData);
        return serialize(outData, SERIALIZER_DEF, serializerSpec.getOutDef(), mPoint.getThriftSpec());
    }

    @Override
    public String getMigrationType() {
        return MigrationType.JOBJECT.getKey();
    }
}
