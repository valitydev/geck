package com.rbkmoney.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.Chainr;
import com.rbkmoney.geck.migrator.ThriftSpec;

/**
 * Created by tolkonepiu on 26/03/2017.
 */
public class JoltSpec {

    private final ThriftSpec thriftSpec;
    private final Chainr chainr;

    public JoltSpec(ThriftSpec thriftSpec, Chainr chainr) {
        this.thriftSpec = thriftSpec;
        this.chainr = chainr;
    }

    public ThriftSpec getThriftSpec() {
        return thriftSpec;
    }

    public Chainr getChainr() {
        return chainr;
    }
}
