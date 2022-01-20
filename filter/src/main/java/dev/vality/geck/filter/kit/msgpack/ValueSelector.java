package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;
import dev.vality.geck.serializer.kit.EventFlags;

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
