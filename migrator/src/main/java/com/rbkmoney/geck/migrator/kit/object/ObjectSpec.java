package com.rbkmoney.geck.migrator.kit.object;

import com.rbkmoney.geck.migrator.MigrationException;
import com.rbkmoney.geck.migrator.ThriftSpec;

public interface ObjectSpec {
    Object apply(Object in) throws MigrationException;
    ThriftSpec getThriftSpec();
}
