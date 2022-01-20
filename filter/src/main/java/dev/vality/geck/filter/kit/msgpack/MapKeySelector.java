package dev.vality.geck.filter.kit.msgpack;

import dev.vality.geck.filter.Rule;
import dev.vality.geck.serializer.kit.EventFlags;

import static dev.vality.geck.serializer.kit.EventFlags.startMapKey;

class MapKeySelector extends Selector {
    private final Rule rule;

    MapKeySelector(Rule rule, Type type) {
        super(type);
        this.rule = rule;
    }

/*    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        Context context = (Context) tryInitContext(config.context);
        if (context.isLevelSelected()) {
            if (eventFlag == EventFlags.endMapKey) {
                context.setLevelConsumed(true);
                return mismatchResult(config);
            } else {
                return selectPushResult(val, rule, config.nextConfig, config);
            }
        } else {
            if (eventFlag == EventFlags.startMapKey) {
                context.setLevelSelected(true);
                return pushResult(config.nextConfig, config);
            } else {
                return mismatchResult(config);
            }
        }
    }*/


    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        /*Context context = (Context) tryInitContext(config.context);

        if (eventFlag == startMapKey && !context.isLevelSelected()) {
            MapSelector.Context mapContext = (MapSelector.Context) config.prevNativeConfig.context;
            context.setLevelSelected(true);
            context.setRemainSelections(mapContext.getMapSize());
        }

        int remains = context.getRemainSelections();
        boolean keyConsumed = eventFlag == endMapKey;
        if (!keyConsumed && remains > 0) {
            context.setRemainSelections(remains - 1);
            return selectPushResult(val, rule, config.nextConfig, config);
        } else {
            if (keyConsumed) {
                context.setLevelConsumed(true);
            }
            return mismatchResult(config);
        }*/


        Context context = (Context) tryInitContext(config.context);

        if (eventFlag == startMapKey && !context.isLevelSelected()) {
            MapSelector.Context mapContext = (MapSelector.Context) config.prevNativeConfig.context;
            context.setLevelSelected(true);
            context.setRemainSelections(mapContext.getMapSize());
        }

        int remains = context.getRemainSelections();
        if (remains > 0 || eventFlag == EventFlags.endMap) {
            if (eventFlag == EventFlags.startMapKey) {
                context.setRemainSelections(remains - 1);
                 return selectPushResult(val, rule, config.nextConfig, config);
            } else {
                if (eventFlag == EventFlags.endMap) {
                    context.reset();
                    context.setLevelConsumed(true);
                    return reuseResult(config.prevConfig, config);
                }
                context.setLevelConsumed(true);
            }
        }
        return mismatchResult(config);
    }

    @Override
    MapKeySelector.Context createContext() {
        return new Context();
    }

    class Context extends Selector.Context {
        private int remainSelections;

        @Override
        public Selector.Context init() {
            remainSelections = -1;
            return super.init();
        }

        public int getRemainSelections() {
            return remainSelections;
        }

        public void setRemainSelections(int remainSelections) {
            this.remainSelections = remainSelections;
        }
    }
}
