package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.*;
import dev.vality.geck.migrator.kit.nop.NopMigrator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseMigrationManager implements MigrationManager {
    private final ConcurrentHashMap<ThriftSpec, List<MigrationPoint>> cache = new ConcurrentHashMap<>();
    private final Map<String, Migrator> migrators = new HashMap<>();
    private final MigrationStore store;

    public BaseMigrationManager(MigrationStore migrationStore) {
        store = migrationStore;
        migrators.put(MigrationType.NOP.getKey(), new NopMigrator());
    }

    public BaseMigrationManager(MigrationStore migrationStore, Collection<Migrator> customMigrators) {
        this(migrationStore);
        customMigrators.stream().forEach(cm -> migrators.put(cm.getMigrationType(), cm));
    }

    @Override
    public <I, O> O migrate(I src, ThriftSpec thriftSpec, SerializerSpec<I, O> serializerSpec) throws MigrationException {
        List<MigrationPoint> mPoints = cache.get(thriftSpec);
        if (mPoints == null) {
            mPoints = store.getMigrations(thriftSpec);
            cache.put(thriftSpec, mPoints);
        }
        MutableSerializerSpec transitionSerSpec = new MutableSerializerSpec();
        transitionSerSpec.setInDef(serializerSpec.getInDef());

        Object data = src;
        for (int i = 0; i < mPoints.size(); ++i) {
            MigrationPoint mPoint = mPoints.get(i);
            Migrator migrator = migrators.get(mPoint.getMigrationType());
            if (migrator == null) {
                throw new MigrationException("Not migrator for type: "+ mPoint.getMigrationType());
            }
            transitionSerSpec.setOutDef( ((i < mPoints.size() - 1) ? mPoints.get(i + 1).getSerializerDef() : serializerSpec.getOutDef()));
            data = migrator.migrate(src, mPoint, transitionSerSpec);
            transitionSerSpec.setInDef(mPoint.getSerializerDef());
        }

        return (O) data;
    }

    @Override
    public <T> T migrate(T src, ThriftDef thriftDef, SerializerDef<T> serializerDef) throws MigrationException {
        ThriftSpec tSpec = new ThriftSpec(thriftDef, new ThriftDef(store.getLastVersion()));
        SerializerSpec<T, T> sSpec = new BaseSerializerSpec<>(serializerDef, serializerDef);
        return migrate(src, tSpec, sSpec);
    }
}
