package com.rbkmoney.geck.migrator.kit;

import com.rbkmoney.geck.common.util.ObjectCache;
import com.rbkmoney.geck.migrator.*;

import java.util.*;


public class BaseMigrationStore implements MigrationStore {

    private final List<MigrationPointProvider> providers;
    private ObjectCache<InitResult> resultCache;

    private static final Comparator<Map.Entry<ThriftSpec, MigrationPointProvider>> specInDefComparator = (a, b) -> {
        int diff = a.getKey().getInDef().getVersion() - b.getKey().getInDef().getVersion();
        if (diff != 0) {
            return diff;
        }
        diff = a.getKey().getOutDef().getVersion() - b.getKey().getOutDef().getVersion();
        if (diff != 0) {
            return diff;
        }
        return a.getValue().hashCode() - b.getValue().hashCode();
    };

    public BaseMigrationStore(List<MigrationPointProvider> providers) {
        this.providers = new ArrayList<>(providers);
        resultCache = new ObjectCache<>(() -> initStore());
    }

    private InitResult initStore() {
        try {
            List<Map.Entry<ThriftSpec, MigrationPointProvider>> specList = new ArrayList<>();
            for (MigrationPointProvider provider: providers) {
                Collection<ThriftSpec> specs = provider.getSpecs();
                specs.stream().forEach(s -> specList.add(new AbstractMap.SimpleImmutableEntry<>(s, provider)));
            }
            Collections.sort(specList, specInDefComparator);
            int min = ThriftDef.NO_VERSION;
            int max = ThriftDef.NO_VERSION;

            for (Map.Entry<ThriftSpec, MigrationPointProvider> specEntry : specList) {
                ThriftSpec spec = specEntry.getKey();
                if (min == ThriftDef.NO_VERSION || spec.getInDef().getVersion() < min) {
                    min = spec.getInDef().getVersion();
                }
                if (max == ThriftDef.NO_VERSION || spec.getOutDef().getVersion() > max) {
                    max = spec.getOutDef().getVersion();
                }
            }
            if (min > max) {
                throw new MigrationException(String.format("Min version %d is greater than max %d", min, max));
            }
            return new InitResult(specList, min, max);
        } catch (MigrationException e) {
            throw new RuntimeException("Error during migration store init", e);
        }
    }

    public List<MigrationPoint> getMigrations(ThriftSpec thriftSpec) throws MigrationException {
        checkVersionBorders(thriftSpec.getInDef().getVersion());
        checkVersionBorders(thriftSpec.getOutDef().getVersion());

        List<MigrationPoint> migrationsList = new ArrayList<>();
        MutableThriftDef outTDef = new MutableThriftDef(thriftSpec.getInDef().getType(), thriftSpec.getInDef().getVersion());
        InitResult initResult = getInitResult();
        int idx = 0;
        while (outTDef.getVersion() < thriftSpec.getOutDef().getVersion()) {
            int newOutVersion = ThriftDef.NO_VERSION;
            String newOutType = null;
            for (int i = idx; i < initResult.specList.size(); ++i) {
                Map.Entry<ThriftSpec, MigrationPointProvider> specEntry = initResult.specList.get(i);
                if (isOverlaps(outTDef, specEntry.getKey().getInDef())) {
                    if (newOutVersion != ThriftDef.NO_VERSION && specEntry.getKey().getOutDef().getVersion() != newOutVersion) {
                        throw new MigrationException(String.format("Selected out version %d for in version %d doesn't match out version for examined migration point: %s", newOutVersion, outTDef.getVersion(), specEntry.getValue().toString()));
                    }
                    newOutVersion = specEntry.getKey().getOutDef().getVersion();
                    newOutType = newOutType == null ? specEntry.getKey().getOutDef().getType() : null;
                    migrationsList.add(specEntry.getValue().getMappedSpec(specEntry.getKey()));
                    ++idx;
                }
            }
            if (newOutVersion != ThriftDef.NO_VERSION) {
                outTDef.setType(newOutType);
                outTDef.setVersion(newOutVersion);
            } else {
                outTDef.setVersion(outTDef.getVersion() + 1);
            }
        }
        if (outTDef.getVersion() != thriftSpec.getOutDef().getVersion()) {
            throw new MigrationException("Not found migration for spec: "+thriftSpec);
        }
        if ((outTDef.getType() != null && thriftSpec.getOutDef().getType() != null && !outTDef.getType().equals(thriftSpec.getOutDef().getType()))) {
            throw new MigrationException(String.format("Out migration %s doesn't match required type: %d", outTDef, thriftSpec.getOutDef()));
        }
        return migrationsList;
    }

    private boolean isOverlaps(ThriftDef def1, ThriftDef def2) {
        if (def1.getVersion() == def2.getVersion()) {
            if (def1.getType() == null || def2.getType() == null) {
                return true;
            } else {
                return def1.getType().equals(def2.getType());
            }
        }
        return false;
    }

    private void checkVersionBorders(int version) throws MigrationException {
        InitResult initResult = getInitResult();
        if (version < initResult.firstVersion || version > initResult.lastVersion) {
            throw new MigrationException("Unknown version: " + version);
        }
    }

    public int getLastVersion() throws MigrationException {
        return getInitResult().lastVersion;
    }

    private InitResult getInitResult() throws MigrationException {
        try {
            InitResult initResult = resultCache.getObject();
            if (initResult == null) {
                throw new MigrationException("Failed to initialize migration store");
            }
            return initResult;
        } catch (MigrationException e) {
            throw e;
        } catch (Exception e) {
            throw new MigrationException("Failed to initialize migration store", e);
        }
    }

    private static class InitResult {
        private final List<Map.Entry<ThriftSpec, MigrationPointProvider>> specList;
        private final int lastVersion;
        private final int firstVersion;

        public InitResult(List<Map.Entry<ThriftSpec, MigrationPointProvider>> specList, int firstVersion, int lastVersion) {
            this.specList = specList;
            this.lastVersion = lastVersion;
            this.firstVersion = firstVersion;
        }
    }

}
