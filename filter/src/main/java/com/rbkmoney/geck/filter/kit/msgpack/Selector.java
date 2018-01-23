package com.rbkmoney.geck.filter.kit.msgpack;


import com.rbkmoney.geck.filter.Condition;
import com.rbkmoney.geck.filter.Rule;
import com.rbkmoney.geck.serializer.StructHandleResult;

/**
 * Created by vpankrashkin on 13.09.17.
 */
abstract class Selector {
    private static final SelectionResult.Mismatch mismatchResult = new SelectionResult.Mismatch();

    private final Type type;
    private final boolean pullable;

    Selector(Type type) {
        this(type, true);
    }
    Selector(Type type, boolean pullable) {
        this.type = type;
        this.pullable = pullable;
    }

    public Type getType() {
        return type;
    }

    public boolean isPullable() {
        return pullable;
    }

    public StructHandleResult getLowestSkipOp(Context context) {
        return (context.lastResult == null || context.lastResult.type == SelectionResult.SelectionType.MATCH) ? StructHandleResult.SKIP_SIBLINGS : StructHandleResult.SKIP_SUBTREE;
    }

    SelectionResult.Match matchResult(Rule rule, Config config) {
        config.context.lastResult = new SelectionResult.Match(rule);
        return (SelectionResult.Match) config.context.lastResult;
    }

    SelectionResult.Mismatch mismatchResult(Config config) {
        config.context.lastResult = mismatchResult;
        return mismatchResult;
    }

    SelectionResult.ReuseLevel reuseResult(Config nextConfig, Config config) {
        config.context.lastResult = new SelectionResult.ReuseLevel(nextConfig);
        return (SelectionResult.ReuseLevel) config.context.lastResult;
    }

    SelectionResult.PushLevel pushResult(Config nextConfig, Config config) {
        return pushResult(nextConfig, config, false);
    }

    SelectionResult.PushLevel pushResult(Config nextConfig, Config config, boolean jumpValue) {
        config.context.lastResult = new SelectionResult.PushLevel(nextConfig, jumpValue);
        return (SelectionResult.PushLevel) config.context.lastResult;
    }

    SelectionResult selectPushResult(Object val, Rule rule, Config nextConfig, Config config) {
        return selectPushResult(val, rule, nextConfig, config, false);
    }

    SelectionResult selectPushResult(Object val, Rule rule, Config nextConfig, Config config, boolean jumpValue) {
        return selectResult(val, rule, nextConfig, config, SelectionResult.SelectionType.PUSH_LEVEL, jumpValue);
    }

    SelectionResult selectReuseResult(Object val, Rule rule, Config nextConfig, Config config) {
        return selectResult(val, rule, nextConfig, config, SelectionResult.SelectionType.REUSE_LEVEL);
    }

    SelectionResult selectResult(Object val, Rule rule, Config nextConfig, Config config, SelectionResult.SelectionType notFinalMatchType) {
        return selectResult(val, rule, nextConfig, config, notFinalMatchType, false);
    }
    SelectionResult selectResult(Object val, Rule rule, Config nextConfig, Config config, SelectionResult.SelectionType notFinalMatchType, boolean jumpValue) {
        if (match(rule, val)) {
            if (nextConfig == null) {
                return matchResult(rule, config);
            } else {
                switch (notFinalMatchType) {
                    case PUSH_LEVEL:
                        return pushResult(nextConfig, config, jumpValue);
                    case REUSE_LEVEL:
                        return reuseResult(nextConfig, config);
                    default:
                        throw new IllegalStateException("Unsupported action type: " + notFinalMatchType);
                }
            }
        } else {
            return mismatchResult(config);
        }
    }

    Context createContext() {
        return new Context();
    }

    Config createConfig() {
        return new Config(createContext());
    }

    Context tryInitContext(Selector.Context context) {
        return context.isInitialized() ? context : context.init();
    }

    abstract SelectionResult select(byte eventFlag, Object val, Selector.Config config);

    boolean match(Rule rule, Object data) {
        Condition[] conditions = rule.getConditions();
        for (int i = 0; i < conditions.length; ++i) {
            if (!conditions[i].accept(data)) {
                return false;
            }
        }
        return true;
    }

    static class Config {
        public final Context context;
        public Config prevConfig;
        public Config prevNativeConfig;
        public Config nextConfig;
        public Config nextNativeConfig;

        public Config(Context context) {
            this.context = context;
        }
    }

    class Context {
        private SelectionResult lastResult;
        private boolean initialized;
        private boolean levelSelected;
        private boolean levelConsumed;

        public Context init() {
            initialized = true;
            lastResult = null;
            levelSelected = false;
            levelConsumed = false;
            return this;
        }

        Selector getSelector() {
            return Selector.this;
        }


        public void reset() {
            initialized = false;
        }

        public void repeat() {
            lastResult = null;
        }

        public boolean isInitialized() {
            return initialized;
        }

        public void setInitialized(boolean initialized) {
            this.initialized = initialized;
        }

        boolean isLevelSelected() {
            return levelSelected;
        }

        void setLevelSelected(boolean levelSelected) {
            this.levelSelected = levelSelected;
        }

        SelectionResult getLastResult() {
            return lastResult;
        }

        void setLastResult(SelectionResult lastResult) {
            this.lastResult = lastResult;
        }

        public boolean isLevelConsumed() {
            return levelConsumed;
        }

        public void setLevelConsumed(boolean levelConsumed) {
            this.levelConsumed = levelConsumed;
        }

        boolean isFinalResult() {
            return isFinalResult(lastResult);
        }

        boolean isFinalResult(SelectionResult result) {
            return result != null && (result.type == SelectionResult.SelectionType.MATCH || result.type == SelectionResult.SelectionType.MISMATCH);
        }

    }

    static class SelectedData {
        private final byte type;
        private final Object data;

        public SelectedData(byte type, Object data) {
            this.type = type;
            this.data = data;
        }

        public byte getType() {
            return type;
        }

        public Object getData() {
            return data;
        }
    }

    enum Type {
        REPEATABLE, UNREPEATABLE, MULTI
    }
}
