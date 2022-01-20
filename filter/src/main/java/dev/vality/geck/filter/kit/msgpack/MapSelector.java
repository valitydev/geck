package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;

import static dev.vality.geck.serializer.kit.EventFlags.endMap;
import static dev.vality.geck.serializer.kit.EventFlags.startMap;

class MapSelector extends Selector {
    private final Rule rule;

    MapSelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

 /*   @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            context.setLevelConsumed(eventFlag == EventFlags.endStruct);
            return mismatchResult(config);
        } else {
            if (eventFlag == startMap) {
                context.setLevelSelected(true);
                return ((Number)val).longValue() > 0 ? selectPushResult(val, rule, config.nextConfig, config) : mismatchResult(config);
            } else {
                return mismatchResult(config);
            }
        }
    }*/

    @Override
    SelectionResult select(byte eventFlag, Object val, Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if (eventFlag == endMap) {
                context.setLevelConsumed(true);
            }
            return mismatchResult(config);
        } else {
            if (eventFlag == startMap) {
                context.setLevelSelected(true);
                context.setMapSize(((Number) val).intValue());
                return selectPushResult(val, rule, config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }

    @Override
    MapSelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int mapSize;

        @Override
        public Selector.Context init() {
            mapSize = -1;
            return super.init();
        }

        public int getMapSize() {
            return mapSize;
        }

        public void setMapSize(int mapSize) {
            this.mapSize = mapSize;
        }

    }
}
