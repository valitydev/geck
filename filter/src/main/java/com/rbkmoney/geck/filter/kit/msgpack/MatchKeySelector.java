package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.filter.rule.ConditionRule;

public class MatchKeySelector extends Selector {
    public static final Rule MATCH_RULE = new ConditionRule(obj -> true);

    MatchKeySelector(Type type) {
        super(type, false);
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        return pushResult(config.nextConfig, config, true);
    }
}
