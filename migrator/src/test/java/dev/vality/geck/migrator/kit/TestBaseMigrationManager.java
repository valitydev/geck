package dev.vality.geck.migrator.kit;

import dev.vality.geck.migrator.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TestBaseMigrationManager {

    @Test
    public void shouldKeepSourceDataWhenMigratorDoesNotTransformIt() throws MigrationException {
        BaseMigrationStore migrationStore =
                new BaseMigrationStore(Arrays.asList(new MigrationPointProviderStub(Arrays.asList(
                new ThriftSpec(new ThriftDef(1, "t1"), new ThriftDef(2, "t2")),
                new ThriftSpec(new ThriftDef(2, "t2"), new ThriftDef(3, "t2")),
                new ThriftSpec(new ThriftDef(3, "t2"), new ThriftDef(30, "t3"))
        ))));
        BaseMigrationManager migrationManager =
                new BaseMigrationManager(migrationStore, Arrays.asList(new PassThroughMigrator()));

        String result = migrationManager.migrate("A", new ThriftDef(1), new SerializerDef<>("TEST"));

        Assert.assertEquals("A", result);
    }

    @Test
    public void shouldPassIntermediateMigrationResultToNextStep() throws MigrationException {
        BaseMigrationStore migrationStore =
                new BaseMigrationStore(Arrays.asList(new MigrationPointProviderStub(Arrays.asList(
                new ThriftSpec(new ThriftDef(1, "t1"), new ThriftDef(2, "t2")),
                new ThriftSpec(new ThriftDef(2, "t2"), new ThriftDef(3, "t2")),
                new ThriftSpec(new ThriftDef(3, "t2"), new ThriftDef(4, "t3"))
        ))));
        BaseMigrationManager migrationManager =
                new BaseMigrationManager(migrationStore, Arrays.asList(new StepNumberAppendingMigrator()));

        String result = migrationManager.migrate("A", new ThriftDef(1), new SerializerDef<>("TEST"));

        Assert.assertEquals("A123", result);
    }

    private static class MigrationPointProviderStub implements MigrationPointProvider {
        private final List<MigrationPoint> migrationPoints;

        MigrationPointProviderStub(Collection<ThriftSpec> thriftSpecs) {
            AtomicInteger idx = new AtomicInteger();
            SerializerDef serializerDef = new SerializerDef("TEST");
            migrationPoints = thriftSpecs.stream().map(thriftSpec -> {
                int i = idx.incrementAndGet();
                return new MigrationPoint(i, thriftSpec, serializerDef, new TestMigrationSpec("TEST" + i));
            }).collect(Collectors.toList());
        }

        @Override
        public List<ThriftSpec> getSpecs() throws MigrationException {
            return migrationPoints.stream().map(migrationPoint -> migrationPoint.getThriftSpec()).collect(Collectors.toList());
        }

        @Override
        public Map<ThriftSpec, MigrationPoint> getSpecMapping() throws MigrationException {
            return migrationPoints.stream().collect(Collectors.toMap(mp -> mp.getThriftSpec(), mp -> mp));
        }

        @Override
        public MigrationPoint getMappedSpec(ThriftSpec thriftSpec) throws MigrationException {
            return migrationPoints.stream().filter(mp -> mp.getThriftSpec().equals(thriftSpec)).findFirst().orElseThrow(() -> new RuntimeException("no t spec"));
        }
    }

    private static class TestMigrationSpec implements MigrationSpec<String> {
        private final String spec;

        TestMigrationSpec(String spec) {
            this.spec = spec;
        }

        @Override
        public String getSpec() {
            return spec;
        }

        @Override
        public String getType() {
            return "TEST";
        }
    }

    private static class PassThroughMigrator extends AbstractMigrator {

        @Override
        public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
            return serialize(data, serializerSpec, mPoint.getThriftSpec());
        }

        @Override
        public String getMigrationType() {
            return "TEST";
        }
    }

    private static class StepNumberAppendingMigrator implements Migrator {

        @Override
        public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) {
            String stepNumber = ((TestMigrationSpec) mPoint.getMigrationSpec()).getSpec().replace("TEST", "");
            return (O) (String.valueOf(data) + stepNumber);
        }

        @Override
        public String getMigrationType() {
            return "TEST";
        }
    }
}
