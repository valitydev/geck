package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;
import dev.vality.geck.serializer.kit.EventFlags;

import static dev.vality.geck.serializer.kit.EventFlags.endMapValue;

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
