package com.rbkmoney.geck.migrator;

import com.rbkmoney.geck.migrator.kit.BaseMigrationManager;
import com.rbkmoney.geck.migrator.kit.BaseMigrationStore;
import com.rbkmoney.geck.migrator.kit.MigrationPointProvider;
import com.rbkmoney.geck.migrator.kit.jolt.JoltMigrator;
import com.rbkmoney.geck.migrator.kit.object.ObjectMigrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MigrationManagerBuilder {
    private Map<String, Migrator> migratorsMap = new HashMap<>();
    private List<MigrationPointProvider> providers = new ArrayList<>();

    private List<MigrationPointProvider> createMPointProviders() throws MigrationException {
        if (providers.isEmpty()) {
            throw new MigrationException("providers must be set");
        }

        List<MigrationPointProvider> newProviders = new ArrayList<>();
        newProviders.addAll(providers);
        return newProviders;
    }

    private List<Migrator> createMigrators() throws MigrationException {
        List<Migrator> newMigrators = new ArrayList<>();

        newMigrators.addAll(migratorsMap.values());
        if (!migratorsMap.containsKey(ObjectMigrator.class.getName())) {
            newMigrators.add(new ObjectMigrator());
        }
        if (!migratorsMap.containsKey(JoltMigrator.class.getName())) {
            newMigrators.add(new JoltMigrator());
        }

        return newMigrators;
    }

    public MigrationManagerBuilder setMigrators(List<Migrator> customMigrators) {
        customMigrators.stream().forEach(m -> migratorsMap.put(m.getClass().getName(), m));
        return this;
    }

    public MigrationManagerBuilder setMigrationPointProviders(List<MigrationPointProvider> customProviders) {
        providers = new ArrayList<>(customProviders);
        return this;
    }

    public MigrationManager build() throws MigrationException {
        MigrationStore store = new BaseMigrationStore(createMPointProviders());
        return new BaseMigrationManager(store, createMigrators());
    }
}
