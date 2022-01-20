package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;
import dev.vality.geck.filter.rule.ConditionRule;

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
