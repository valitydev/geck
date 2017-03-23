package com.rbkmoney.geck.migrator.kit.object;

import com.rbkmoney.geck.common.reflection.ClassFinder;
import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.MigrationPoint;
import com.rbkmoney.geck.migrator.MigrationType;
import com.rbkmoney.geck.migrator.kit.BaseMigrationSpec;
import com.rbkmoney.geck.migrator.kit.SimpleMigrationPointProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by vpankrashkin on 22.03.17.
 */
public class ObjectMigrationPointProvider extends SimpleMigrationPointProvider {
    private static final Logger log = LoggerFactory.getLogger(ObjectMigrationPointProvider.class);

    private static final String CLASS_NAME_SUFFIX = "Spec";

    public static ObjectMigrationPointProvider newInstance(String migrationSpecPkg) throws MigrationException {
        try {
            List<Class<ObjectSpec>> specClList = ClassFinder.find(migrationSpecPkg, CLASS_NAME_SUFFIX, ObjectSpec.class);
            return new ObjectMigrationPointProvider(instantiateObjectSpecs(specClList));
        } catch (Exception e) {
            throw new MigrationException("Cannot create ObjectSpec list", e);
        }
    }

    private ObjectMigrationPointProvider(List<ObjectSpec> specList) {
        super(createMigrationPoints(specList));
    }

    private static List<ObjectSpec> instantiateObjectSpecs(List<Class<ObjectSpec>> specClList) {
        List<ObjectSpec> specList = new ArrayList<>(specClList.size());
        for (Class<ObjectSpec> specClass: specClList) {
            try {
                specList.add(specClass.newInstance());
            } catch (Exception e) {
                log.error("Cannot create ObjectSpec class instance:" + specClass, e);
            }
        }
        return specList;
    }

    private static List<MigrationPoint> createMigrationPoints(List<ObjectSpec> specList) {
        AtomicInteger counter = new AtomicInteger(0);
        return specList.stream()
                .map(spec -> new MigrationPoint(
                        counter.incrementAndGet(),
                        spec.getThriftSpec(),
                        ObjectMigrator.SERIALIZER_DEF,
                        new BaseMigrationSpec(spec, MigrationType.JOBJECT.getKey())
                )).collect(Collectors.toList());
    }
}

