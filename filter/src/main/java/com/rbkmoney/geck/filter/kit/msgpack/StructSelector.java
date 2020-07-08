package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

import static com.rbkmoney.geck.serializer.kit.EventFlags.endStruct;


class StructSelector extends Selector {
    private final Rule rule;

    StructSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if (eventFlag == endStruct) {
                context.setLevelConsumed(true);
            }
            return mismatchResult(config);
        } else {
            if (eventFlag == EventFlags.startStruct) {
                context.setLevelSelected(true);
                context.setStructSize(((Number) val).intValue());
                return selectPushResult(val, rule, config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    StructSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int structSize;

        @Override
        public Selector.Context init() {
            structSize = -1;
            return super.init();
        }

        public int getStructSize() {
            return structSize;
        }

        public void setStructSize(int structSize) {
            this.structSize = structSize;
        }

    }
}
