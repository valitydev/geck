package com.rbkmoney.geck.filter.kit.msgpack;

import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.kit.EventFlags;

import static com.rbkmoney.geck.serializer.kit.EventFlags.*;

class ArraySelector extends Selector {
    private final Rule rule;

    ArraySelector(Rule rule, Type type) {
        this(rule, type, false);
    }

    ArraySelector(Rule rule, Type type, boolean jumpValue) {
        super(type, jumpValue);
        this.rule = rule;
    }

    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if ((eventFlag == endList && context.getLevelSelectionType() == startList) ||
                    (eventFlag == startList && context.getLevelSelectionType() == startSet)) {
                context.setLevelConsumed(true);
            }
            return mismatchResult(config);
        } else {
            if (eventFlag == EventFlags.startList || eventFlag == EventFlags.startSet) {
                context.setLevelSelected(true);
                context.setLevelSelectionType(eventFlag);
                context.setArraySize(((Number) val).intValue());
                return selectPushResult(val, rule, config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    ArraySelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int levelSelectionType;
        private int arraySize;

        @Override
        public Selector.Context init() {
            levelSelectionType = EventFlags.nop;
            arraySize = -1;
            return super.init();
        }

        public int getArraySize() {
            return arraySize;
        }

        public void setArraySize(int arraySize) {
            this.arraySize = arraySize;
        }

        public int getLevelSelectionType() {
            return levelSelectionType;
        }

        public void setLevelSelectionType(int levelSelectionType) {
            this.levelSelectionType = levelSelectionType;
        }
    }
}
