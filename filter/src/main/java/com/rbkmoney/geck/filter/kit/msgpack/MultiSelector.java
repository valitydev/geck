package com.rbkmoney.geck.filter.kit.msgpack;

abstract class MultiSelector extends Selector {

    public MultiSelector(Type type) {
        super(type);
    }


    @Override
    SelectionResult select(byte eventFlag, Object val, Selector.Config config) {
        /*Context context = (Context) config.context;//tryInitContext
        if (context.isLevelConsumed()) {
            return mismatchResult(config);//TODO remove this
        }

        SelectionResult result = null;
        for (int i = 0; i < context.children.length; ++i) {
            Selector.Context chContext = context.children[i];
            if (!chContext.isFinalResult()) {
                SelectionResult chResult = chContext.getSelector().select(eventFlag, val, contexts);
                result = nearestResult(result, chResult);
            }
        }*/
        return null;

    }

    @Override
    Config createConfig() {
        return new Config(createContext());
    }


    static class Config extends Selector.Config {
        Selector.Config[] enclosedConfigs;

        Config(Selector.Context context) {
            super(context);
        }
    }

    private SelectionResult nearestResult(SelectionResult prevResult, SelectionResult newResult) {
        return null;
    }

    class Context extends Selector.Context {
        private final Selector.Context[][] childrenLevels;

        Context(Selector.Context[][] childrenLevels) {
            this.childrenLevels = childrenLevels;
        }

        private boolean isSelectable(SelectionResult result) {
            //if (result.type == SelectionResult.SelectionType.PUSH_LEVEL)
            return false;
        }

        @Override
        public Selector.Context init() {
            /*for (int i = 0; i < childrenLevels[getContextIndex()].length; ++i) {
                childrenLevels[getContextIndex()][i].init();
            }*/
            return super.init();
        }
    }
}
