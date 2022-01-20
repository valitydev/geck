package dev.vality.geck.migrator.kit.object;

import dev.vality.geck.common.reflection.ClassFinder;
import dev.vality.geck.migrator.MigrationException;
import dev.vality.geck.migrator.MigrationPoint;
import dev.vality.geck.migrator.MigrationType;
import dev.vality.geck.migrator.kit.BaseMigrationSpec;
import dev.vality.geck.migrator.kit.SimpleMigrationPointProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ObjectMigrationPointProvider extends SimpleMigrationPointProvider {
    private static final Logger log = LoggerFactory.getLogger(ObjectMigrationPointProvider.class);

    private static final String CLASS_NAME_SUFFIX = "Spec";

    private static final String DEFAULT_SPEC_PACKAGE = "dev.vality.geck.migrator.spec.object";

    public static ObjectMigrationPointProvider newInstance() throws MigrationException {
        return newInstance(Arrays.asList(DEFAULT_SPEC_PACKAGE));
    }

    public static ObjectMigrationPointProvider newInstance(Collection<String> migrationSpecPkg) throws MigrationException {
        try {
            List<String> searchSpecPkgs = new ArrayList<>(migrationSpecPkg);
            List<Class<? extends ObjectSpec>> specClList = new ArrayList<>(ClassFinder.find(searchSpecPkgs, CLASS_NAME_SUFFIX, ObjectSpec.class));
            return new ObjectMigrationPointProvider(instantiateObjectSpecs(specClList));
        } catch (Exception e) {
            throw new MigrationException("Cannot create ObjectSpec list", e);
        }
    }

    private ObjectMigrationPointProvider(List<ObjectSpec> specList) {
        super(createMigrationPoints(specList));
    }

    private static List<ObjectSpec> instantiateObjectSpecs(List<Class<? extends ObjectSpec>> specClList) {
        List<ObjectSpec> specList = new ArrayList<>(specClList.size());
        for (Class<? extends ObjectSpec> specClass: specClList) {
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

