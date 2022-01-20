package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;
import dev.vality.geck.serializer.kit.EventFlags;

class FieldSelector extends Selector {
    private final Rule rule;
    private final boolean jumpValue;

    FieldSelector(Rule rule, Type type) {
        this(rule, type, false);
    }

    FieldSelector(Rule rule, Type type, boolean jumpValue) {
        super(type);
        this.rule = rule;
        this.jumpValue = jumpValue;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);

        if (!context.isLevelSelected()) {
            StructSelector.Context strContext = (StructSelector.Context) config.prevNativeConfig.context;
            context.setLevelSelected(true);
            context.setRemainSelections(strContext.getStructSize());
        }

        int remains = context.getRemainSelections();
        if (remains > 0 || eventFlag == EventFlags.endStruct) {
            if (eventFlag == EventFlags.pointName) {
                context.setRemainSelections(remains - 1);
                return selectPushResult(val, rule, config.nextConfig, config, jumpValue);
            } else if (eventFlag == EventFlags.endStruct) {
                context.reset();
                return reuseResult(config.prevConfig, config);
            }
            context.setLevelConsumed(true);
        }
        return mismatchResult(config);
    }

    @Override
    FieldSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int remainSelections;

        public int getRemainSelections() {
            return remainSelections;
        }

        public void setRemainSelections(int remainSelections) {
            this.remainSelections = remainSelections;
        }
    }
}
