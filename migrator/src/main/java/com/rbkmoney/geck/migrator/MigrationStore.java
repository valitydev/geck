package com.rbkmoney.geck.migrator;

import java.util.List;

/**
 * Created by vpankrashkin on 02.03.17.
 */
public interface MigrationStore {
    List<MigrationPoint> getMigrations(ThriftDef in, ThriftDef out);
}
