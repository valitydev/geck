package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

import static com.rbkmoney.geck.serializer.kit.EventFlags.endMapKey;
import static com.rbkmoney.geck.serializer.kit.EventFlags.endMapValue;
import static com.rbkmoney.geck.serializer.kit.EventFlags.endStruct;

/**
 * Created by vpankrashkin on 13.09.17.
 */
class MapValueSelector extends Selector {
    private final Rule rule;

    MapValueSelector(Rule rule, Type type) {
        super(type, false);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if (eventFlag == endMapValue) {
                context.setLevelConsumed(true);
            }
            return mismatchResult(config);
        } else {
            if (eventFlag == EventFlags.startMapValue) {
                context.setLevelSelected(true);
                return selectPushResult(val, rule, config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    MapValueSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
    }
}
