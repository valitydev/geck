package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

/**
 * Created by vpankrashkin on 21.09.17.
 */
class ValueSelector extends Selector {
    private final Rule rule;

    ValueSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) tryInitContext(config.context);
        context.setLevelSelected(true);
        return selectPushResult(eventFlag == EventFlags.pointValue ? val : new SelectedData(eventFlag, val), rule, config.nextConfig, config);
    }

    @Override
    ValueSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
