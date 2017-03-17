package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.migrator.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Created by vpankrashkin on 17.03.17.
 */
public class TestBaseMigrationManager {

    @Test
    public void test() throws MigrationException {
        BaseMigrationStore migrationStore = new BaseMigrationStore(Arrays.asList(new MigrationPointProviderImpl(Arrays.asList(
                new ThriftSpec(new ThriftDef(1, "t1"), new ThriftDef(2, "t2")),
                new ThriftSpec(new ThriftDef(2, "t2"), new ThriftDef(3, "t2")),
                new ThriftSpec(new ThriftDef(3, "t2"), new ThriftDef(30, "t3"))
        ))));
        BaseMigrationManager migrationManager = new BaseMigrationManager(migrationStore, Arrays.asList(new MigratorImpl()));
        String res = migrationManager.migrate("A", new ThriftDef(1), new SerializerDef<>("TEST"));
        Assert.assertEquals("A", res);
    }

    private static class MigrationPointProviderImpl implements MigrationPointProvider {
        private List<MigrationPoint> migrationPoints;

        public MigrationPointProviderImpl(Collection<ThriftSpec> tSpecs) {
            AtomicInteger idx = new AtomicInteger();
            SerializerDef serializerDef = new SerializerDef("TEST");
            migrationPoints = tSpecs.stream().map(thriftSpec -> {
                int i = idx.incrementAndGet();
                return new MigrationPoint(i, thriftSpec, serializerDef, new MigrationSpecImpl("TEST"+i));
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

    private static class MigrationSpecImpl implements MigrationSpec<String> {
        private String spec;

        public MigrationSpecImpl(String spec) {
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

    private static class MigratorImpl extends AbstractMigrator {

        @Override
        public <I, O> O migrate(I data, MigrationPoint mPoint, SerializerSpec<I, O> serializerSpec) throws MigrationException {
            return serialize(data, serializerSpec, mPoint.getThriftSpec());
        }

        @Override
        public String getMigrationType() {
            return "TEST";
        }
    }
}
