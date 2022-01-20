package dev.vality.geck.migrator.kit.jolt;

import com.bazaarvoice.jolt.Chainr;
import dev.vality.geck.migrator.ThriftSpec;

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
